package gui;

import action.ResultAction;
import action.State;
import actionClient.CommandHandler;
import authentication.Account;
import authentication.CurrentAccount;
import authentication.TypeAuthentication;
import utilites.CollectionManagerClient;
import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame{

    protected final LanguageManager languageManager;
    protected final CommandHandler commandHandler;

    protected final String nameFrame;

    protected final JPanel mainPanel = new JPanel();
    protected final JPanel forCenteringMainPanel = new JPanel();
    protected final JPanel forSeparateMainAndLanguagePanels = new JPanel();
    protected final JPanel languagePanel = new JPanel();

    protected JComboBoxLanguage languageBox;

    protected final JButton registerButton = new JButton();
    protected final JButton logButton = new JButton();
    private final JButton cancelButton = new JButton();
    protected final JLabel loginLabel = new JLabel();
    protected final JLabel passLabel = new JLabel();

    protected final JTextField textFieldForUsername = new JTextField();
    protected final JPasswordField passwordFieldForPassword = new JPasswordField();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public RegistrationFrame(String nameFrame, LanguageManager languageManager, CommandHandler commandHandler){
        super(languageManager.getString(nameFrame));
        this.nameFrame = nameFrame;
        this.languageManager=languageManager;
        this.commandHandler = commandHandler;
    }

    private void initRegistrationFrame(){
        initComboBox();
        setTextForButtonSAndLabels();
        setDimensionTextField();
        configLanguagePanel();
        configCenteringPanel(mainPanel);
        configSeparatedPanel();
        configMainPanel();
        setButtonAction();
        pack();
    }

    public void getWelcomeFrame(){
        initRegistrationFrame();
        cancelButton.setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createDialog();
    }

    public void getChangeAccountFrame(){
        initRegistrationFrame();
        cancelButton.setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createDialog();
    }

    private void createDialog(){
        setBounds(screenSize.width/2 - 250, screenSize.height/2 - 100, 500, 200);
        add(forSeparateMainAndLanguagePanels);
        pack();
        setVisible(true);
    }

    protected void initComboBox(){
        languageBox = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTextForButtonSAndLabels();
                setTitle(languageManager.getString(nameFrame));
                pack();
                revalidate();
            }
        };
    }

    private void setTextForButtonSAndLabels(){
        registerButton.setText(languageManager.getString("registration"));
        logButton.setText(languageManager.getString("log_in"));
        cancelButton.setText(languageManager.getString("cancel"));
        loginLabel.setText(languageManager.getString("username"));
        passLabel.setText(languageManager.getString("password"));
    }

    private void setButtonAction(){
        registerButton.addActionListener(e -> {
            String login = textFieldForUsername.getText();
            String password = new String(passwordFieldForPassword.getPassword());
            if (!password.equals("")){
                CurrentAccount.setPreviousAccount(CurrentAccount.getAccount());
                CurrentAccount.setAccount(new Account(login, password, TypeAuthentication.REGISTRATION));
                handleRegistration(commandHandler.login());
            } else JOptionPane.showMessageDialog(this, languageManager.getString("The password must contain at least one character. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
        });
        logButton.addActionListener(e -> {
            String login = textFieldForUsername.getText();
            String password = new String(passwordFieldForPassword.getPassword());
            if (!password.equals("")){
                CurrentAccount.setPreviousAccount(CurrentAccount.getAccount());
                CurrentAccount.setAccount(new Account(login, password, TypeAuthentication.LOGIN));
                handleRegistration(commandHandler.login());
            } else JOptionPane.showMessageDialog(this, languageManager.getString("The password must contain at least one character. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
        });
        cancelButton.addActionListener(e -> {
            dispose();
        });
    }

    private void handleRegistration(ResultAction resultAction){
        if (resultAction.getState().equals(State.SUCCESS)) {
            SwingApp.setHomeFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultAction.getDescription(), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
            CurrentAccount.setAccount(CurrentAccount.getPreviousAccount());
        }
    }

    private void setDimensionTextField(){
        textFieldForUsername.setMaximumSize(new Dimension(350, 1));
        passwordFieldForPassword.setMaximumSize(new Dimension(350, 1));
    }

    private void configLanguagePanel(){
        BoxLayout layoutForCentering = new BoxLayout(languagePanel, BoxLayout.LINE_AXIS);
        languagePanel.setLayout(layoutForCentering);
        languagePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        languagePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        languagePanel.add(languageBox);
        languagePanel.add(Box.createHorizontalGlue());
    }

    private void configCenteringPanel(JPanel panel){
        forCenteringMainPanel.setLayout(new BoxLayout(forCenteringMainPanel, BoxLayout.PAGE_AXIS));
        forCenteringMainPanel.add(Box.createVerticalGlue());
        forCenteringMainPanel.add(panel);
        forCenteringMainPanel.add(Box.createVerticalGlue());
    }

    private void configSeparatedPanel(){
        forSeparateMainAndLanguagePanels.setLayout(new BorderLayout());
        forSeparateMainAndLanguagePanels.add(forCenteringMainPanel, BorderLayout.CENTER);
        forSeparateMainAndLanguagePanels.add(languagePanel,BorderLayout.NORTH);
    }

    public void repaint(){
        super.repaint();
        languageBox.setSelectedItem(languageManager.getLocaleName());
        setTitle(languageManager.getString(nameFrame));
        setTextForButtonSAndLabels();
        pack();
    }

    protected void configMainPanel(){
        GroupLayout layoutForMainPanel = new GroupLayout(mainPanel);

        layoutForMainPanel.setAutoCreateGaps(true);
        layoutForMainPanel.setAutoCreateContainerGaps(true);
        layoutForMainPanel.linkSize(SwingConstants.HORIZONTAL, passLabel, loginLabel);
        layoutForMainPanel.linkSize(SwingConstants.VERTICAL, textFieldForUsername, passwordFieldForPassword, logButton, registerButton, cancelButton);

        mainPanel.setLayout(layoutForMainPanel);
        layoutForMainPanel.setHorizontalGroup(layoutForMainPanel.createSequentialGroup()
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(loginLabel)
                        .addComponent(passLabel))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(textFieldForUsername)
                        .addComponent(passwordFieldForPassword)
                        .addGroup(layoutForMainPanel.createSequentialGroup()
                                .addComponent(registerButton)
                                .addComponent(logButton)
                                .addComponent(cancelButton))));
        layoutForMainPanel.setVerticalGroup(layoutForMainPanel.createSequentialGroup()
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginLabel)
                        .addComponent(textFieldForUsername))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passLabel)
                        .addComponent(passwordFieldForPassword))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(registerButton)
                        .addComponent(logButton)
                        .addComponent(cancelButton)));
        pack();
    }
}
