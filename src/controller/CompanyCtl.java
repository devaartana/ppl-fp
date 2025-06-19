package src.controller;

import javax.swing.*;

import src.model.Company;
import src.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CompanyCtl {
    private final Dashboard view;
    private final CompanyMenu menu;
    private final dbhandler dbHandler;

    public CompanyCtl(Dashboard view, CompanyMenu menu) {
        this.view = view;
        this.menu = menu;
        this.dbHandler = dbhandler.getInstance();
        this.view.setJMenuBar(this.menu);
        this.view.addSearchListener(new SearchListener());
        this.view.addListMouseListener(new ListMouseListener());
        this.view.addAddCompanyListener(new AddCompanyListener());
        loadInitialData();
    }

    private void loadInitialData() {
        try {
            view.displayCompanies(dbHandler.getAllCompanies());
        } catch (SQLException e) {
            MessageBox.showError(view, "Gagal memuat data perusahaan: " + e.getMessage());
        }
    }

    private void openCompanyDetails() {
        Company selectedCompany = view.getSelectedCompany();
        if (selectedCompany == null) {
            MessageBox.showInfo(view, "Silakan pilih perusahaan untuk dilihat detailnya.");
            return;
        }

        CompanyPage page = new CompanyPage(view, "Detail Perusahaan: " + selectedCompany.getName());
        final Company companyToEdit = selectedCompany.clone();
        page.setCompanyData(companyToEdit);
        new ObserverListCtl(page, companyToEdit.getId());

        page.addSaveCompanyListener(e -> {
            Company updatedCompany = page.getCompanyFromForm(companyToEdit);
            if (updatedCompany.getName().trim().isEmpty()) {
                MessageBox.showError(page, "Nama perusahaan tidak boleh kosong.");
                return;
            }
            performDatabaseOperation(() -> dbHandler.updateCompany(updatedCompany), "Data perusahaan telah disimpan.",
                    "Gagal memperbarui perusahaan:", page);
        });
        page.setVisible(true);
    }

    private void performDatabaseOperation(RunnableWithException dbTask, String successMessage, String errorPrefix,
            JDialog parentDialog) {
        ProgressBar progressDialog = new ProgressBar(view);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private Exception exception = null;

            @Override
            protected Void doInBackground() throws Exception {
                try {
                    dbTask.run();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    exception = e;
                }
                return null;
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                if (exception == null) {
                    if (parentDialog != null)
                        parentDialog.dispose();
                    MessageBox.showInfo(view, successMessage);
                    loadInitialData();
                } else {
                    String message = (exception instanceof SQLException
                            && ((SQLException) exception).getSQLState().equals("23505"))
                                    ? "Data dengan informasi unik yang sama sudah ada."
                                    : errorPrefix + " " + exception.getMessage();
                    MessageBox.showError(parentDialog, message);
                }
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
    }

    class AddCompanyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CompanyPage dialog = new CompanyPage(view, "Tambah Perusahaan Baru");
            dialog.addSaveCompanyListener(saveEvt -> {
                Company baseCompany = new Company.Builder().build();
                Company newCompany = dialog.getCompanyFromForm(baseCompany);
                if (newCompany.getName().trim().isEmpty()) {
                    MessageBox.showError(dialog, "Nama perusahaan tidak boleh kosong.");
                    return;
                }
                performDatabaseOperation(() -> dbHandler.addCompany(newCompany), "Perusahaan telah dibuat.",
                        "Gagal menyimpan perusahaan:", dialog);
            });
            dialog.setVisible(true);
        }
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.displayCompanies(dbHandler.searchCompanies(view.getSearchKeyword()));
            } catch (SQLException ex) {
                MessageBox.showError(view, "Gagal mencari perusahaan: " + ex.getMessage());
            }
        }
    }

    class ViewDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            openCompanyDetails();
        }
    }

    class ListMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                openCompanyDetails();
            }
        }
    }

    @FunctionalInterface
    interface RunnableWithException {
        void run() throws Exception;
    }
}
