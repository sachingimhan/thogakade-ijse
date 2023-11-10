package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

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
public class Customer {

    public static List<CustomerDto> getCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDto> list = new ArrayList<>();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(new CustomerDto(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("tel")

            ));
        }
        return list;
    }

    public static boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO customer (id,name,address,tel) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getTel());
        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE customer SET name=?, address=?, tel=? WHERE id = ?");
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE customer SET name=?, address=?, tel=? WHERE id = ?");
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static CustomerDto getCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE id = ?");
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new CustomerDto(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("tel")
            );
        }
        return null;
    }

}
