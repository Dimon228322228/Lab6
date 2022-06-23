package gui;

import utilites.LanguageManager;

import javax.swing.*;
import java.awt.*;

public class ProductFrame extends JFrame {

    LanguageManager languageManager;

    private final JButton add = new JButton();
    private final JButton cancel = new JButton();

    private JComboBoxLanguage language;

    private final MainInfoProductPanel mainInfoProductPanel;
    private final OwnerPanel ownerPanel;
    private final CoordinatesPanel coordinatesPanel;

    private final GridBagConstraints constraintsLanguage = new GridBagConstraints(
            0, 0, 1, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsMainInfoPanel = new GridBagConstraints(
            1, 1, 1, 2, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsCoordinatePanel = new GridBagConstraints(
            2, 1, 3, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsOwnerPanel = new GridBagConstraints(
            2, 2, 3, 1, 0, 0,
            GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
            new Insets(2,2,2,2), 0, 0);

    private final GridBagConstraints constraintsButton = new GridBagConstraints(
            4, 3, 1, 1, 0, 0,
            GridBagConstraints.EAST, 0,
            new Insets(2,2,2,2), 0, 0);

    public ProductFrame(LanguageManager languageManager){
        super();
        this.languageManager = languageManager;
        initLanguageCheckBox();
        mainInfoProductPanel = new MainInfoProductPanel(languageManager);
        ownerPanel = new OwnerPanel(languageManager);
        coordinatesPanel = new CoordinatesPanel(languageManager);
        setNameButton();
        setTitle(languageManager.getString("productFrame"));
    }

    private void initLanguageCheckBox(){
        language = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                setTitle(languageManager.getString("productFrame"));
                mainInfoProductPanel.update();
                ownerPanel.update();
                coordinatesPanel.update();
                setNameButton();
                pack();
                revalidate();
            }
        };
    }

    private void setNameButton(){
        add.setText(languageManager.getString("add"));
        cancel.setText(languageManager.getString("cancel"));
    }

    public void createAddFrame(){
        JPanel main = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        main.setLayout(layout);

        JPanel buttonPanel = new JPanel();
        BoxLayout layout1 = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
        buttonPanel.setLayout(layout1);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(add);
        buttonPanel.add(Box.createRigidArea(new Dimension(12,0)));
        buttonPanel.add(cancel);

        main.add(language, constraintsLanguage);
        main.add(mainInfoProductPanel, constraintsMainInfoPanel);
        main.add(coordinatesPanel, constraintsCoordinatePanel);
        main.add(ownerPanel, constraintsOwnerPanel);
        main.add(buttonPanel, constraintsButton);


        add(main);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        revalidate();
    }
}