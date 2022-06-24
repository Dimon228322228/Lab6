package gui;

import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;

public class ChangeAccountFrame extends RegistrationFrame{

    private final JButton cancelButton = new JButton();

    public ChangeAccountFrame(String nameFrame, LanguageManager languageManager, CommandHandler commandHandler) {
        super(nameFrame, languageManager, commandHandler);
        initCancelButton();
        configMainPanel();
    }

    private void initCancelButton(){
        cancelButton.setText(languageManager.getString("cancel"));
        cancelButton.addActionListener(e -> {
            setVisible(false);
        });
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
        revalidate();
    }

    protected void repaintChildrenComponent(){
        initCancelButton();
        configMainPanel();
    }
}
