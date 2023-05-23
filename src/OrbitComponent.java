import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class OrbitComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private double angle = 0;
    private double speed = 0.02;

    public OrbitComponent() {
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    angle += speed;
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                }
            }
        });

        animationThread.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int planetRadius = 100;
        int satelliteRadius = 15;

        int planetX = (DEFAULT_WIDTH - planetRadius) / 2;
        int planetY = (DEFAULT_HEIGHT - planetRadius) / 2;

        int satelliteX = (int) (planetX + (planetRadius * Math.cos(angle)));
        int satelliteY = (int) (planetY + (planetRadius * Math.sin(angle)));

        // Create shapes for planet and satellite
        Ellipse2D planet = new Ellipse2D.Double(planetX, planetY, planetRadius, planetRadius);
        Ellipse2D satellite = new Ellipse2D.Double(satelliteX, satelliteY, satelliteRadius, satelliteRadius);

        // Draw the planet
        g2.fill(planet);

        if (!(planet.intersects(satellite.getBounds2D()) && Math.cos(angle) < 0)) {
            g2.fill(satellite);
        }
    }
}
