package gui;

import util.LanguageManager;

import javax.swing.*;


public class SwingApp {
    public SwingApp() {
        HomeFrame home = HomeFrame.getInstance();

        RegistrationFrame registrationFrame = new RegistrationFrame();
        registrationFrame.setPanel();

        home.setHomePanel(new Table(new Reflector(new LanguageManager())));
//        устанавливает название фрейма как название кнопки
//        findButton.addActionListener(EventHandler.create(ActionListener.class, frame, "title", "source.text"));
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setVisible(true);

        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setVisible(true);
    }
}