package utilites;

import actionClient.CommandHandler;
import gui.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InvalidObjectException;

public class RemoveByIdListener implements ActionListener {

    private Table table;
    private LanguageManager languageManager;
    private CommandHandler commandHandler;
    private Table.MyTableModel tableModel;

    public RemoveByIdListener(Table table, Table.MyTableModel tableModel, LanguageManager languageManager, CommandHandler commandHandler){
        this.table = table;
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
        this.tableModel = tableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int [] rows = table.getTable().getSelectedRows();
        if (rows.length == 0){
            JOptionPane.showMessageDialog(table, languageManager.getString("Please, choose rows product that you want to delete. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
        }
        int column = 0;
        try {
            column = tableModel.getIdColumn();
        } catch (InvalidObjectException ex) {
            return;
        }
        for (int row: rows){
            if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand("removeById", String.valueOf(tableModel.getValueAt(row, column)), null))){
                JOptionPane.showMessageDialog(table, languageManager.getString("Remove success"), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(table, languageManager.getString("Remove failed, error is occurred"), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
        table.update();
    }
}
