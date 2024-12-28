package com.pack.billingsystem;

import com.pack.billingsystem.models.Bill;
import com.pack.billingsystem.models.PatientBillDTO;
import com.pack.billingsystem.services.BillService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class BillingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BillsList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 700);
        String css = getClass().getResource("stylesheets/style.css").toExternalForm();
        primaryStage.setResizable(false);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Billing System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        BillService billService = new BillService();

        try {
            List<Bill> bills = billService.getAllBills();
            List<PatientBillDTO> unpayedBills = billService.getUnpayedBills(bills);
            System.out.println(bills.get(1).getPatient().getNom());
            for(PatientBillDTO bill:unpayedBills) {
                System.out.println(bill.getBillID()+" "+bill.getPatientID()+" "+
                                   bill.getPatientFirstName()+" "+
                                    bill.getPatientLastName()+" "+bill.getTel()+" "+
                                    bill.getDate());
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }


        launch(args);
    }
}