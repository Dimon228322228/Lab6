package gui;

import actionClient.CommandHandler;
import authentication.CurrentAccount;
import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import lombok.Getter;
import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;

public abstract class ProductFrame extends JFrame {

    protected final LanguageManager languageManager;
    protected final CommandHandler commandHandler;

    protected final JButton someActionButton = new JButton();
    protected final JButton cancel = new JButton();

    protected JComboBoxLanguage language;

    @Getter protected Product product;

    protected final MainInfoProductPanel mainInfoProductPanel;
    protected final OwnerPanel ownerPanel;
    protected final CoordinatesPanel coordinatesPanel;

    private final Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();

    private final GridBagConstraints constraintsLanguage = new GridBagConstraints(
            0, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(6,12,2,2), 0, 0);

    private final GridBagConstraints constraintsMainInfoPanel = new GridBagConstraints(
            1, 1, 1, 2, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsCoordinatePanel = new GridBagConstraints(
            2, 1, 3, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,12), 0, 0);

    private final GridBagConstraints constraintsOwnerPanel = new GridBagConstraints(
            2, 2, 3, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,12), 0, 0);

    private final GridBagConstraints constraintsButton = new GridBagConstraints(
            4, 3, 1, 1, 0, 0,
            GridBagConstraints.EAST, 0,
            new Insets(2,2,6,12), 0, 0);

    public ProductFrame(LanguageManager languageManager, CommandHandler commandHandler, String commandName){
        super();
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
        ownerPanel = new OwnerPanel(languageManager);
        coordinatesPanel = new CoordinatesPanel(languageManager);
        mainInfoProductPanel = new MainInfoProductPanel(languageManager){
            @Override
            public void resetAccessOwnerPanel() {
                ownerPanel.setEnabled(false);
            }
            @Override
            public void setAccessOwnerPanel() {
                ownerPanel.setEnabled(true);
            }
        };
        mainInfoProductPanel.resetAccessOwnerPanel();
        setNameButton();
        setActionButton(commandName);
        initLanguageCheckBox();
        setTitle(languageManager.getString("productFrame"));
    }

    private void initLanguageCheckBox(){
        language = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTitle(languageManager.getString("productFrame"));
                mainInfoProductPanel.update();
                ownerPanel.update();
                coordinatesPanel.update();
                setNameButton();
                pack();
                revalidate();
            }
        };
    }

    protected abstract void setNameButton();

    protected abstract void setActionButton(String commandName);

    protected boolean createProduct(){
        BuilderProduct builderProduct = new BuilderProduct();
        try{
        builderProduct.setName(mainInfoProductPanel.getNameProduct())
                .setXCoordinate(coordinatesPanel.getXCoordinate())
                .setYCoordinate(coordinatesPanel.getYCoordinate())
                .setPrice(mainInfoProductPanel.getPriceProduct())
                .setPartNumber(mainInfoProductPanel.getPartNumberProduct())
                .setManufactureCost(mainInfoProductPanel.getCostProduct())
                .setUnitOfMeasure(mainInfoProductPanel.getUnitProduct())
                .setUsername(CurrentAccount.getAccount().getName());
        if (mainInfoProductPanel.getStateCheckBox()){
            builderProduct.setPersonName(ownerPanel.getNameOwner())
                    .setPersonBirthday(ownerPanel.getBirthdayOwner())
                    .setPersonHeight(ownerPanel.getHeightOwner())
                    .setPersonWeight(ownerPanel.getWeightOwner())
                    .setPersonPassportId(ownerPanel.getPassportOwner());
        }
        product = builderProduct.getProduct();
        } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
            return false;
        }
        return true;
    }

    public void createFrame(){
        JPanel main = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        main.setLayout(layout);

        JPanel buttonPanel = new JPanel();
        BoxLayout layout1 = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
        buttonPanel.setLayout(layout1);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(someActionButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(12,0)));
        buttonPanel.add(cancel);

        main.add(language, constraintsLanguage);
        main.add(mainInfoProductPanel, constraintsMainInfoPanel);
        main.add(coordinatesPanel, constraintsCoordinatePanel);
        main.add(ownerPanel, constraintsOwnerPanel);
        main.add(buttonPanel, constraintsButton);


        add(main);
        pack();
        setBounds(sizeScreen.width/2 - getSize().width/2, sizeScreen.height/2 - getSize().height/2, getSize().width,getSize().height);
        revalidate();
    }
}