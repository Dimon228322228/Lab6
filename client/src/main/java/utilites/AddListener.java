package utilites;

import actionClient.CommandHandler;
import gui.AddFrame;
import gui.Table;

import java.awt.event.*;

public class AddListener implements ActionListener{

    private Table table;
    private LanguageManager languageManager;
    private CommandHandler commandHandler;

    public AddListener(Table table, LanguageManager languageManager, CommandHandler commandHandler){
        this.table = table;
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        table.setEnabled(false);
        AddFrame productFrame = new AddFrame(languageManager, commandHandler, "add");
        productFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                table.setEnabled(true);
                table.updateTable();
            }
        });
        productFrame.createFrame();
        productFrame.setVisible(true);
    }
}
