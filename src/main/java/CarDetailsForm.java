import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class CarDetailsForm extends JFrame {
    private JTextField brandField, modelField, yearField;
    private JCheckBox hasAirConditioning;
    private JRadioButton sedanRadio, suvRadio;
    private JComboBox<String> fuelTypeComboBox;

    public CarDetailsForm() {
        setTitle("Car Details Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Brand:"));
        brandField = new JTextField();
        add(brandField);

        add(new JLabel("Model:"));
        modelField = new JTextField();
        add(modelField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("Air Conditioning:"));
        hasAirConditioning = new JCheckBox();
        add(hasAirConditioning);

        add(new JLabel("Body Type:"));
        ButtonGroup bodyTypeGroup = new ButtonGroup();
        sedanRadio = new JRadioButton("Sedan");
        suvRadio = new JRadioButton("SUV");
        bodyTypeGroup.add(sedanRadio);
        bodyTypeGroup.add(suvRadio);
        JPanel bodyTypePanel = new JPanel();
        bodyTypePanel.add(sedanRadio);
        bodyTypePanel.add(suvRadio);
        add(bodyTypePanel);

        add(new JLabel("Fuel Type:"));
        String[] fuelTypes = {"Benzina", "Diesel", "Electric"};
        fuelTypeComboBox = new JComboBox<>(fuelTypes);
        add(fuelTypeComboBox);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFormDataToJson();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(saveButton);
        add(cancelButton);
    }

    private void saveFormDataToJson() {
        JSONObject carDetails = new JSONObject();
        carDetails.put("Brand", brandField.getText());
        carDetails.put("Model", modelField.getText());
        carDetails.put("Year", yearField.getText());
        carDetails.put("AirConditioning", hasAirConditioning.isSelected());
        carDetails.put("BodyType", sedanRadio.isSelected() ? "Sedan" : "SUV");
        carDetails.put("FuelType", fuelTypeComboBox.getSelectedItem());

        try (FileWriter file = new FileWriter("car_details.json", true)) {
            file.write(carDetails.toJSONString() + "\n");
            file.flush();
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
            clearForm();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        brandField.setText("");
        modelField.setText("");
        yearField.setText("");
        hasAirConditioning.setSelected(false);
        sedanRadio.setSelected(true);
        fuelTypeComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CarDetailsForm().setVisible(true);
            }
        });
    }
}