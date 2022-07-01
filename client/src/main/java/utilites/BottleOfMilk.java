package utilites;

import java.awt.*;

public class BottleOfMilk{

    private final Point center;
    private final String username;
    private final Graphics2D g2d;

    public BottleOfMilk(Point centerBigSquare, String username, Graphics2D g2d){
        center = centerBigSquare;
        this.username = username;
        this.g2d = g2d;
    }

    public void paintBottle() {
        int halfSide = 20;
        Point leftAngle = new Point(center.x - halfSide, center.y - halfSide);
        Point rightAngle = new Point(center.x + halfSide, center.y - halfSide);
        Point leftAngleSmallSquare = new Point(leftAngle.x, leftAngle.y - halfSide);
        Point rightAngleSmallSquare = new Point(leftAngle.x + halfSide, leftAngle.y - halfSide);

        g2d.setColor(new Color(0,100,250));
        g2d.fillRect(leftAngle.x, leftAngle.y, 2 * halfSide, 2 * halfSide);

        g2d.setColor(new Color(0,150,250));
        g2d.fillRect(leftAngleSmallSquare.x, leftAngleSmallSquare.y, halfSide, halfSide);

        g2d.setColor(new Color(0,170,250));
        g2d.fillPolygon(new int[]{rightAngleSmallSquare.x,
                        rightAngleSmallSquare.x,
                        rightAngle.x - halfSide /2,
                        rightAngle.x},
                new int[]{rightAngleSmallSquare.y,
                        rightAngleSmallSquare.y + halfSide /2,
                        rightAngle.y,
                        rightAngle.y}, 4);

        g2d.setColor(new Color(255, 63,0));
        g2d.fillPolygon(new int[]{
                        leftAngleSmallSquare.x + halfSide /4,
                        leftAngleSmallSquare.x + 3 * halfSide /4,
                        leftAngleSmallSquare.x + 3 * halfSide /4,
                        leftAngleSmallSquare.x + halfSide /4},
                new int[]{
                        leftAngleSmallSquare.y,
                        leftAngleSmallSquare.y,
                        leftAngleSmallSquare.y - halfSide /5,
                        leftAngleSmallSquare.y - halfSide /5}, 4);

        g2d.setColor(new Color(username.hashCode()));
        g2d.fillOval(leftAngle.x, leftAngle.y, 2 * halfSide, 2 * halfSide);
    }
}
