package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductOrdersDAL {
 public boolean insertProductOrder(int orderId, int productId, int quantity, double price) {
     String sql = "INSERT INTO productorders (orderid, productid, productquantity, price) VALUES (?, ?, ?, ?)";
     
     try (
             Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","acoecse");
         PreparedStatement pstmt = conn.prepareStatement(sql)
     ) {
         pstmt.setInt(1, orderId);
         pstmt.setInt(2, productId);
         pstmt.setInt(3, quantity);
         pstmt.setDouble(4, price);
         
         int rowsAffected = pstmt.executeUpdate();
         return rowsAffected > 0;
     } catch (SQLException ex) {
         ex.printStackTrace();
         return false;
     }
 }
}
