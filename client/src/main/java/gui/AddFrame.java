package gui;

import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;

public class AddFrame extends ProductFrame{
    public AddFrame(LanguageManager languageManager, CommandHandler commandHandler, String commandName) {
        super(languageManager, commandHandler, commandName);
    }

    protected void setNameButton(){
        someActionButton.setText(languageManager.getString("add"));
        cancel.setText(languageManager.getString("cancel"));
    }

    protected void setActionButton(String commandName){
        someActionButton.addActionListener(e -> {
            if (createProduct()) {
                if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand(commandName, null, product))){
                    JOptionPane.showMessageDialog(this, languageManager.getString("Added success. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
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
