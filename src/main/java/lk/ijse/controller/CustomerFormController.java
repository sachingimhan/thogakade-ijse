package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class CustomerFormController implements Initializable {

    public AnchorPane root;
    public TextField txtId;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtTel;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        // table listener
        tableListener();
        try {
            // load all customers
            getAllCustomers();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || tel.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Field Not found").showAndWait();
            return;
        }
        CustomerDto dto = new CustomerDto(
                id,
                name,
                address,
                tel
        );
        try {
            boolean isSaved = Customer.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Success").showAndWait();
                getAllCustomers();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").showAndWait();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDto> customers = Customer.getCustomers();
        List<CustomerTm> tmList = new ArrayList<>();
        for(CustomerDto dto: customers){
            tmList.add(new CustomerTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getTel()
            ));
        }
        updateCustomerTable(tmList);
    }

    private void updateCustomerTable(List<CustomerTm> list){
        tblCustomer.setItems(FXCollections.observableArrayList(list));
        tblCustomer.refresh();
    }

    private void tableListener(){
        tblCustomer.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, customerTm, t1) -> {
            txtId.setText(t1.getId());
            txtName.setText(t1.getName());
            txtAddress.setText(t1.getAddress());
            txtTel.setText(t1.getTel());
        });
    }
}
