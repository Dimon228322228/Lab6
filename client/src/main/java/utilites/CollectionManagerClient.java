package utilites;

import actionClient.CommandHandler;
import authentication.CurrentAccount;
import content.Product;
import exceptions.InvalidRecievedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionManagerClient {

    private List<Product> products = new ArrayList<>();
    private final List<UpdatableElement> updatableElements = new ArrayList<>();
    private final CommandHandler commandHandler;

    public CollectionManagerClient(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
        startSynchronizedCollection();
    }

    public List<Product> getProducts(){
        return products;
    }

    public void subscribe(UpdatableElement updatableElement){
        updatableElements.add(updatableElement);
    }

    public void unsubscribe(UpdatableElement updatableElement){
        updatableElements.remove(updatableElement);
    }

    private synchronized void setNewCollection(List<Product> productList){
        if (productList.size() != products.size()){
            products = productList;
            updatableElements.stream().forEach(UpdatableElement::update);
        }
        if (!productList.containsAll(products) || !products.containsAll(productList)) {
            products = productList;
            updatableElements.stream().forEach(UpdatableElement::update);
        }
    }

    private void startSynchronizedCollection(){
        new Thread(() -> {
            while(true){
                try {
                    if (CurrentAccount.getAccount() != null) {
                        setNewCollection(commandHandler.getCollectionFromServer());
                    }
                } catch (InvalidRecievedException | IOException | NullPointerException ignored) {}
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignore) {}
            }
        }).start();
    }
}
