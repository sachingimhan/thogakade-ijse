package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.PlaceOrderDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.model.Customer;
import lk.ijse.model.Item;
import lk.ijse.model.Order;
import lk.ijse.model.PlaceOrderItem;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class PlaceOrderFormController implements Initializable {
    public AnchorPane pane;
    public Label lblOrderId;
    public Label lblOrderDate;
    public JFXComboBox<String> cmbCustomerId;
    public Label lblCustomerName;
    public JFXComboBox<String> cmbItemCode;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtQty;
    public TableView tblOrderCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colAction;
    public JFXButton btnAddToCart;
    public Label lblNetTotal;
    private ObservableList<CartTm> obList = null;

    public void cmbCustomerOnAction(ActionEvent actionEvent) {

    }

    public void cmbItemOnAction(ActionEvent actionEvent) {

    }

    public void txtQtyOnAction(ActionEvent actionEvent) {

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String code = cmbItemCode.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");

        btn.setCursor(Cursor.HAND);


        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    tot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(tot);

                    calculateTotal();
                    tblOrderCart.refresh();
                    return;
                }
            }
        }
        var cartTm = new CartTm(code, description, qty, unitPrice, tot, btn);
        setRemoveBtnAction(btn, cartTm);
        obList.add(cartTm);

        tblOrderCart.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }

    private void setRemoveBtnAction(Button btn, CartTm cartTm) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
//                CartTm selectedItem = tblOrderCart.getSelectionModel().getSelectedItem();

                obList.remove(cartTm);
                tblOrderCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();

        List<CartTm> cartTmList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        System.out.println("Place order form controller: " + cartTmList);
        PlaceOrderDto placeOrderDto = new PlaceOrderDto(orderId, date, customerId, cartTmList);
        try {
            boolean isSuccess = PlaceOrderItem.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
        obList = FXCollections.observableArrayList();
        lblOrderDate.setText(String.valueOf(LocalDate.now()));

        try {
            // set next order id
            String nextOrderId = Order.generateNextOrderId();
            lblOrderId.setText(nextOrderId);
            // set customer ids
            List<CustomerDto> customers = Customer.getCustomers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (CustomerDto dto : customers) {
                list.add(dto.getId());
            }
            cmbCustomerId.setItems(list);
            //
            cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                try {
                    CustomerDto customer = Customer.getCustomer(t1);
                    lblCustomerName.setText(customer.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                try {
                    ItemDto item = Item.searchItem(t1);
                    lblDescription.setText(item.getDescription());
                    lblUnitPrice.setText(String.valueOf(item.getUnit_price()));
                    lblQtyOnHand.setText(String.valueOf(item.getQty_on_hand()));
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getNextOrderId();
        loadItemCodes();

    }

    private void getNextOrderId(){
        try {
            String orderId = Order.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadItemCodes(){
        try {
            List<ItemDto> allItems = Item.getAllItems();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (ItemDto item : allItems) {
                list.add(item.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
