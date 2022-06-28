package gui;

import action.ResultAction;
import action.State;
import actionClient.CommandHandler;
import authentication.Account;
import authentication.CurrentAccount;
import authentication.TypeAuthentication;
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
    protected final JLabel loginLabel = new JLabel();
    protected final JLabel passLabel = new JLabel();

    protected final JTextField textFieldForUsername = new JTextField();
    protected final JPasswordField passwordFieldForPassword = new JPasswordField();

    public RegistrationFrame(String nameFrame, LanguageManager languageManager, CommandHandler commandHandler){
        super(languageManager.getString(nameFrame));
        this.nameFrame = nameFrame;
        this.languageManager=languageManager;
        this.commandHandler = commandHandler;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2 - 250, screenSize.height/2 - 100, 500, 200);
        initComboBox();
        setTextForButtonSAndLabels();
        setDimensionTextField();
        configLanguagePanel();
        configCenteringPanel(mainPanel);
        configSeparatedPanel();
        setButtonAction();

        add(forSeparateMainAndLanguagePanels);
        pack();
        revalidate();
    }

    protected void initComboBox(){
        languageBox = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTitle(languageManager.getString(nameFrame));
                setTextForButtonSAndLabels();
                repaintChildrenComponent();
                SwingApp.repaintAll();
                pack();
                revalidate();
            }
        };
    }

    protected void repaintChildrenComponent(){}

    private void setTextForButtonSAndLabels(){
        registerButton.setText(languageManager.getString("registration"));
        logButton.setText(languageManager.getString("log_in"));
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
    }

    private void handleRegistration(ResultAction resultAction){
        if (resultAction.getState().equals(State.SUCCESS)) {
            SwingApp.setHomeFrame();
            setVisible(false);
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
        repaintChildrenComponent();
        pack();
        revalidate();
    }
}
