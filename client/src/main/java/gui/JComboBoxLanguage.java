package gui;

import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JComboBoxLanguage extends JComboBox<String> {

    LanguageManager languageManager;

    public JComboBoxLanguage(LanguageManager languageManager){
        super();
        this.languageManager = languageManager;
        createComboBox();
        addListeners();
    }

    private void setNameItems(){
        addItem(languageManager.getString("ru_RU"));
        addItem(languageManager.getString("el"));
        addItem(languageManager.getString("es_PA"));
        addItem(languageManager.getString("is_IS"));
    }

    private void createComboBox(){
        setNameItems();
        setSelectedItem(languageManager.getLocaleName());
        setMinimumSize(getPreferredSize());
    }

    private void addListeners(){
        addActionListener(e -> {
            languageManager.setLocalByString((String) Objects.requireNonNull(getSelectedItem()));
            abstractDoing();
        });
    }

    public void abstractDoing(){}
}
