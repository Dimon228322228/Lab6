package gui;

import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame{

    private final LanguageManager languageManager;

    private final String nameFrame;

    private final JPanel mainPanel = new JPanel();
    private final JPanel forCenteringMainPanel = new JPanel();
    private final JPanel forSeparateMainAndLanguagePanels = new JPanel();
    private final JPanel languagePanel = new JPanel();

    private JComboBoxLanguage languageBox;

    private final JButton registerButton = new JButton();
    private final JButton logButton = new JButton();
    private final JLabel loginLabel = new JLabel();
    private final JLabel passLabel = new JLabel();

    private final JTextField textFieldForUsername = new JTextField();
    private final JPasswordField passwordFieldForPassword = new JPasswordField();

    public RegistrationFrame(String nameFrame, LanguageManager languageManager){
        super(languageManager.getString(nameFrame));
        this.nameFrame = nameFrame;
        this.languageManager=languageManager;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width/2 - 250, screenSize.height/2 - 100, 500, 200);
        initComboBox();
        setTextForButtonSAndLabels();
        setDimensionTextField();
    }

    private void initComboBox(){
        languageBox = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTitle(languageManager.getString(nameFrame));
                setTextForButtonSAndLabels();
                pack();
                revalidate();
            }
        };
    }

    private void setTextForButtonSAndLabels(){
        registerButton.setText(languageManager.getString("registration"));
        logButton.setText(languageManager.getString("log_in"));
        loginLabel.setText(languageManager.getString("username"));
        passLabel.setText(languageManager.getString("password"));
    }

    private void setDimensionTextField(){
        textFieldForUsername.setMaximumSize(new Dimension(250, 1));
        passwordFieldForPassword.setMaximumSize(new Dimension(250, 1));
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

    private void configMainPanel(){
        GroupLayout layoutForMainPanel = new GroupLayout(mainPanel);

        layoutForMainPanel.setAutoCreateGaps(true);
        layoutForMainPanel.setAutoCreateContainerGaps(true);
        layoutForMainPanel.linkSize(SwingConstants.HORIZONTAL, passLabel, loginLabel);
        layoutForMainPanel.linkSize(SwingConstants.VERTICAL, textFieldForUsername, passwordFieldForPassword, logButton, registerButton);

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
                                .addComponent(logButton))));
        layoutForMainPanel.setVerticalGroup(layoutForMainPanel.createSequentialGroup()
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginLabel)
                        .addComponent(textFieldForUsername))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passLabel)
                        .addComponent(passwordFieldForPassword))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(registerButton)
                        .addComponent(logButton)));

    }

    public void setPanel(){
        configLanguagePanel();
        configMainPanel();
        configCenteringPanel(mainPanel);
        configSeparatedPanel();

        add(forSeparateMainAndLanguagePanels);
        pack();
        revalidate();
    }
}
