package src.view;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.Company;
import src.model.Observer;

public class dbhandler {
    private static String DB_URL = "jdbc:postgresql://localhost:8081/database";
    private static String USER = "user";
    private static String PASS = "password";
    private static final dbhandler instance = new dbhandler();

    private dbhandler() {
    }

    public static dbhandler getInstance() {
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM companies ORDER BY name";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                companies.add(mapRowToCompany(rs));
            }
        }
        return companies;
    }

    public void addCompany(Company company) throws SQLException {
        String sql = "INSERT INTO companies (name, address, contact_person, telephone_number, email_address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getAddress());
            pstmt.setString(3, company.getContactPerson());
            pstmt.setString(4, company.getMobilePhone());
            pstmt.setString(5, company.getEmailAddress());
            pstmt.executeUpdate();
        }
    }

    public void updateCompany(Company company) throws SQLException {
        String sql = "UPDATE companies SET name = ?, address = ?, contact_person = ?, telephone_number = ?, email_address = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getAddress());
            pstmt.setString(3, company.getContactPerson());
            pstmt.setString(4, company.getMobilePhone());
            pstmt.setString(5, company.getEmailAddress());
            pstmt.setInt(6, company.getId());
            pstmt.executeUpdate();
        }
    }

    public List<Company> searchCompanies(String keyword) throws SQLException {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM companies WHERE name ILIKE ? OR contact_person ILIKE ? ORDER BY name";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    companies.add(mapRowToCompany(rs));
                }
            }
        }
        return companies;
    }

    private Company mapRowToCompany(ResultSet rs) throws SQLException {
        return new Company.Builder().id(rs.getInt("id")).name(rs.getString("name")).address(rs.getString("address"))
                .dateRegistered(rs.getDate("date_registered")).contactPerson(rs.getString("contact_person"))
                .mobilePhone(rs.getString("telephone_number")).emailAddress(rs.getString("email_address")).build();
    }

    public List<Observer> getAllObserversForCompany(int companyId) throws SQLException {
        List<Observer> observers = new ArrayList<>();
        String sql = "SELECT * FROM observers WHERE company_id = ? ORDER BY name";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, companyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    observers.add(mapRowToObserver(rs));
                }
            }
        }
        return observers;
    }

    public void addObserver(Observer observer) throws SQLException {
        String sql = "INSERT INTO observers (name, address, telephone_number, email_address, company_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, observer.getName());
            pstmt.setString(2, observer.getAddress());
            pstmt.setString(3, observer.getMobilePhone());
            pstmt.setString(4, observer.getEmailAddress());
            pstmt.setInt(5, observer.getCompanyId());
            pstmt.executeUpdate();
        }
    }

    public void updateObserver(Observer observer) throws SQLException {
        String sql = "UPDATE observers SET name = ?, address = ?, telephone_number = ?, email_address = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, observer.getName());
            pstmt.setString(2, observer.getAddress());
            pstmt.setString(3, observer.getMobilePhone());
            pstmt.setString(4, observer.getEmailAddress());
            pstmt.setInt(5, observer.getId());
            pstmt.executeUpdate();
        }
    }

    private Observer mapRowToObserver(ResultSet rs) throws SQLException {
        return new Observer.Builder().id(rs.getInt("id")).name(rs.getString("name")).address(rs.getString("address"))
                .dateCreated(rs.getDate("date_created")).mobilePhone(rs.getString("telephone_number"))
                .emailAddress(rs.getString("email_address")).companyId(rs.getInt("company_id")).build();
    }
}