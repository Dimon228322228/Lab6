package gui;

import action.ResultAction;
import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;
import java.util.Optional;

public class RemoveLowerFrame extends ProductFrame{

    public RemoveLowerFrame(LanguageManager languageManager, CommandHandler commandHandler, String commandName) {
        super(languageManager, commandHandler, commandName);
    }

    protected void setNameButton(){
        someActionButton.setText(languageManager.getString("removeLover"));
        cancel.setText(languageManager.getString("cancel"));
    }

    @Override
    protected void setActionButton(String commandName) {
        someActionButton.addActionListener(e -> {
            if (createProduct()) {
                Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand(commandName, null, product);
                if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                    JOptionPane.showMessageDialog(this, languageManager.getString("Remove " + resultActionOptional.get().getCollection().size() + " element(-s) deleted"), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                }
                setVisible(false);
            }
            else JOptionPane.showMessageDialog(this, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
        });
        cancel.addActionListener(e -> {
            setVisible(false);
        });
    }
}
