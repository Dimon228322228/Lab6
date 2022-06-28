package gui;

import actionClient.CommandHandler;
import content.Product;
import exceptions.InvalidRecievedException;
import lombok.Getter;
import utilites.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Table extends UpdatablePanel {

    private final Reflector reflector;
    private final LanguageManager languageManager;
    private final CommandHandler commandHandler;
    @Getter private MyTableModel tableModel;

    private JScrollPane pane;
    @Getter private JTable table;
    private final JPanel buttonsPanel = new JPanel();
    private final JButton add = new JButton();
    private final JButton remove = new JButton();
    private final JButton updateById = new JButton();

    public Table(Reflector reflector, LanguageManager languageManager, CommandHandler commandHandler){
        this.languageManager = languageManager;
        this.reflector = reflector;
        this.commandHandler = commandHandler;
        tableModel = new MyTableModel(reflector);
        setName(languageManager.getString("table"));
        setLayout(new BorderLayout());
        setButtonName();
        setButton();
    }

    private void setButtonName(){
        add.setText(languageManager.getString("add"));
        remove.setText(languageManager.getString("remove"));
        updateById.setText(languageManager.getString("update"));
    }

    private void setButtonAction(){
        add.addActionListener(new AddListener(this, languageManager, commandHandler));
        remove.addActionListener(new RemoveByIdListener(this, tableModel, languageManager, commandHandler));
        updateById.addActionListener(new UpdateByIdListener(this, tableModel, languageManager, commandHandler));
    }

    private JScrollPane repaintTable(){
        tableModel = new MyTableModel(reflector);
        setButtonAction();
        table = new JTable(tableModel){
            public boolean getScrollableTracksViewportWidth(){
                return getPreferredSize().width < getParent().getWidth();
            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };
        table.setDefaultRenderer(Date.class, new MyTableCellRenderer());
        table.setDefaultRenderer(LocalDateTime.class, new MyTableCellRenderer());
        addProductInTable();
        resizeColumnWight(table);
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        JScrollPane pane = new JScrollPane(table);
        pane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeColumnWight(table);
                repaint();
            }
        });
        revalidate();
        return pane;
    }

    private void addProductInTable(){
        try {
            tableModel.setData(new ArrayList<>(commandHandler.getCollectionFromServer()));
        } catch (InvalidRecievedException | IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, languageManager.getString("Wherever occurred an error. "), languageManager.getString("error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setButton(){
        BoxLayout layout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
        buttonsPanel.setLayout(layout);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(add);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        buttonsPanel.add(updateById);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        buttonsPanel.add(remove);
        add(buttonsPanel, BorderLayout.SOUTH);
        revalidate();
    }


    private void resizeColumnWight(JTable table){
        TableColumnModel model = table.getColumnModel();
        for (int col = 0; col < tableModel.getColumnCount(); col++){
            int width = 50;
            String name = tableModel.getColumnName(col);
            width = Math.max(name.length() * 10 + 1 + getInsets().left + getInsets().right, width);
            for (int row = 0; row < tableModel.getRowCount(); row++){
                TableCellRenderer renderer = table.getCellRenderer(row, col);
                Component cells = table.prepareRenderer(renderer, row, col);
                width = Math.max(cells.getPreferredSize().width + 1 + getInsets().left + getInsets().right, width);
            }
            model.getColumn(col).setMinWidth(width);
        }
    }

    public void updateTable() {
        if (pane != null) remove(pane);
        pane = repaintTable();

        setVisible(false);
        add(pane, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void update() {
        setName(languageManager.getString("table"));
        setButtonName();
        updateTable();
    }

    class MyTableCellRenderer extends DefaultTableCellRenderer{
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss", languageManager.getCurrentLocale());
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            format.applyPattern(format.toLocalizedPattern());
            if (value instanceof Date) {
                try {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, format.format(value), isSelected,hasFocus,row, column);
                    label.setToolTipText("date in format: day/month/year hours:minutes:seconds");
                    value =format.parse(format.format(value));
                    return label;
                } catch (ParseException ignored) {}
            }
            if (value instanceof LocalDateTime dateTime){
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
                try {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, format.format(calendar.getTime()), isSelected,hasFocus,row, column);
                    label.setToolTipText("date in format: day/month/year hours:minutes:seconds");
                    value = format.parse(format.format(calendar.getTime()));
                    return label;
                } catch (ParseException ignored) {}
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class MyTableModel extends AbstractTableModel {
        private final ArrayList<String> columnNames;
        private final ArrayList<Class<?>> columnTypes;

        public MyTableModel(Reflector reflector){
            columnNames = reflector.getColumnNamesForProduct();
            columnTypes = reflector.getColumnTypesForProduct();
        }

        private final ArrayList<ArrayList<Object>> data = new ArrayList<>();

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnTypes.get(columnIndex);
        }

        @Override
        public String getColumnName(int index) {
            return columnNames.get(index);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return (data.get(rowIndex)).get(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            (data.get(rowIndex)).set(columnIndex, aValue);
        }

        public int getIdColumn() throws InvalidObjectException {
            int column = -1;
            for (int i = 0; i < getColumnCount(); i++){
                if (getColumnName(i).equals(languageManager.getString("id"))){
                    column = i;
                }
            }
            if (column != -1){
                return column;
            } else throw new InvalidObjectException("Nu such column id in table");
        }

        public void setData(ArrayList<Product> products) {
            for (Product product : products) setProduct(product);
            fireTableStructureChanged();
        }

        private void setProduct(Product product) {
            ArrayList<Object> row = new ArrayList<>();
            row.add(product.getId());
            row.add(product.getName());
            row.add(product.getCoordinates().getX());
            row.add(product.getCoordinates().getY());
            row.add(product.getCreationDate());
            row.add(product.getPrice());
            if (product.getPartNumber() != null) row.add(product.getPartNumber());
            else row.add("-");
            row.add(product.getManufactureCost());
            if (product.getUnitOfMeasure() != null) row.add(product.getUnitOfMeasure());
            else row.add("-");
            if (product.getOwner() != null) {
                row.add(product.getOwner().getNameOwner());
                row.add(product.getOwner().getBirthday());
                row.add(product.getOwner().getHeight());
                row.add(product.getOwner().getWeight());
                row.add(product.getOwner().getPassportID());
            } else {
                row.add("-");
                row.add("-");
                row.add("-");
                row.add("-");
                row.add("-");
            }
            row.add(product.getUsername());
            data.add(row);
        }
    }
}
