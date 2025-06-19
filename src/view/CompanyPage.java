package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.model.Company;
import src.model.Observer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CompanyPage extends JDialog {
    private JTextField nameField, addressField, contactPersonField, phoneField, emailField;
    private JButton saveButton;
    private JList<Observer> observerList;
    private DefaultListModel<Observer> observerListModel;
    private JButton addObserverButton, editObserverButton;

    public CompanyPage(Frame owner, String title) {
        super(owner, title, true);
        setSize(800, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel companyDetailsPanel = new JPanel(new BorderLayout(10, 10));
        companyDetailsPanel.setBorder(BorderFactory.createTitledBorder("Company Details"));
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        nameField = new JTextField();
        addressField = new JTextField();
        contactPersonField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Contact Person:"));
        formPanel.add(contactPersonField);
        formPanel.add(new JLabel("Mobile Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        JPanel companyButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save Changes"); 
        companyButtonPanel.add(saveButton);
        companyDetailsPanel.add(formPanel, BorderLayout.CENTER);
        companyDetailsPanel.add(companyButtonPanel, BorderLayout.SOUTH);

        JPanel observerPanel = new JPanel(new BorderLayout(10, 10));
        observerPanel.setBorder(BorderFactory.createTitledBorder("Affiliated Observers"));
        observerListModel = new DefaultListModel<>();
        observerList = new JList<>(observerListModel);
        observerPanel.add(new JScrollPane(observerList), BorderLayout.CENTER);
        JPanel observerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addObserverButton = new JButton("Add New Observer"); 
        editObserverButton = new JButton("Edit Selected Observer");
        observerButtons.add(addObserverButton);
        observerButtons.add(editObserverButton);
        observerPanel.add(observerButtons, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, companyDetailsPanel, observerPanel);
        splitPane.setResizeWeight(0.4);
        add(splitPane, BorderLayout.CENTER);
    }

    public void setCompanyData(Company c) {
        nameField.setText(c.getName());
        addressField.setText(c.getAddress());
        contactPersonField.setText(c.getContactPerson());
        phoneField.setText(c.getMobilePhone());
        emailField.setText(c.getEmailAddress());
    }

    public Company getCompanyFromForm(Company originalBaseCompany) {
        return new Company.Builder()
                .id(originalBaseCompany.getId()) 
                .dateRegistered(originalBaseCompany.getDateRegistered()) 
                .name(nameField.getText())
                .address(addressField.getText())
                .contactPerson(contactPersonField.getText())
                .mobilePhone(phoneField.getText())
                .emailAddress(emailField.getText())
                .build();
    }

    public void addSaveCompanyListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void displayObservers(List<Observer> observers) {
        observerListModel.clear();
        for (Observer o : observers) {
            observerListModel.addElement(o);
        }
    }

    public Observer getSelectedObserver() {
        return observerList.getSelectedValue();
    }

    public void addAddObserverListener(ActionListener listener) {
        addObserverButton.addActionListener(listener);
    }

    public void addEditObserverListener(ActionListener listener) {
        editObserverButton.addActionListener(listener);
    }

    public void close() {
        dispose();
    }
}