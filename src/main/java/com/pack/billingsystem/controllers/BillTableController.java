package com.pack.billingsystem.controllers;

import com.pack.billingsystem.MainController;
import com.pack.billingsystem.models.Bill;
import com.pack.billingsystem.models.BillRowData;
import com.pack.billingsystem.models.PatientBillDTO;
import com.pack.billingsystem.services.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class BillTableController implements Initializable {

    @FXML
    public TableView<BillRowData> table;

    @FXML
    public TableColumn<BillRowData, Integer> idBill;

    @FXML
    public TableColumn<BillRowData, String> nom;

    @FXML
    public TableColumn<BillRowData, String> prenom;

    @FXML
    public TableColumn<BillRowData, String> tele;

    @FXML
    public TableColumn<BillRowData, Date> date;

    public ObservableList<BillRowData> list = FXCollections.observableArrayList();

    private BillService billService = new BillService();

    private MainController mainController = new MainController();

    public void setRowData() throws SQLException {
        List<Bill> bills = billService.getAllBills();
        List<PatientBillDTO> unpayedBills = billService.getUnpayedBills(bills);
        for(PatientBillDTO unpayedBill:unpayedBills) {
            BillRowData row = new BillRowData(
                    unpayedBill.getBillID(),
                    unpayedBill.getPatientFirstName(),
                    unpayedBill.getPatientLastName(),
                    unpayedBill.getDate(),
                    unpayedBill.getTel(),
                    unpayedBill.getPatientID()
            );

            list.add(row);
        }
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idBill.setCellValueFactory(new PropertyValueFactory<BillRowData,Integer>("idBill"));
        nom.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<BillRowData,Date>("date"));
        tele.setCellValueFactory(new PropertyValueFactory<BillRowData,String>("tele"));
        try {
            setRowData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Double-click
                BillRowData selectedBill = table.getSelectionModel().getSelectedItem();
                if (selectedBill != null)  {
                    try {
                        System.out.println("Selected Bill: " + selectedBill.getNom() + " " + selectedBill.getPrenom());
                        System.out.println("ID Patient: "+selectedBill.getIdPatient());
                        int patientID = selectedBill.getBillId();
                        if (patientID>0)
                            mainController.switchToBill(event,selectedBill.getIdPatient());
                        else System.out.println("Erreur : ID patient Invalide.");
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}