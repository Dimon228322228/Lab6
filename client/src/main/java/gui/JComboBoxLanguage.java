package gui;

import utilites.LanguageManager;

import javax.swing.*;

public class JComboBoxLanguage extends JComboBox<String> {

    LanguageManager languageManager;

    public JComboBoxLanguage(LanguageManager languageManager){
        super();
        this.languageManager = languageManager;
        createComboBox();
    }

    private void createComboBox(){
        addItem(languageManager.getString("ru_RU"));
        addItem(languageManager.getString("el"));
        addItem(languageManager.getString("es_PA"));
        addItem(languageManager.getString("is_IS"));
        setSelectedItem(languageManager.getLocaleName());
    }
}
