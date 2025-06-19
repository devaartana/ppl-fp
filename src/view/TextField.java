package src.view;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

public class TextField extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addCompanyButton;

    public TextField() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        JLabel searchLabel = new JLabel("Search by Name:");
        searchField = new JTextField(25);
        searchButton = new JButton("Search Company");
        addCompanyButton = new JButton("Add New Company");

        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(addCompanyButton);
    }

    public String getKeyword() {
        return searchField.getText();
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addAddCompanyListener(ActionListener listener) {
        addCompanyButton.addActionListener(listener);
    }
}
