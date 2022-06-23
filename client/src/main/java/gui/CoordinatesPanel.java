package gui;

import utilites.LanguageManager;
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

    private final GridBagConstraints constraintsX = new GridBagConstraints(
            0, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsXTextField = new GridBagConstraints(
            1, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsY = new GridBagConstraints(
            0, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsYTextField = new GridBagConstraints(
            1, 1, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(2,2,2,2), 0, 0);

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

    private void setTextSize(){
        xTextField.setMinimumSize(new Dimension(50, 20));
        xTextField.setPreferredSize(new Dimension(100, 20));
        yTextField.setMinimumSize(new Dimension(50, 20));
        yTextField.setPreferredSize(new Dimension(100, 20));
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