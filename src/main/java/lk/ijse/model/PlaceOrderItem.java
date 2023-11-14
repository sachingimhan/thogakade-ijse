package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PlaceOrderDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class PlaceOrderItem {
    public static boolean placeOrder(PlaceOrderDto dto) throws SQLException {
        Connection con = null;
        try{

            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            // set auto commit = false
            //1. order table
            //2. item table
            //3. order_details table
            // commit
            // else
            // rollback
            // finally
            // set auto commit = true

            boolean isOrderSaved = Order.saveOrder(dto.getOrderId(), dto.getCustomerId(), dto.getDate());
            if (isOrderSaved){
                boolean isItemUpdated = updateItem(dto.getCartTmList());
                if (isItemUpdated){
                    boolean isOrderDetailsSaved = OrderItem.saveOrderDetails(dto.getOrderId(), dto.getCartTmList());
                    if (isOrderDetailsSaved){
                        // all 3 queries must be success
                        con.commit();
                        return true;
                    }else {
                        con.rollback();
                    }
                }else {
                    con.rollback();
                }
            }else {
                con.rollback();
            }

        } catch (SQLException | ClassNotFoundException e) {
            if (con != null) con.rollback();
            e.printStackTrace();
        }finally {
            if (con != null) con.setAutoCommit(true);
        }

        return false;
    }

    private static boolean updateItem(List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        for (CartTm tm : cartTmList) {
            boolean isUpdateItem = Item.updateQty(tm.getCode(), tm.getQty());
            if (!isUpdateItem){
                return false;
            }
        }
        return true;
    }

}
