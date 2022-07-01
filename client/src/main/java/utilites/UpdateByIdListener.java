package utilites;

import actionClient.CommandHandler;
import gui.ProductFrame;
import gui.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InvalidObjectException;

public class UpdateByIdListener implements ActionListener {

    private Table table;
    private LanguageManager languageManager;
    private CommandHandler commandHandler;
    private Table.MyTableModel tableModel;

    public UpdateByIdListener(Table table, Table.MyTableModel tableModel, LanguageManager languageManager, CommandHandler commandHandler){
        this.table = table;
        this.languageManager = languageManager;
        this.tableModel = tableModel;
        this.commandHandler = commandHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int [] rows = table.getTable().getSelectedRows();
        if (rows.length == 0){
            JOptionPane.showMessageDialog(table, languageManager.getString("Please, choose row product for update. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
        } else if (rows.length != 1){
            JOptionPane.showMessageDialog(table, languageManager.getString("Please, choose one row. "), languageManager.getString("error"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            table.setEnabled(false);
            ProductFrame productFrame = new ProductFrame(languageManager, commandHandler) {
                @Override
                protected void setActionButton() {
                    someActionButton.addActionListener(e -> {
                        if (createProduct()) {
                            if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand("updateById", String.valueOf(id), product))){
                                JOptionPane.showMessageDialog(this, languageManager.getString("Update success. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                            }
                            setVisible(false);
                        }
                        else JOptionPane.showMessageDialog(this, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
                    });
                }
            };
            productFrame.setNameSomeActionButton("updateById");
            productFrame.setProductInformation(rows[0], tableModel);
            try {
                productFrame.setId((Long) tableModel.getValueAt(rows[0], tableModel.getIdColumn()));
            } catch (InvalidObjectException ex) { return;}
            productFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    table.setEnabled(true);
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        }
    }
}
