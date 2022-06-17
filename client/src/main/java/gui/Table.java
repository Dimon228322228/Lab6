package gui;

import content.Coordinates;
import content.Person;
import content.Product;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Table extends JPanel{

    private Reflector reflector;

    private JTable table = new JTable(){
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

    public Table(Reflector reflector){
        this.reflector = reflector;
        createTable();
    }

    private void createTable(){
        table.setModel(new MyTableModel());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        setWidthColumns(table);
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(new JScrollPane(table));
        for (Class<?> clas:
                (new MyTableModel()).getColumnTypes() ) {
            System.out.println(clas);
        }
        for (String clas:
                (new MyTableModel()).getColumnNames() ) {
            System.out.println(clas);
        }

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createRaisedBevelBorder(),
                "Table"));
    }

//    private void resizeColumnWight(JTable table){
//        int allWight = 0;
//        TableColumnModel model = table.getColumnModel();
//        for (int i = 0; i < table.getRowCount(); i++){
//            int wight = 30;
//            for
//        }
//    }

//    private void setWidthColumns(JTable table) {
//        TableColumnModel model = table.getColumnModel();
//        for (int i = 0; i < model.getColumnCount(); i++){
//            TableColumn column = model.getColumn(i);
//            column.setMinWidth(115);
//        }
//    }

    class MyTableModel extends AbstractTableModel {
        private final ArrayList<String> columnNames;
        private final ArrayList<Class<?>> columnTypes;

        public MyTableModel(){
            columnNames = getColumnNames();
            columnTypes = getColumnTypes();
        }

        public ArrayList<Class<?>> getColumnTypes(){
            ArrayList<Class<?>> allClasses = new ArrayList<>();
            ArrayList<Class<?>> productColumnTypes = reflector.columnClasses(Product.class);
            for (Class<?> clas: productColumnTypes){
                if (Coordinates.class.equals(clas)) {
                    allClasses.addAll(reflector.columnClasses(Coordinates.class));
                } else if (Person.class.equals(clas)) {
                    allClasses.addAll(reflector.columnClasses(Person.class));
                } else {
                    allClasses.add(clas);
                }
            }
            return allClasses;
        }

        public ArrayList<String> getColumnNames(){
            ArrayList<String> allClasses = new ArrayList<>();
            ArrayList<String> productColumnNames = reflector.columnNames(Product.class);
            for (String str: productColumnNames){
                if ("coordinates".equals(str)) {
                    allClasses.addAll(reflector.columnNames(Coordinates.class));
                } else if ("owner".equals(str)) {
                    allClasses.addAll(reflector.columnNames(Person.class));
                } else {
                    allClasses.add(str);
                }
            }
            return allClasses;
        }
//        private ArrayList<String> columnNames = new ArrayList<>(java.util.List.of(
//                "id",
//                "name",
//                "X",
//                "Y",
//                "creation date",
//                "price",
//                "part number",
//                "manufacture cost",
//                "unit of measure",
//                "name owner",
//                "birthday owner",
//                "height owner",
//                "weight owner",
//                "passport id owner",
//                "username"));


//        private ArrayList<Class<?>> columnTypes = new ArrayList<>(List.of(
//                int.class,
//                String.class,
//                Integer.class,
//                Integer.class,
//                Date.class,
//                Double.class,
//                String.class,
//                double.class,
//                Object.class,
//                String.class,
//                Date.class,
//                Long.class,
//                Integer.class,
//                String.class,
//                String.class
//        ));


        private ArrayList<ArrayList<Object>> data = new ArrayList<>();
        @Setter
        private boolean editable;

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
            return editable;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            (data.get(rowIndex)).set(columnIndex, aValue);
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
