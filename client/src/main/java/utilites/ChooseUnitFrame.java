package utilites;

import content.UnitOfMeasure;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class ChooseUnitFrame extends JFrame {

    private final LanguageManager languageManager;

    private JComboBox<String> unitOfMeasure;

    @Getter private boolean ready = false;

    private final JButton select = new JButton();
    private final JButton cancel = new JButton();

    public ChooseUnitFrame(LanguageManager languageManager) {
        this.languageManager = languageManager;
        Vector<String> titleUnit = new Vector<>();
        Arrays.stream(UnitOfMeasure.getArrayNames()).map(languageManager::getString).forEachOrdered(titleUnit::add);
        unitOfMeasure = new JComboBox<>(titleUnit);
        setTitle("Choose unit of measure");
        initButton();
        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(sizeScreen.width/2 - 100, sizeScreen.height/2 - 100, 200,200);
    }

    private void initButton(){
        select.setText("select");
        cancel.setText("cancel");
        select.addActionListener(e -> {
            ready = true;
            setVisible(false);
        });
        cancel.addActionListener(e -> {
            setVisible(false);
        });
    }

    public UnitOfMeasure getUnitProduct(){
        switch (unitOfMeasure.getSelectedIndex()){
            case 0 -> {return UnitOfMeasure.KILOGRAMS;}
            case 1 -> {return UnitOfMeasure.CENTIMETERS;}
            case 2 -> {return UnitOfMeasure.PCS;}
            case 3 -> {return UnitOfMeasure.MILLILITERS;}
            case 4 -> {return UnitOfMeasure.GRAMS;}
            default -> {return null;}
        }
    }

    public void createFrame(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(select);
        buttonPanel.add(cancel);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(unitOfMeasure);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
        add(mainPanel);
        setVisible(true);
        pack();
        revalidate();
    }
}
