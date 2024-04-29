package servlets;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersDAL {
 public boolean insertOrder(int orderId, double totalCost, int customerId) {
     String sql = "INSERT INTO orders (orderid, ototal, customerid) VALUES (?, ?, ?, ?)";
     
     try (
         Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","acoecse");
         PreparedStatement pstmt = conn.prepareStatement(sql)
     ) {
         pstmt.setInt(1, orderId);
         pstmt.setDouble(3, totalCost);
         pstmt.setInt(4, customerId);
         
         int rowsAffected = pstmt.executeUpdate();
         return rowsAffected > 0;
     } catch (SQLException ex) {
         ex.printStackTrace();
         return false;
     }
 }
}

