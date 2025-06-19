package src;

import javax.swing.SwingUtilities;

import src.controller.CompanyCtl;
import src.view.CompanyMenu;
import src.view.Dashboard;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            CompanyMenu menu = new CompanyMenu();
            
            new CompanyCtl(dashboard, menu);
            
            dashboard.setVisible(true);
        });
    }
}