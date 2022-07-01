package gui;

import actionClient.CommandHandler;
import utilites.LanguageManager;

import javax.swing.*;


public class SwingApp {

    private static final LanguageManager languageManager = new LanguageManager();
    private static RegistrationFrame registrationFrame;
    private static CommandHandler commandHandler;
    private static HomeFrame homeFrame;

    public SwingApp(CommandHandler commandHandler) {
        SwingApp.commandHandler = commandHandler;
        registrationFrame = new RegistrationFrame("hello", languageManager, commandHandler);
        registrationFrame.getWelcomeFrame();
    }

    public static void setHomeFrame(){
        if (homeFrame == null){
            homeFrame = new HomeFrame(languageManager, commandHandler);
            homeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            homeFrame.setVisible(true);
        } else homeFrame.revalidate();
    }

    public static void repaintAll(){
        registrationFrame.repaint();
        if (homeFrame != null) homeFrame.repaint();
    }
}