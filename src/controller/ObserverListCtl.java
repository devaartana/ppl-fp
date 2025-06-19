package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import src.model.Observer;
import src.view.CompanyPage;
import src.view.MessageBox;
import src.view.ObserverPage;
import src.view.dbhandler;

public class ObserverListCtl {
    private final CompanyPage view;
    private final dbhandler dbHandler;
    private final int companyId;

    public ObserverListCtl(CompanyPage view, int companyId) {
        this.view = view;
        this.dbHandler = dbhandler.getInstance();
        this.companyId = companyId;
        this.view.addAddObserverListener(new AddObserverListener());
        this.view.addEditObserverListener(new EditObserverListener());
        loadObserverData();
    }

    private void loadObserverData() {
        try {
            view.displayObservers(dbHandler.getAllObserversForCompany(companyId));
        } catch (SQLException e) {
            MessageBox.showError(view, "Gagal memuat data observer: " + e.getMessage());
        }
    }

    class AddObserverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ObserverPage dialog = new ObserverPage(view, "Tambah observer Baru");
            dialog.addSaveListener(saveEvt -> {
                Observer baseObserver = new Observer.Builder().build();
                Observer newObserver = dialog.getObserverFromForm(baseObserver);
                newObserver.setCompanyId(companyId);
                if (newObserver.getName().trim().isEmpty()) {
                    MessageBox.showError(dialog, "Nama observer tidak boleh kosong.");
                    return;
                }
                try {
                    dbHandler.addObserver(newObserver);
                    dialog.close();
                    loadObserverData();
                    MessageBox.showInfo(view, "observer telah dibuat.");
                } catch (SQLException ex) {
                    handleSqlException(ex, dialog);
                }
            });
            dialog.setVisible(true);
        }
    }

    class EditObserverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Observer selected = view.getSelectedObserver();
            if (selected == null) {
                MessageBox.showInfo(view, "Silakan pilih observer untuk diedit.");
                return;
            }
            ObserverPage dialog = new ObserverPage(view, "Edit observer");
            final Observer observerToEdit = selected.clone();
            dialog.setObserverData(observerToEdit);
            dialog.addSaveListener(saveEvt -> {
                Observer updatedObserver = dialog.getObserverFromForm(observerToEdit);
                if (updatedObserver.getName().trim().isEmpty()) {
                    MessageBox.showError(dialog, "Nama observer tidak boleh kosong.");
                    return;
                }
                try {
                    dbHandler.updateObserver(updatedObserver);
                    dialog.close();
                    loadObserverData();
                    MessageBox.showInfo(view, "Data observer telah disimpan.");
                } catch (SQLException ex) {
                    handleSqlException(ex, dialog);
                }
            });
            dialog.setVisible(true);
        }
    }

    private void handleSqlException(SQLException ex, ObserverPage dialog) {
        if (ex.getSQLState().equals("23505")) {
            MessageBox.showError(dialog, "observer dengan email ini sudah ada.");
        } else {
            MessageBox.showError(dialog, "Gagal menyimpan observer: " + ex.getMessage());
        }
    }
}
