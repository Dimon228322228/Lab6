package gui;

import utilites.LanguageManager;

import javax.swing.*;
import java.util.Locale;


public class SwingApp {
    public SwingApp() {
        LanguageManager languageManager = new LanguageManager();

        RegistrationFrame registrationFrame = new RegistrationFrame("home", languageManager);
        registrationFrame.setPanel();
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setVisible(true);

        HomeFrame home = new HomeFrame(languageManager);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setVisible(true);

        ProductFrame addProduct = new ProductFrame(languageManager);
        addProduct.createAddPanel();
        addProduct.setVisible(true);
    }
}