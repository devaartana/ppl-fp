package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.model.Company;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;

public class Dashboard extends JFrame {
    private final TextField searchPanel;
    private final JList<Company> companyList;
    private final DefaultListModel<Company> listModel;

    public Dashboard() {
        setTitle("PVT - Dashboard");
        setSize(850, 500);
        setMinimumSize(new Dimension(800, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        searchPanel = new TextField();
        searchPanel.setBorder(new EmptyBorder(10, 5, 5, 5));

        listModel = new DefaultListModel<>();
        companyList = new JList<>(listModel);
        companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        companyList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        companyList.setFixedCellHeight(36);
        
        JScrollPane scrollPane = new JScrollPane(companyList);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 10, 10, 10),
                BorderFactory.createTitledBorder("Registered Companies")));

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getSearchKeyword() {
        return searchPanel.getKeyword();
    }

    public void addSearchListener(ActionListener listener) {
        searchPanel.addSearchListener(listener);
    }

    public void addAddCompanyListener(ActionListener listener) {
        searchPanel.addAddCompanyListener(listener);
    }

    public void addListMouseListener(MouseAdapter listener) {
        companyList.addMouseListener(listener);
    }

    public void displayCompanies(List<Company> companies) {
        listModel.clear();
        for (Company c : companies) {
            listModel.addElement(c);
        }
    }

    public Company getSelectedCompany() {
        return companyList.getSelectedValue();
    }
}