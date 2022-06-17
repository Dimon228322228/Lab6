package gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    private final JPanel mainPanel = new JPanel();
    private final JPanel titlePanel = new JPanel();
    @Getter @Setter private JPanel bodyPanel = new JPanel();
    @Setter private JLabel username = new JLabel("");

    private static HomeFrame homeframe;

    public static HomeFrame getInstance(){
        if (homeframe == null) return new HomeFrame();
        else return homeframe;
    }

    private HomeFrame(){
        super("Home");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        setBounds(sizeScreen.width/2 - 325, sizeScreen.height/2 - 200, 750, 400);
        setMenu();
    }

    private void setMenu(){
        JMenuItem itemru = new JMenuItem("ru");
        JMenuItem itemel = new JMenuItem("el");
        JMenuItem itemca = new JMenuItem("ca_ES");
        JMenuItem itemis = new JMenuItem("is_IS");
        JMenuItem table = new JMenuItem("table");
        JMenuItem coordinate = new JMenuItem("coordinate");

        JMenu changeLanguageCheckBox = new JMenu("language");
        changeLanguageCheckBox.setMnemonic('l');
        changeLanguageCheckBox.add(itemru);
        changeLanguageCheckBox.add(itemel);
        changeLanguageCheckBox.add(itemca);
        changeLanguageCheckBox.add(itemis);

        JMenu product = new JMenu("product");
        product.setMnemonic('p');
        product.add(table);
        product.add(coordinate);

        JMenu changeAccount = new JMenu("change account");
        changeAccount.setMnemonic('a');
        JMenu exit = new JMenu("exit");
        exit.setMnemonic('e');

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(changeLanguageCheckBox);
        menuBar.add(changeAccount);
        menuBar.add(product);
        menuBar.add(exit);
        setJMenuBar(menuBar);
    }

    public void setHomePanel(JPanel panel){
        JLabel user = new JLabel("username: ");

        BoxLayout titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(user);
        titlePanel.add(username);

        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
                .addComponent(titlePanel)
                .addComponent(panel));
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addComponent(titlePanel)
                .addComponent(panel)
        );
        mainPanel.setLayout(mainLayout);
        add(mainPanel);
        pack();
        revalidate();
    }
}
