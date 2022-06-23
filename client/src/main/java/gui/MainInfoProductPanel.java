package gui;

import content.UnitOfMeasure;
import utilites.LanguageManager;
import utilites.UpdatablePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class MainInfoProductPanel extends UpdatablePanel {

    private final LanguageManager languageManager;

    private final JLabel name = new JLabel();
    private final JLabel price = new JLabel();
    private final JLabel partNumber = new JLabel();
    private final JLabel cost = new JLabel();
    private final JLabel unit = new JLabel();

    private final JTextField nameTextField = new JTextField();
    private final JTextField priceTextField = new JTextField();
    private final JTextField partNumberTextField = new JTextField();
    private final JTextField costTextField = new JTextField();

    private JComboBox<String> unitOfMeasure;
    private final JCheckBox checkbox = new JCheckBox();

    private final GridBagConstraints constraintsName = new GridBagConstraints(
            0, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsNameText = new GridBagConstraints(
            1, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsPrice = new GridBagConstraints(
            0, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsPriceText = new GridBagConstraints(
            1, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsPartNumber = new GridBagConstraints(
            0, 2, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsPartNumberText = new GridBagConstraints(
            1, 2, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsCost = new GridBagConstraints(
            0, 3, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsCostText = new GridBagConstraints(
            1, 3, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsUnitOfMeasure = new GridBagConstraints(
            0, 4, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsUnitOfMeasureTextField = new GridBagConstraints(
            1, 4, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsQuestion = new GridBagConstraints(
            0, 5, 2, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    public MainInfoProductPanel(LanguageManager languageManager){
        super();
        this.languageManager = languageManager;
        setNameButton();
        initComboBox();
        setBox();
        configPanel();
    }

    private void setNameButton(){
        name.setText(languageManager.getString("name")+": ");
        price.setText(languageManager.getString("price") + ": ");
        partNumber.setText(languageManager.getString("partNumber")+ ": ");
        cost.setText(languageManager.getString("manufactureCost") + ": ");
        unit.setText(languageManager.getString("unitOfMeasure") + ": ");
        checkbox.setText(languageManager.getString("question"));
    }

    private void initComboBox(){
        Vector<String> titleUnit = new Vector<>();
        Arrays.stream(UnitOfMeasure.getArrayNames()).map(languageManager::getString).forEachOrdered(titleUnit::add);
        unitOfMeasure = new JComboBox<>(titleUnit);
    }

    private void setBox(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), languageManager.getString("main_data"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", Font.ITALIC, 12)));
    }

    private void configPanel(){
        removeAll();
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        add(name, constraintsName);
        add(nameTextField, constraintsNameText);

        add(price, constraintsPrice);
        add(priceTextField, constraintsPriceText);

        add(partNumber, constraintsPartNumber);
        add(partNumberTextField, constraintsPartNumberText);

        add(cost, constraintsCost);
        add(costTextField, constraintsCostText);

        add(unit, constraintsUnitOfMeasure);
        add(unitOfMeasure, constraintsUnitOfMeasureTextField);

        add(checkbox, constraintsQuestion);
    }

    public void update(){
        setNameButton();
        initComboBox();
        setBox();
        configPanel();
        repaint();
    }
}