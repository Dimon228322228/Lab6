package gui;

import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;
import java.util.Locale;


public class SwingApp {

    private static final LanguageManager languageManager = new LanguageManager();
    private static CommandHandler commandHandler;
    private static JFrame jFrame;
    private static JFrame registrationFrame;
    private static JFrame homeFrame;
//    private static JFrame productFrame;

    public SwingApp(CommandHandler commandHandler) {
        SwingApp.commandHandler = commandHandler;
        setFrames();
        jFrame = registrationFrame;
        jFrame.setVisible(true);
    }

    private void setFrames(){
        registrationFrame = new WelcomeFrame("hello", languageManager, commandHandler);
        registrationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        homeFrame = new HomeFrame(languageManager, commandHandler);
        homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        productFrame = new ProductFrame(languageManager, commandHandler, );
//        productFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void setRegistrationFrame(){
        jFrame.setVisible(false);
        jFrame = registrationFrame;
        jFrame.setVisible(true);
    }

    public static void setHomeFrame(){
        jFrame.setVisible(false);
        jFrame = homeFrame;
        jFrame.setVisible(true);
    }

    public static void setProductFrame(){
        jFrame.setVisible(false);
//        jFrame = productFrame;
        jFrame.setVisible(true);
    }

    public static void repaintAll(){
        registrationFrame.repaint();
        homeFrame.repaint();
    }
}