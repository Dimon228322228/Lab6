package gui;

import utilites.LanguageManager;
import utilites.MyConstraints;
import utilites.UpdatablePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CoordinatesPanel extends UpdatablePanel {

    LanguageManager languageManager;

    private final JLabel x = new JLabel();
    private final JLabel y = new JLabel();

    private final JTextField xTextField = new JTextField();
    private final JTextField yTextField = new JTextField();

    private final GridBagConstraints constraintsX = new MyConstraints();
    private final GridBagConstraints constraintsXTextField = (new MyConstraints()).setGridX(1).setGridY(0).setFill(GridBagConstraints.BOTH);
    private final GridBagConstraints constraintsY = (new MyConstraints()).setGridX(0).setGridY(1);
    private final GridBagConstraints constraintsYTextField = (new MyConstraints()).setGridX(1).setGridY(1).setFill(GridBagConstraints.BOTH);

    public CoordinatesPanel(LanguageManager languageManager){
        super();
        this.languageManager = languageManager;
        setTextSize();
        setNameLabel();
        setBox();
        configPanel();
    }

    private void setBox(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.orange), languageManager.getString("coordinates"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));
    }

    private void setNameLabel(){
        x.setText(languageManager.getString("x") + ": ");
        y.setText(languageManager.getString("y") + ": ");
    }

    public String getXCoordinate(){
        return xTextField.getText();
    }
    public String getYCoordinate(){
        return yTextField.getText();
    }

    private void setTextSize(){
        xTextField.setMinimumSize(new Dimension(50, 20));
        xTextField.setPreferredSize(new Dimension(100, 20));
        yTextField.setMinimumSize(new Dimension(50, 20));
        yTextField.setPreferredSize(new Dimension(100, 20));
    }

    public void setProductInformation(int row, Table.MyTableModel tableModel){
        xTextField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        yTextField.setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }

    private void configPanel(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(x, constraintsX);
        add(xTextField, constraintsXTextField);

        add(y, constraintsY);
        add(yTextField, constraintsYTextField);
    }

    public void update(){
        setNameLabel();
        setBox();
        repaint();
    }
}