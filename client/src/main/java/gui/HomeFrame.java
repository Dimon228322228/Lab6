package gui;

import lombok.Getter;
import lombok.Setter;
import utilites.LanguageManager;
import utilites.UpdatablePanel;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    private final LanguageManager languageManager;

    private final JPanel mainPanel = new JPanel();
    private final JPanel titlePanel = new JPanel();
    private final JPanel helpPanel = new JPanel();

    private final JMenuItem tableItem = new JMenuItem();
    private final JMenuItem coordinate = new JMenuItem();

    private final Table table;
    private final CoordinatePane coordinatePane;

    private JComboBoxLanguage languageBox;

    @Getter @Setter private UpdatablePanel bodyPanel = new UpdatablePanel();
    @Setter private JLabel username = new JLabel("");

    public HomeFrame(LanguageManager languageManager){
        super(languageManager.getString("home"));
        this.languageManager = languageManager;
        table = new Table(new Reflector(languageManager), languageManager);
        coordinatePane = new CoordinatePane(languageManager);
        Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(sizeScreen.width/2 - 325, sizeScreen.height/2 - 200, 750, 400);
        setMenu();
        initLanguageBox();
    }

    private void initLanguageBox(){
        languageBox = new JComboBoxLanguage(languageManager){
            public void abstractDoing(){
                bodyPanel.update();
                setTitle(languageManager.getString("home"));
                setMenu();
                setUsernameLabel();
                setHomePanel();
            }
        };
    }

    private void setNameItems(){
        tableItem.setText(languageManager.getString("table"));
        coordinate.setText(languageManager.getString("coordinate"));
    }

    private void setMenu(){
        setNameItems();
        tableItem.addActionListener(e -> {
                table.updateTable();
                setBodyPanel(table);
                setHomePanel();
        });
        coordinate.addActionListener(e -> {
                setBodyPanel(coordinatePane);
                setHomePanel();
        });

        JMenu product = new JMenu(languageManager.getString("view"));
        product.setMnemonic('2');
        product.add(tableItem);
        product.add(coordinate);

        JMenu changeAccount = new JMenu(languageManager.getString("change_account"));
        changeAccount.setMnemonic('1');

        JMenu exit = new JMenu(languageManager.getString("exit"));
        exit.setMnemonic('3');

        JMenuBar menuBar = new JMenuBar();
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
        titlePanel.add(languageBox);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(user);
        titlePanel.add(username);
    }
}
