package gui;

import lombok.Getter;
import lombok.Setter;
import utilites.LanguageManager;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {

    private final LanguageManager languageManager;
    private final JPanel mainPanel = new JPanel();
    private final JPanel titlePanel = new JPanel();
    private final JPanel helpPanel = new JPanel();

    @Getter @Setter private JPanel bodyPanel = new JPanel();
    @Setter private JLabel username = new JLabel("");

    public HomeFrame(LanguageManager languageManager){
        super(languageManager.getString("home"));
        this.languageManager = languageManager;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        setBounds(sizeScreen.width/2 - 325, sizeScreen.height/2 - 200, 750, 400);
        setMenu();
    }

    private void setMenu(){
        JMenuItem itemru = new MyJMenuItem(languageManager.getString("ru_RU"));
        JMenuItem itemel = new JMenuItem(languageManager.getString("el"));
        JMenuItem itemca = new JMenuItem(languageManager.getString("es_PA"));
        JMenuItem itemis = new JMenuItem(languageManager.getString("is_IS"));

        JMenuItem table = new JMenuItem(languageManager.getString("table"));
        table.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table table = new Table(new Reflector(languageManager), languageManager);
                table.updateTable();
                setBodyPanel(table);
                setHomePanel();
            }
        });

        JMenuItem coordinate = new JMenuItem(languageManager.getString("coordinate"));
        coordinate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBodyPanel(new CoordinatePanel(languageManager));
                setHomePanel();
            }
        });

        JMenu changeLanguageCheckBox = new JMenu(languageManager.getString("language"));
        changeLanguageCheckBox.setMnemonic('1');
        changeLanguageCheckBox.add(itemru);
        changeLanguageCheckBox.add(itemel);
        changeLanguageCheckBox.add(itemca);
        changeLanguageCheckBox.add(itemis);

        JMenu product = new JMenu(languageManager.getString("view"));
        product.setMnemonic('3');
        product.add(table);
        product.add(coordinate);

        JMenu changeAccount = new JMenu(languageManager.getString("change_account"));
        changeAccount.setMnemonic('2');
        JMenu exit = new JMenu(languageManager.getString("exit"));
        exit.setMnemonic('4');

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(changeLanguageCheckBox);
        menuBar.add(changeAccount);
        menuBar.add(product);
        menuBar.add(exit);
        setJMenuBar(menuBar);
    }

    public void setHomePanel(){
        mainPanel.removeAll();
        helpPanel.removeAll();

        setUsernameLabel();

        helpPanel.setLayout(new BorderLayout());
        helpPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.BLACK,5,0.1f), bodyPanel.getName()));
        helpPanel.add(bodyPanel, BorderLayout.CENTER);

        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainLayout);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
                .addComponent(titlePanel)
                .addComponent(helpPanel));
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addComponent(titlePanel)
                .addComponent(helpPanel)
        );

        add(mainPanel);
        pack();
        revalidate();
    }

    private void setUsernameLabel(){
        titlePanel.removeAll();
        JLabel user = new JLabel(languageManager.getString("username") + ": ");
        BoxLayout titleLayout = new BoxLayout(titlePanel, BoxLayout.LINE_AXIS);
        titlePanel.setLayout(titleLayout);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(user);
        titlePanel.add(username);
    }

    class MyJMenuItem extends JMenuItem{
        public MyJMenuItem(String str){
            super(str);
        }
    }
}
