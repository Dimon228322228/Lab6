package gui;

import content.Product;
import utilites.BottleOfMilk;
import utilites.CollectionManagerClient;
import utilites.LanguageManager;
import utilites.UpdatablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class CoordinatePane extends UpdatablePanel {

    private final LanguageManager languageManager;
    private final CollectionManagerClient collectionManagerClient;
    //Расстояние между линиями параллельными оси у
    private int xGrid = 50;

    //Расстояние между линиями параллельными оси х
    private int yGrid = 50;

    private Timer timer;
    private int mouseWheelNumber = 0;

    private int minValue = 5;
    private final int xGridLetterDistant = 5;

    //Listening to mouse drag
    private Point mousePt = new Point(0,0);

    //Offset from center of coordinates
    private Integer movX = 0;
    private Integer movY = 0;

    public CoordinatePane(LanguageManager languageManager, CollectionManagerClient collectionManagerClient){
        this.languageManager = languageManager;
        this.collectionManagerClient = collectionManagerClient;
        setPreferredSize(new Dimension(550, 500));
        setName(languageManager.getString("coordinate"));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int diffX = e.getX() - mousePt.x;
                int diffY = e.getY() - mousePt.y;
                movX += diffX;
                if (abs(movX) > getWidth()/2 - 20) { movX -= diffX; }
                movY += diffY;
                if (abs(movY) > getHeight()/2 - 20) { movY -= diffY; }
                mousePt = e.getPoint();
                repaint();
            }
        });

        timer = new Timer(50, e -> change());

        addMouseWheelListener(e -> {
            changeMinValue(e.getUnitsToScroll());
            repaint();
            timer.start();
        });
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        //Сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //отрисовка белой панели
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);

        int halW = getWidth()/2;
        int halH = getHeight()/2;

        //отрисовка вертикальной оси
        g2d.drawLine(halW + movX, 0, halW + movX, getHeight());
        //отрисовка горизонтальной оси
        g2d.drawLine(0, halH + movY, getWidth(), halH + movY);
        g2d.drawString(String.valueOf(0), halW + movX, getHeight()/2 + 10 + movY);

//        Отрисовка стрелочек на осях
        g2d.fillPolygon(new int[]{halW - 5 + movX, halW + movX, halW + 5 + movX}, new int[]{15, 0, 15}, 3);
        g2d.fillPolygon(new int[]{getWidth() - 15, getWidth(), getWidth() - 15}, new int[]{halH - 5 + movY, halH + movY, halH + 5 + movY}, 3);

//        Прорисовка букв
        g2d.drawString("X", getWidth()-10, halH + 15 + movY);
        g2d.drawString("Y", halW + 8 + movX, 10);

        drawHelpLines(g2d, halW + movX, halH + movY);

        setProduct(new ArrayList<>(collectionManagerClient.getProducts()), g2d);

//        Перерисовка компонента
        repaint();
    }

    private synchronized void changeMinValue(int change){
        minValue += change;
        mouseWheelNumber = change;
        if (minValue < 0) minValue -= change;
    }


    private void drawHelpLines(Graphics2D g2d, int xCenter, int yCenter) {
        int currentX = xCenter + xGrid;
        int value = minValue;
        while(currentX < getWidth()){
            paintX(g2d, currentX, value);
            currentX += xGrid;
            value += xGridLetterDistant;
        }
        currentX = xCenter - xGrid;
        value = -minValue;
        while(currentX > 0){
            paintX(g2d, currentX, value);
            currentX -= xGrid;
            value -= xGridLetterDistant;
        }

        int currentY = yCenter + yGrid;
        value = minValue;
        while(currentY < getHeight()){
            paintY(g2d, currentY, value);
            currentY += yGrid;
            value += xGridLetterDistant;
        }
        currentY = yCenter - yGrid;
        value = -minValue;
        while(currentY > 0){
            paintY(g2d, currentY, value);
            currentY -= yGrid;
            value -= xGridLetterDistant;
        }
    }

    private void paintX(Graphics2D g2d, int currentX, int value){
        g2d.setColor(Color.lightGray);
        g2d.drawLine(currentX, 0, currentX, getHeight());
        g2d.setColor(Color.gray);
        g2d.drawString(String.valueOf(value), currentX - new JLabel(String.valueOf(value)).getPreferredSize().width / 2, getHeight()/2 + 12 + movY);
    }

    private void paintY(Graphics2D g2d, int currentY, int value){
        g2d.setColor(Color.lightGray);
        g2d.drawLine(0, currentY, getWidth(), currentY);
        g2d.setColor(Color.gray);
        g2d.drawString(String.valueOf(-value), getWidth()/2 + movX - new JLabel(String.valueOf(-value)).getPreferredSize().width, currentY + 6);
    }

    private void setProduct(ArrayList<Product> products, Graphics2D g2d){
        int halW = getWidth()/2;
        int halH = getHeight()/2;
        products.stream().forEach(x -> {
            BottleOfMilk bottleOfMilk = new BottleOfMilk(new Point(halW + movX + x.getCoordinates().getX()*minValue, halH + movY - x.getCoordinates().getY() * minValue), x.getUsername(), g2d);
            bottleOfMilk.paintBottle();
            repaint();
        });
    }

    private void change(){
        while (mouseWheelNumber != 0){
            if (mouseWheelNumber > 0){
                xGrid--;
                yGrid--;
                mouseWheelNumber--;
            } else {
                xGrid++;
                yGrid++;
                mouseWheelNumber++;
            }
            repaint();
        }
    }

    @Override
    public void updateLanguage() {
        setName(languageManager.getString("coordinate"));
    }

    @Override
    public void update(){
        repaint();
    }
}