package gui;

import actionClient.CommandHandler;
import authentication.CurrentAccount;
import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import lombok.Getter;
import lombok.Setter;
import utilites.LanguageManager;
import utilites.MyConstraints;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;

public abstract class ProductFrame extends JFrame {
    @Setter
    protected long id;

    protected final LanguageManager languageManager;
    protected final CommandHandler commandHandler;

    protected final JButton someActionButton = new JButton();
    protected final JButton cancel = new JButton();

    @Setter private String nameSomeActionButton = "add";

    protected JComboBoxLanguage language;

    @Getter protected Product product;

    protected MainInfoProductPanel mainInfoProductPanel;
    protected OwnerPanel ownerPanel;
    protected CoordinatesPanel coordinatesPanel;
    protected JPanel main;

    private final Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();

    private final GridBagConstraints constraintsLanguage = (new MyConstraints()).setGridX(0).setGridY(0).setTopInsets(6).setLeftInsets(12);
    private final GridBagConstraints constraintsMainInfoPanel = (new MyConstraints()).setGridX(1).setGridY(1).setGridHeight(2).setFill(GridBagConstraints.BOTH);
    private final GridBagConstraints constraintsCoordinatePanel = (new MyConstraints()).setGridX(2).setGridY(1).setGridWidth(3).setRightInsets(12);
    private final GridBagConstraints constraintsOwnerPanel = (new MyConstraints()).setGridX(2).setGridY(2).setGridWidth(3).setRightInsets(12);
    private final GridBagConstraints constraintsButton = (new MyConstraints()).setGridX(4).setGridY(3).setFill(0).setBottomInsets(6).setRightInsets(12);

    public ProductFrame(LanguageManager languageManager, CommandHandler commandHandler){
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
        initFrame();
    }

    public void initFrame(){
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

        someActionButton.setText(languageManager.getString(nameSomeActionButton));
        cancel.setText(languageManager.getString("cancel"));

        cancel.addActionListener(e -> {
            dispose();
        });

        setActionButton();

        language = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTitle(languageManager.getString("productFrame"));
                mainInfoProductPanel.update();
                ownerPanel.update();
                coordinatesPanel.update();
                cancel.setText(languageManager.getString("cancel"));
                someActionButton.setText(languageManager.getString(nameSomeActionButton));
                pack();
                revalidate();
            }
        };

        setTitle(languageManager.getString("productFrame"));
    }

    protected abstract void setActionButton();

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
        if (main != null) remove(main);
        main = new JPanel();
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

    public void setProductInformation(int row, Table.MyTableModel tableModel){
        mainInfoProductPanel.setProductInformation(row, tableModel);
        coordinatesPanel.setProductInformation(row, tableModel);
        if (mainInfoProductPanel.getStateCheckBox()){
            ownerPanel.setProductInformation(row, tableModel);
            ownerPanel.setEnabled(true);
        }
    }

}