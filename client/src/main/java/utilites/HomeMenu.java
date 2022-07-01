package utilites;

import lombok.Getter;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class HomeMenu extends JMenuBar{
    @Getter private final JMenuItem tableItem = new JMenuItem();
    @Getter private final JMenuItem coordinate = new JMenuItem();
    @Getter private final JMenuItem executeScript = new JMenuItem();
    @Getter private final JMenuItem help = new JMenuItem();
    @Getter private final JMenuItem history = new JMenuItem();
    @Getter private final JMenuItem add = new JMenuItem();
    @Getter private final JMenuItem addIfMax = new JMenuItem();
    @Getter private final JMenuItem clear = new JMenuItem();
    @Getter private final JMenuItem countByManufactureCost = new JMenuItem();
    @Getter private final JMenuItem countGreaterThenUnit = new JMenuItem();
    @Getter private final JMenuItem displayInfo = new JMenuItem();
    @Getter private final JMenuItem removeById = new JMenuItem();
    @Getter private final JMenuItem removeLower = new JMenuItem();
    @Getter private final JMenuItem updateById = new JMenuItem();
    @Getter private final JMenu changeAccount;
    private final JMenu product;
    private final JMenu commands;
    private final JMenu server;
    private final JMenu client;
    private final JMenu exit;
    private final LanguageManager languageManager;


    public HomeMenu(LanguageManager languageManager){
        this.languageManager = languageManager;
        tableItem.setText(languageManager.getString("table"));
        coordinate.setText(languageManager.getString("coordinate"));

        product = new JMenu(languageManager.getString("view"));
        product.setMnemonic('2');
        product.add(tableItem);
        product.add(coordinate);

        changeAccount = new JMenu(languageManager.getString("change_account"));
        changeAccount.setMnemonic('1');

        commands = new JMenu(languageManager.getString("commands"));
        commands.setMnemonic('3');

        server = new JMenu(languageManager.getString("server"));
        client = new JMenu(languageManager.getString("client"));

        add.setText(languageManager.getString("add"));
        addIfMax.setText(languageManager.getString("addIfMax"));
        clear.setText(languageManager.getString("clear"));
        countByManufactureCost.setText(languageManager.getString("countByManufactureCost"));
        countGreaterThenUnit.setText(languageManager.getString("countGreaterThenUnit"));
        displayInfo.setText(languageManager.getString("displayInfo"));
        removeById.setText(languageManager.getString("removeById"));
        removeLower.setText(languageManager.getString("removeLower"));
        updateById.setText(languageManager.getString("updateById"));
        executeScript.setText(languageManager.getString("executeScript"));
        help.setText(languageManager.getString("help"));
        history.setText(languageManager.getString("history"));

        server.add(add);
        server.add(addIfMax);
        server.add(clear);
        server.add(countByManufactureCost);
        server.add(countGreaterThenUnit);
        server.add(displayInfo);
        server.add(removeById);
        server.add(removeLower);
        server.add(updateById);

        client.add(executeScript);
        client.add(help);
        client.add(history);

        commands.add(client);
        commands.add(server);

        exit = new JMenu(languageManager.getString("exit"));
        exit.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (JOptionPane.showOptionDialog(this, languageManager.getString("Do you want exit?"), "exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        exit.setMnemonic('4');

        add(changeAccount);
        add(product);
        add(commands);
        add(exit);
    }

    public void update() {
        tableItem.setText(languageManager.getString("table"));
        coordinate.setText(languageManager.getString("coordinate"));
        product.setText(languageManager.getString("view"));
        changeAccount.setText(languageManager.getString("change_account"));
        commands.setText(languageManager.getString("commands"));
        server.setText(languageManager.getString("server"));
        client.setText(languageManager.getString("client"));
        add.setText(languageManager.getString("add"));
        addIfMax.setText(languageManager.getString("addIfMax"));
        clear.setText(languageManager.getString("clear"));
        countByManufactureCost.setText(languageManager.getString("countByManufactureCost"));
        countGreaterThenUnit.setText(languageManager.getString("countGreaterThenUnit"));
        displayInfo.setText(languageManager.getString("displayInfo"));
        removeById.setText(languageManager.getString("removeById"));
        removeLower.setText(languageManager.getString("removeLower"));
        updateById.setText(languageManager.getString("updateById"));
        executeScript.setText(languageManager.getString("executeScript"));
        help.setText(languageManager.getString("help"));
        history.setText(languageManager.getString("history"));
        exit.setText(languageManager.getString("exit"));
        repaint();
    }
}
