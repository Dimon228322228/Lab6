package gui;

import actionClient.CommandHandler;
import lombok.Setter;
import utilites.LanguageManager;

import javax.swing.*;

public class UpdateByIdFrame extends ProductFrame{
    @Setter private long id;

    public UpdateByIdFrame(LanguageManager languageManager, CommandHandler commandHandler, String commandName) {
        super(languageManager, commandHandler, commandName);
        pack();
        revalidate();
    }

    @Override
    protected void setNameButton() {
        someActionButton.setText(languageManager.getString("update"));
        cancel.setText(languageManager.getString("cancel"));
    }

    @Override
    protected void setActionButton(String commandName) {
        someActionButton.addActionListener(e -> {
            if (createProduct()) {
                if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand(commandName, String.valueOf(id), product))){
                    JOptionPane.showMessageDialog(this, languageManager.getString("Update success. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                }
                setVisible(false);
            }
            else JOptionPane.showMessageDialog(this, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
        });
        cancel.addActionListener(e -> {
            setVisible(false);
        });
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
