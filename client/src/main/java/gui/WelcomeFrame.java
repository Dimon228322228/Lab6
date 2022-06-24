package gui;

import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;

public class WelcomeFrame extends RegistrationFrame{
    public WelcomeFrame(String nameFrame, LanguageManager languageManager, CommandHandler commandHandler) {
        super(nameFrame, languageManager, commandHandler);
        configMainPanel();
    }

    protected void configMainPanel(){
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
        pack();
        revalidate();
    }
}
