package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther sachin
 * @date 2023-11-08
 */
public class Item {

    public static List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        List<ItemDto> list = new ArrayList<>();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM item");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            list.add(new ItemDto(
                    rs.getString("code"),
                    rs.getString("description"),
                    rs.getDouble("unit_price"),
                    rs.getInt("qty_on_hand")
            ));
        }
        return list;
    }

    public static boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty_on_hand = qty_on_hand - ? WHERE code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0;
    }

    public static ItemDto searchItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }
}
