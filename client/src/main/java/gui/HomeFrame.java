package gui;

import action.ResultAction;
import action.State;
import actionClient.CommandHandler;
import authentication.CurrentAccount;
import lombok.Getter;
import lombok.Setter;
import utilites.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.Optional;

public class HomeFrame extends JFrame {

    private final LanguageManager languageManager;
    private final CommandHandler commandHandler;

    private final JPanel mainPanel = new JPanel();
    private final JPanel titlePanel = new JPanel();
    private final JPanel helpPanel = new JPanel();

    private final JMenuItem tableItem = new JMenuItem();
    private final JMenuItem coordinate = new JMenuItem();

    private final JMenuItem executeScript = new JMenuItem();
    private final JMenuItem help = new JMenuItem();
    private final JMenuItem history = new JMenuItem();
    private final JMenuItem add = new JMenuItem();
    private final JMenuItem addIfMax = new JMenuItem();
    private final JMenuItem clear = new JMenuItem();
    private final JMenuItem countByManufactureCost = new JMenuItem();
    private final JMenuItem countGreaterThenUnit = new JMenuItem();
    private final JMenuItem displayInfo = new JMenuItem();
    private final JMenuItem removeById = new JMenuItem();
    private final JMenuItem removeLower = new JMenuItem();
    private final JMenuItem updateById = new JMenuItem();

    private final Table table;
    private final CoordinatePane coordinatePane;

    private JComboBoxLanguage languageBox;

    @Getter @Setter private UpdatablePanel bodyPanel = new UpdatablePanel();
    @Setter private JLabel username = new JLabel("");

    public HomeFrame(LanguageManager languageManager, CommandHandler commandHandler){
        super(languageManager.getString("home"));
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
        table = new Table(new Reflector(languageManager), languageManager, commandHandler){
            public void setEnabled(boolean b){
                setEnabledHomeFrame(b);
            }
        };
        coordinatePane = new CoordinatePane(languageManager);
        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(sizeScreen.width/2 - 325, sizeScreen.height/2 - 200, 750, 400);
        setMenu();
        initLanguageBox();
        setTable();
    }

    private void setEnabledHomeFrame(boolean b){
        setEnabled(b);
        mainPanel.setEnabled(b);
    }

    private void initLanguageBox(){
        languageBox = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                bodyPanel.update();
                setTitle(languageManager.getString("home"));
                setMenu();
                setUsernameLabel();
                setHomePanel();
            }
        };
    }

    private void setNameItems(){
        tableItem.setText(languageManager.getString("table"));
        coordinate.setText(languageManager.getString("coordinate"));
    }

    public void setTable(){
        table.updateTable();
        setBodyPanel(table);
        setHomePanel();
    }

    private void setMenu(){
        setNameItems();
        tableItem.addActionListener(e -> {
                setTable();
        });
        coordinate.addActionListener(e -> {
            coordinatePane.update();
            setBodyPanel(coordinatePane);
            setHomePanel();
        });

        JMenu product = new JMenu(languageManager.getString("view"));
        product.setMnemonic('2');
        product.add(tableItem);
        product.add(coordinate);

        JMenu changeAccount = new JMenu(languageManager.getString("change_account"));
        changeAccount.setMnemonic('1');

        changeAccount.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                ChangeAccountFrame frame = new ChangeAccountFrame("change_account", languageManager, commandHandler);
                frame.setVisible(true);
            }
        });

        JMenu commands = new JMenu(languageManager.getString("commands"));
        commands.setMnemonic('3');

        JMenu server = new JMenu(languageManager.getString("server"));
        JMenu client = new JMenu(languageManager.getString("client"));

        setCommandsAction();
        setNameCommands();

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


        JMenu exit = new JMenu(languageManager.getString("exit"));
        exit.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (JOptionPane.showOptionDialog(this, languageManager.getString("Do you want exit?"), "exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        exit.setMnemonic('4');

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(changeAccount);
        menuBar.add(product);
        menuBar.add(commands);
        menuBar.add(exit);
        setJMenuBar(menuBar);
    }

    private void setNameCommands(){
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
    }

    public void setCommandsAction(){
        add.addActionListener(new AddListener(table, languageManager, commandHandler));
        addIfMax.addActionListener(e -> {
            AddFrame productFrame = new AddFrame(languageManager, commandHandler, "addIfMax");
            productFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    table.setEnabled(true);
                    table.updateTable();
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        });
        clear.addActionListener(e -> {
            commandHandler.handleServerCommand("clear", null, null);
            bodyPanel.update();
        });
        countByManufactureCost.addActionListener(e -> {
            String str = JOptionPane.showInputDialog(this, "input cost");
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("countByManufactureCost", str, null);
            ResultAction resultAction = resultActionOptional.orElseGet(() -> new ResultAction(State.FAILED, ""));
            if (resultAction.getState().equals(State.SUCCESS)){
                JOptionPane.showMessageDialog(this, "found " + resultAction.getCollection().size() + " element", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error has occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        countGreaterThenUnit.addActionListener(e -> {
            ChooseUnitFrame chooseUnitFrame = new ChooseUnitFrame(languageManager);
            chooseUnitFrame.createFrame();
            chooseUnitFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    if (chooseUnitFrame.isReady()){
                        Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("CountGreaterThenUnitOfMeasure", chooseUnitFrame.getUnitProduct().getTitle(), null);
                        ResultAction resultAction = resultActionOptional.orElseGet(() -> new ResultAction(State.FAILED, ""));
                        if (resultAction.getState().equals(State.SUCCESS)){
                            JOptionPane.showMessageDialog(bodyPanel, "found " + resultAction.getCollection().size() + " element", "Result", JOptionPane.INFORMATION_MESSAGE);
                        } else JOptionPane.showMessageDialog(bodyPanel, "error has occurred", "Result", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });
        displayInfo.addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("info", null, null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        removeById.addActionListener(e -> {
            String str = JOptionPane.showInputDialog(this, "input id: ", "Id", JOptionPane.QUESTION_MESSAGE);
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("removeById", str, null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
            table.update();
        });
        removeLower.addActionListener(e -> {
            RemoveLowerFrame productFrame = new RemoveLowerFrame(languageManager, commandHandler, "removeLower");
            productFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    table.setEnabled(true);
                    table.updateTable();
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        });
        if (bodyPanel instanceof Table){
            updateById.addActionListener(new UpdateByIdListener(table, table.getTableModel(), languageManager, commandHandler));
        } else updateById.addActionListener(e ->{});

        executeScript.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // i = 0 choose, i = 1 not choose
            if (chooser.showOpenDialog(this) == 0){
                File file = chooser.getSelectedFile();
                Optional<ResultAction> resultActionOptional = commandHandler.handleUserCommand("executeScript", file.getAbsolutePath());
                if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                    JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
                } else JOptionPane.showMessageDialog(this, "Error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
                bodyPanel.update();
            }
        });

        help.addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleUserCommand("help", null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        history.addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleUserCommand("history", null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void setHomePanel(){
        mainPanel.removeAll();
        helpPanel.removeAll();

        setUsernameLabel();

        helpPanel.setLayout(new BorderLayout());
        helpPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.BLACK,5,0.1f), bodyPanel.getName()));
        helpPanel.add(bodyPanel, BorderLayout.CENTER);

        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainLayout);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
                .addComponent(titlePanel)
                .addComponent(helpPanel));
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addComponent(titlePanel)
                .addComponent(helpPanel)
        );

        add(mainPanel);
        pack();
        revalidate();
    }

    private void setUsernameLabel(){
        titlePanel.removeAll();
        String account_name = CurrentAccount.getAccount() == null ? "" : CurrentAccount.getAccount().getName();
        JLabel user = new JLabel(languageManager.getString("username") + ": " + account_name);
        BoxLayout titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.add(languageBox);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(user);
        titlePanel.add(username);
    }

    public void repaint(){
        super.repaint();
        bodyPanel.update();
        languageBox.setSelectedItem(languageManager.getLocaleName());
        setTitle(languageManager.getString("home"));
        setMenu();
        setUsernameLabel();
        setHomePanel();
        dispose();
    }
}
