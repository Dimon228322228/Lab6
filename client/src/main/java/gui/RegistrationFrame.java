package gui;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame{
    private final JPanel mainPanel = new JPanel();
    private final JPanel forCenteringMainPanel = new JPanel();
    private final JPanel forSeparateMainAndLanguagePanels = new JPanel();
    private final JPanel languagePanel = new JPanel();

    public RegistrationFrame(){
        super("Hello");
    }

    public void setPanel(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        setBounds(sizeScreen.width/2 - 250, sizeScreen.height/2 - 100, 500, 200);
        JButton registerButton = new JButton("Registration");

        JButton logButton = new JButton("Log in");

        JComboBox<String> changeLanguageCheckBox = new JComboBox<>();
        changeLanguageCheckBox.addItem("ru");
        changeLanguageCheckBox.addItem("el");
        changeLanguageCheckBox.addItem("ca_ES");
        changeLanguageCheckBox.addItem("is_IS");
        changeLanguageCheckBox.setMaximumSize(new Dimension(5, 20));
        changeLanguageCheckBox.setMinimumSize(new Dimension(1, 20));

        JLabel loginButton = new JLabel("username");

        JLabel passButton = new JLabel("password");

        JTextField textFieldForUsername = new JTextField(registerButton.getWidth());
        textFieldForUsername.setMaximumSize(new Dimension(250, 1));

        JPasswordField passwordFieldForPassword = new JPasswordField(10);
        passwordFieldForPassword.setMaximumSize(new Dimension(250, 1));

        BoxLayout layoutForCentering = new BoxLayout(languagePanel, BoxLayout.LINE_AXIS);
        languagePanel.setLayout(layoutForCentering);
        languagePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        languagePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        languagePanel.add(changeLanguageCheckBox);
        languagePanel.add(Box.createHorizontalGlue());

        forCenteringMainPanel.setLayout(new BoxLayout(forCenteringMainPanel, BoxLayout.PAGE_AXIS));
        forCenteringMainPanel.add(Box.createVerticalGlue());
        forCenteringMainPanel.add(mainPanel);
        forCenteringMainPanel.add(Box.createVerticalGlue());

        forSeparateMainAndLanguagePanels.setLayout(new BorderLayout());
        forSeparateMainAndLanguagePanels.add(forCenteringMainPanel, BorderLayout.CENTER);
        forSeparateMainAndLanguagePanels.add(languagePanel,BorderLayout.NORTH);

        GroupLayout layoutForMainPanel = new GroupLayout(mainPanel);
        mainPanel.setLayout(layoutForMainPanel);
        layoutForMainPanel.setHorizontalGroup(layoutForMainPanel.createSequentialGroup()
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(loginButton)
                        .addComponent(passButton))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(textFieldForUsername)
                        .addComponent(passwordFieldForPassword)
                        .addGroup(layoutForMainPanel.createSequentialGroup()
                                .addComponent(registerButton)
                                .addComponent(logButton))));
        layoutForMainPanel.setVerticalGroup(layoutForMainPanel.createSequentialGroup()
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(textFieldForUsername))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passButton)
                        .addComponent(passwordFieldForPassword))
                .addGroup(layoutForMainPanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(registerButton)
                        .addComponent(logButton)));

        layoutForMainPanel.setAutoCreateGaps(true);
        layoutForMainPanel.setAutoCreateContainerGaps(true);
        layoutForMainPanel.linkSize(SwingConstants.HORIZONTAL, passButton, loginButton);
        layoutForMainPanel.linkSize(SwingConstants.VERTICAL, textFieldForUsername, passwordFieldForPassword, logButton, registerButton);

        add(forSeparateMainAndLanguagePanels);
        pack();
        revalidate();
    }
}
