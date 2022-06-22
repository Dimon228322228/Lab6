package gui;

import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

public class RegistrationFrame extends JFrame{
    private final LanguageManager languageManager;
    private String name;
    private JPanel mainPanel;
    private JPanel forCenteringMainPanel;
    private JPanel forSeparateMainAndLanguagePanels;
    private JPanel languagePanel;
    JComboBox<String> changeLanguageCheckBox;

    public RegistrationFrame(String name, LanguageManager languageManager){
        super(languageManager.getString(name));
        this.name = name;
        this.languageManager=languageManager;
        setComboBox();
    }

    private void setComboBox(){
        changeLanguageCheckBox = new JComboBox<>();
        changeLanguageCheckBox.addItem(languageManager.getString("ru_RU"));
        changeLanguageCheckBox.addItem(languageManager.getString("el"));
        changeLanguageCheckBox.addItem(languageManager.getString("es_PA"));
        changeLanguageCheckBox.addItem(languageManager.getString("is_IS"));
        changeLanguageCheckBox.setSelectedItem(languageManager.getLocaleName());
        changeLanguageCheckBox.setMaximumSize(new Dimension(5, 20));
        changeLanguageCheckBox.setMinimumSize(new Dimension(1, 20));
        changeLanguageCheckBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = (String) changeLanguageCheckBox.getSelectedItem();
                System.out.println(str);
                languageManager.setLocalByString(str);
                setTitle(languageManager.getString(name));
                setPanel();
            }
        });
    }

    public void setPanel(){
        this.getContentPane().removeAll();
        mainPanel = new JPanel();
        forCenteringMainPanel = new JPanel();
        forSeparateMainAndLanguagePanels = new JPanel();
        languagePanel = new JPanel();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        setBounds(sizeScreen.width/2 - 250, sizeScreen.height/2 - 100, 500, 200);
        JButton registerButton = new JButton(languageManager.getString("registration"));

        JButton logButton = new JButton(languageManager.getString("log_in"));

        JLabel loginButton = new JLabel(languageManager.getString("username"));

        JLabel passButton = new JLabel(languageManager.getString("password"));

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
