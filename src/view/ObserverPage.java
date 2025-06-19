package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.model.Observer;

import java.awt.*;
import java.awt.event.ActionListener;


public class ObserverPage extends JDialog {
    private JTextField nameField, addressField, phoneField, emailField;
    private JButton saveButton;

    public ObserverPage(Dialog owner, String title) {
        super(owner, title, true);
        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        nameField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Mobile Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save"); 
        buttonPanel.add(saveButton);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setObserverData(Observer o) {
        nameField.setText(o.getName());
        addressField.setText(o.getAddress());
        phoneField.setText(o.getMobilePhone());
        emailField.setText(o.getEmailAddress());
    }

    public Observer getObserverFromForm(Observer originalBaseObserver) {
        return new Observer.Builder()
                .id(originalBaseObserver.getId())
                .companyId(originalBaseObserver.getCompanyId())
                .name(nameField.getText())
                .address(addressField.getText())
                .mobilePhone(phoneField.getText())
                .emailAddress(emailField.getText())
                .build();
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void close() {
        dispose();
    }
}
