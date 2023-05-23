
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MatrixApplication extends JFrame {
    private final JButton openFileButton;
    private final JButton computeButton;
    private final JTextField resultField;
    private final JTable matrixTable;
    private final JLabel instructionLabel;
    private final JFileChooser fileChooser;

    public MatrixApplication() {
        setLayout(new FlowLayout());

        instructionLabel = new JLabel("Виберіть файл та натисніть кнопку для обчислення");
        add(instructionLabel);

        openFileButton = new JButton("Відкрити файл");
        add(openFileButton);

        matrixTable = new JTable(3, 3);  // maximum n = 20
        add(matrixTable);

        computeButton = new JButton("Обчислити");
        add(computeButton);

        resultField = new JTextField(10);
        add(resultField);

        fileChooser = new JFileChooser();

        event e = new event();
        openFileButton.addActionListener(e);
        computeButton.addActionListener(e);
    }

    public double[] compute(JTable table) throws IOException, InvalidValueException {
        double[] result = new double[table.getRowCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            double min = Double.MAX_VALUE;
            double max = -Double.MAX_VALUE;
            for (int j = 0; j < table.getColumnCount(); j++) {
                try {
                    String cellValue = (String)table.getValueAt(i, j);
                    if(cellValue == null || cellValue.isEmpty()) {
                        continue;
                    }
                    double value = Double.parseDouble(cellValue);
                    if (value < -100 || value > 100) throw new InvalidValueException("Некоректне значення: " + value);
                    min = Math.min(min, value);
                    max = Math.max(max, value);
                } catch (NumberFormatException ex) {
                    throw new IOException("Некоректний формат вхідних даних");
                }
            }
            result[i] = (min + max) / 2;
        }
        return result;
    }

    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openFileButton) {
                int returnVal = fileChooser.showOpenDialog(MatrixApplication.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null && row < 20) {
                            String[] values = line.split(" ");
                            for (int col = 0; col < values.length && col < 20; col++) {
                                matrixTable.setValueAt(values[col], row, col);
                            }
                            row++;
                        }
                        br.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Помилка при зчитуванні файлу");
                    }
                }
            } else if (e.getSource() == computeButton) {
                try {
                    double[] result = compute(matrixTable);
                    resultField.setText(java.util.Arrays.toString(result));
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Файл не знайдено");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Некоректний формат вхідних даних");
                } catch (InvalidValueException ex) {
                    JOptionPane.showMessageDialog(null, "Некоректне значення в матриці");
                }
            }
        }
    }

    public static void main(String[] args) {
        MatrixApplication app = new MatrixApplication();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(500, 500);
        app.setVisible(true);
    }
}
