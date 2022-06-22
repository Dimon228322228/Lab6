package gui;

import content.UnitOfMeasure;
import utilites.LanguageManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;

public class ProductFrame extends JFrame {

    LanguageManager languageManager;
    private final JRadioButton radioButton;
    private final JCheckBox checkbox = new JCheckBox();
    private final JComboBox comboBox = new JComboBox();

    public ProductFrame(LanguageManager languageManager){
        super(languageManager.getString("productFrame"));
        this.languageManager = languageManager;
        radioButton = new JRadioButton(languageManager.getString("question"));
    }

    public void createAddPanel(){
        JPanel mainAdd = new JPanel();
        BorderLayout mainAddBorder = new BorderLayout();
        mainAdd.setLayout(mainAddBorder);

        //main panel which it has two horizontal sequential part
        JPanel mainPanel = new JPanel();
        BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), languageManager.getString("product"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));

        //panel is on the right place
        JPanel rightPanel = new JPanel();
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
//        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.orange), "Product", TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));
        rightPanel.setLayout(rightLayout);

        //right-top panel
        JPanel rightTopPanel = new JPanel();
        BoxLayout rightTopLayout = new BoxLayout(rightTopPanel, BoxLayout.Y_AXIS);
        rightTopPanel.setLayout(rightTopLayout);
        rightTopPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.orange), languageManager.getString("coordinates"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));
        rightPanel.add(rightTopPanel);

        //panel x
        JPanel x = new JPanel();
        x.add(new JLabel(languageManager.getString("x") + ": "));
        x.add(new JTextField(20));
        rightTopPanel.add(x);

        //panel y
        JPanel y = new JPanel();
        y.add(new JLabel(languageManager.getString("y") + ": "));
        y.add(new JTextField(20));
        rightTopPanel.add(y);


        // right-bottom panel
        JPanel rightBottomPanel = new JPanel();
        BoxLayout rightBottomLayout = new BoxLayout(rightBottomPanel, BoxLayout.Y_AXIS);
        rightBottomPanel.setLayout(rightBottomLayout);
        rightBottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.yellow), languageManager.getString("owner"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));
        rightPanel.add(rightBottomPanel);

        //panel name owner
        JPanel nameOwner = new JPanel();
        nameOwner.add(new JLabel(languageManager.getString("nameOwner")+": "));
        nameOwner.add(new JTextField(20));
        rightBottomPanel.add(nameOwner);

        //panel birthday
        JPanel birthday = new JPanel();
        birthday.add(new JLabel(languageManager.getString("birthday") + ": "));
        birthday.add(new JSpinner(new SpinnerDateModel()));
        rightBottomPanel.add(birthday);

        //panel height owner
        JPanel height = new JPanel();
        height.add(new JLabel(languageManager.getString("height") + ": "));
        height.add(new JSpinner(new SpinnerNumberModel(100,50,300,1)));
        rightBottomPanel.add(height);

        //panel weight owner
        JPanel weight = new JPanel();
        weight.add(new JLabel(languageManager.getString("weight") + ": "));
        weight.add(new JSpinner(new SpinnerNumberModel(70,0,1000,1)));
        rightBottomPanel.add(weight);

        //panel weight owner
        JPanel passport = new JPanel();
        passport.add(new JLabel(languageManager.getString("passportID") + ": "));
        passport.add(new JTextField(20));
        rightBottomPanel.add(passport);

        //left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), languageManager.getString("main_data"), TitledBorder.CENTER, TitledBorder.CENTER, new Font("Italic", 2, 12)));
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
        leftPanel.setLayout(leftLayout);

        leftPanel.add(Box.createVerticalGlue());
        //panel name
        JPanel name = new JPanel();
        name.add(new JLabel(languageManager.getString("name")+": "));
        name.add(new JTextField(20));
        leftPanel.add(name);

        //panel price
        JPanel price = new JPanel();
        price.add(new JLabel(languageManager.getString("price") + ": "));
        price.add(new JTextField(20));
        leftPanel.add(price);

        //panel price
        JPanel partNumber = new JPanel();
        partNumber.add(new JLabel(languageManager.getString("partNumber")+ ": "));
        partNumber.add(new JTextField(20));
        leftPanel.add(partNumber);

        //panel price
        JPanel cost = new JPanel();
        cost.add(new JLabel(languageManager.getString("manufactureCost") + ": "));
        cost.add(new JTextField(20));
        leftPanel.add(cost);


        //panel price
        JPanel unit = new JPanel();
        unit.add(new JLabel(languageManager.getString("unitOfMeasure") + ": "));
        Vector<String> titleUnit = new Vector<>();
        Arrays.stream(UnitOfMeasure.getArrayNames()).map(languageManager::getString).forEachOrdered(titleUnit::add);
        unit.add(new JComboBox(titleUnit));
        leftPanel.add(unit);

        leftPanel.add(radioButton);
        leftPanel.add(Box.createVerticalGlue());

        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        mainPanel.add(Box.createHorizontalGlue());


        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(rightPanel);
        mainPanel.add(Box.createHorizontalGlue());

        mainAdd.add(mainPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        BoxLayout southLayout = new BoxLayout(southPanel, BoxLayout.X_AXIS);
        southPanel.setLayout(southLayout);
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(new JButton(languageManager.getString("add")));
        southPanel.add(new JButton(languageManager.getString("cancel")));

        mainAdd.add(southPanel, BorderLayout.SOUTH);

        add(mainAdd);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        revalidate();
    }

}