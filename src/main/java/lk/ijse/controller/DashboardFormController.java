package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.AppInit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class DashboardFormController implements Initializable {

    @FXML
    public AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"))));
        stage.setTitle("Customer");
        stage.show();
        // hide current window
        ((Node)actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        /**
         * if you need to load fxml to the view without opening a new window here is how you do it
         * 1. Add Stack Pane where you want to load other fxml file and add fxid (ex: rootpane)
         * 2. make your other fxml with root component as AnchorPane this way you will be able to change the design
         * code:
         * FXMLLoader fxml = new FXMLLoader(this.getClass().getResource("/view/your_form.fxml"));
         * AnchorPane frmItem = fxml.load();
         * rootpane.getChildren().setAll(frmItem);
         */

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/placeorder_form.fxml"))));
        stage.setTitle("Orders");
        stage.show();
    }
}
