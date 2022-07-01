package gui;

import action.ResultAction;
import action.State;
import actionClient.CommandHandler;
import authentication.CurrentAccount;
import lombok.Getter;
import lombok.Setter;
import utilites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Optional;

public class HomeFrame extends JFrame {

    private final LanguageManager languageManager;
    private final CommandHandler commandHandler;

    private final JPanel mainPanel = new JPanel();
    private final JPanel titlePanel = new JPanel();
    private final JPanel helpPanel = new JPanel();

    private final Table table;
    private final CoordinatePane coordinatePane;
    private final HomeMenu homeMenu;

    private JComboBoxLanguage languageBox;

    @Getter @Setter private UpdatablePanel bodyPanel = new UpdatablePanel();
    @Setter private JLabel username = new JLabel("");

    private final CollectionManagerClient collectionManagerClient;

    public HomeFrame(LanguageManager languageManager, CommandHandler commandHandler){
        super(languageManager.getString("home"));
        this.languageManager = languageManager;
        this.commandHandler = commandHandler;
        homeMenu = new HomeMenu(languageManager);
        collectionManagerClient = new CollectionManagerClient(commandHandler);

        table = new Table(languageManager, commandHandler, collectionManagerClient){
            public void setEnabled(boolean b){
                setEnabledHomeFrame(b);
            }
        };

        coordinatePane = new CoordinatePane(languageManager, collectionManagerClient);
        collectionManagerClient.subscribe(table);
        collectionManagerClient.subscribe(coordinatePane);
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
                repaint();
            }
        };
    }

    public void setTable(){
        setBodyPanel(table);
        setHomePanel();
    }

    private void setMenu(){
        homeMenu.getAdd().addActionListener(e -> {
            table.setEnabled(false);
            ProductFrame productFrame = new ProductFrame(languageManager, commandHandler) {
                @Override
                protected void setActionButton() {
                    someActionButton.addActionListener(e -> {
                        if (createProduct()) {
                            if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand("add", null, product))){
                                JOptionPane.showMessageDialog(table, languageManager.getString("Added success. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                            }
                            dispose();
                        }
                        else JOptionPane.showMessageDialog(table, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
                    });
                }
            };
            productFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    table.setEnabled(true);
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        });
        homeMenu.getAddIfMax().addActionListener(e -> {
            ProductFrame productFrame = new ProductFrame(languageManager, commandHandler) {
                @Override
                protected void setActionButton() {
                    someActionButton.addActionListener(e -> {
                        if (createProduct()) {
                            if (commandHandler.handleResultActionForGUI(commandHandler.handleServerCommand("addIfMax", null, product))){
                                JOptionPane.showMessageDialog(this, languageManager.getString("Added success. "), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                            }
                            dispose();
                        }
                        else JOptionPane.showMessageDialog(this, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
                    });
                }
            };
            productFrame.setNameSomeActionButton(languageManager.getString("addIfMax"));
            productFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    table.setEnabled(true);
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        });
        homeMenu.getClear().addActionListener(e -> {
            commandHandler.handleServerCommand("clear", null, null);
        });
        homeMenu.getCountByManufactureCost().addActionListener(e -> {
            String str = JOptionPane.showInputDialog(this, "input cost");
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("countByManufactureCost", str, null);
            ResultAction resultAction = resultActionOptional.orElseGet(() -> new ResultAction(State.FAILED, ""));
            if (resultAction.getState().equals(State.SUCCESS)){
                JOptionPane.showMessageDialog(this, "found " + resultAction.getCollection().size() + " element", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error has occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        homeMenu.getCountGreaterThenUnit().addActionListener(e -> {
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
        homeMenu.getDisplayInfo().addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("info", null, null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        homeMenu.getRemoveById().addActionListener(e -> {
            String str = JOptionPane.showInputDialog(this, "input id: ", "Id", JOptionPane.QUESTION_MESSAGE);
            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("removeById", str, null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        homeMenu.getRemoveLower().addActionListener(e -> {
            ProductFrame productFrame = new ProductFrame(languageManager, commandHandler) {
                @Override
                protected void setActionButton() {
                    someActionButton.addActionListener(e -> {
                        if (createProduct()) {
                            Optional<ResultAction> resultActionOptional = commandHandler.handleServerCommand("removeLower", null, product);
                            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                                JOptionPane.showMessageDialog(this, languageManager.getString("Remove " + resultActionOptional.get().getCollection().size() + " element(-s) deleted"), languageManager.getString("info"), JOptionPane.INFORMATION_MESSAGE);
                            }
                            setVisible(false);
                        }
                        else JOptionPane.showMessageDialog(this, languageManager.getString("Something error is occurred. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
                    });
                }
            };
            productFrame.setNameSomeActionButton(languageManager.getString("removeLower"));
            productFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    table.setEnabled(true);
                }
            });
            productFrame.createFrame();
            productFrame.setVisible(true);
        });
        if (bodyPanel instanceof Table){
            homeMenu.getUpdateById().addActionListener(new UpdateByIdListener(table, table.getTableModel(), languageManager, commandHandler));
        } else homeMenu.getUpdateById().addActionListener(e ->{});

        homeMenu.getExecuteScript().addActionListener(e -> {
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
            }
        });

        homeMenu.getHelp().addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleUserCommand("help", null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        homeMenu.getHistory().addActionListener(e -> {
            Optional<ResultAction> resultActionOptional = commandHandler.handleUserCommand("history", null);
            if (commandHandler.handleResultActionForGUI(resultActionOptional)){
                JOptionPane.showMessageDialog(this, resultActionOptional.get().getDescription(), "Result", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this, "error is occurred", "Result", JOptionPane.ERROR_MESSAGE);
        });
        homeMenu.getTableItem().addActionListener(e -> {
            setTable();
        });
        homeMenu.getCoordinate().addActionListener(e -> {
            setBodyPanel(coordinatePane);
            setHomePanel();
        });
        homeMenu.getChangeAccount().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                RegistrationFrame frame = new RegistrationFrame("change_account", languageManager, commandHandler);
                frame.getChangeAccountFrame();
            }
        });

        setJMenuBar(homeMenu);
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
        bodyPanel.updateLanguage();
        languageBox.setSelectedItem(languageManager.getLocaleName());
        setTitle(languageManager.getString("home"));
        setUsernameLabel();
        homeMenu.update();
        setHomePanel();
    }
}
