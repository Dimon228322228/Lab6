package gui;

import utilites.LanguageManager;
import utilites.MyConstraints;
import utilites.UpdatablePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OwnerPanel extends UpdatablePanel {

    LanguageManager languageManager;

    private final JLabel name = new JLabel();
    private final JLabel birthday = new JLabel();
    private final JLabel height = new JLabel();
    private final JLabel weight = new JLabel();
    private final JLabel passportId = new JLabel();

    private final JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(100,50,300,1));
    private final JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(70,0,1000,1));
    private JSpinner dateSpinner;

    private final JTextField nameTextField = new JTextField();
    private final JTextField passportTextField = new JTextField();

    private final GridBagConstraints constraintsName = (new MyConstraints()).setGridX(0).setGridY(0);
    private final GridBagConstraints constraintsNameText = (new MyConstraints()).setGridX(1).setGridY(0);
    private final GridBagConstraints constraintsBirthday = (new MyConstraints()).setGridX(0).setGridY(1);
    private final GridBagConstraints constraintsBirthdaySpinner = (new MyConstraints()).setGridX(1).setGridY(1);
    private final GridBagConstraints constraintsHeight = (new MyConstraints()).setGridX(0).setGridY(2);
    private final GridBagConstraints constraintsHeightSpinner = (new MyConstraints()).setGridX(1).setGridY(2);
    private final GridBagConstraints constraintsWeight = (new MyConstraints()).setGridX(0).setGridY(3);
    private final GridBagConstraints constraintsWeightSpinner = (new MyConstraints()).setGridX(1).setGridY(3);
    private final GridBagConstraints constraintsPassportId = (new MyConstraints()).setGridX(0).setGridY(4);
    private final GridBagConstraints constraintsPassportIdTextField = (new MyConstraints()).setGridX(1).setGridY(4);

    public OwnerPanel(LanguageManager languageManager){
        this.languageManager = languageManager;
        setTextSize();
        initDateSpinner();
        setNameLabels();
        setBox();
        configPanel();
    }

    private void initDateSpinner(){
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setLocale(languageManager.getCurrentLocale());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner);
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        dateSpinner.setEditor(editor);
    }

    private void setTextSize(){
        nameTextField.setPreferredSize(new Dimension(50,20));
        passportTextField.setPreferredSize(new Dimension(50,20));
    }

    private void setNameLabels(){
        name.setText(languageManager.getString("nameOwner")+": ");
        birthday.setText(languageManager.getString("birthday") + ": ");
        height.setText(languageManager.getString("height") + ": ");
        weight.setText(languageManager.getString("weight") + ": ");
        passportId.setText(languageManager.getString("passportID") + ": ");
    }

    private void setBox(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.yellow), languageManager.getString("owner"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", Font.ITALIC, 12)));
    }

    public String getNameOwner(){
        if (isEnabled()) return nameTextField.getText();
        else return null;
    }
    public Date getBirthdayOwner(){
        if (isEnabled()) return (Date) dateSpinner.getValue();
        else return null;

    }
    public String getHeightOwner(){
        if (isEnabled()) return String.valueOf(heightSpinner.getValue());
        else return null;
    }
    public String getWeightOwner(){
        if (isEnabled()) return String.valueOf(weightSpinner.getValue());
        else return null;
    }
    public String getPassportOwner(){
        if (isEnabled()) return passportTextField.getText();
        else return null;
    }

    public void setEnabled(boolean b){
        super.setEnabled(b);
        heightSpinner.setEnabled(b);
        weightSpinner.setEnabled(b);
        dateSpinner.setEnabled(b);
        nameTextField.setEnabled(b);
        passportTextField.setEnabled(b);
        name.setEnabled(b);
        birthday.setEnabled(b);
        height.setEnabled(b);
        weight.setEnabled(b);
        passportId.setEnabled(b);
    }

    public void setProductInformation(int row, Table.MyTableModel tableModel){
        heightSpinner.setValue(tableModel.getValueAt(row, 11));
        weightSpinner.setValue(tableModel.getValueAt(row, 12));
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss", languageManager.getCurrentLocale());
        try {
            dateSpinner.setValue(format.parse(String.valueOf(tableModel.getValueAt(row, 10))));
        } catch (ParseException ignore) {}
        nameTextField.setText(String.valueOf(tableModel.getValueAt(row, 9)));
        passportTextField.setText(String.valueOf(tableModel.getValueAt(row, 13)));
    }

    private void configPanel(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(name, constraintsName);
        add(nameTextField, constraintsNameText);

        add(birthday, constraintsBirthday);
        add(dateSpinner, constraintsBirthdaySpinner);

        add(height, constraintsHeight);
        add(heightSpinner, constraintsHeightSpinner);

        add(weight, constraintsWeight);
        add(weightSpinner, constraintsWeightSpinner);

        add(passportId, constraintsPassportId);
        add(passportTextField, constraintsPassportIdTextField);
    }

    public void update(){
        removeAll();
        initDateSpinner();
        setNameLabels();
        setBox();
        configPanel();
    }
}
