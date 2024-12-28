package com.pack.billingsystem;

import com.pack.billingsystem.controllers.BillingController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private BillingController billingController;

    public void switchToBill(MouseEvent event,int idPatient) throws IOException,SQLException {
        root = FXMLLoader.load(getClass().getResource("billing.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        billingController = new BillingController();
        stage.setScene(scene);
        stage.show();
    }
}
