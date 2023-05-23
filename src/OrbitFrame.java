import javax.swing.*;

class OrbitFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    public OrbitFrame() {
        setTitle("Orbit Simulation");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        OrbitComponent component = new OrbitComponent();
        add(component);
    }
}