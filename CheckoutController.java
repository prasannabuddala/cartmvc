package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cartItemsJson = request.getParameter("cartitems");
		Gson gson = new Gson();
		List<CartItem> cartItems = (ArrayList<CartItem>) gson.fromJson(cartItemsJson, ArrayList.class);

		int customerId = 123;

        int orderId = generateRandomOrderId();
        
        double orderTotal = calculateTotalOrderCost(cartItems);

        OrdersDAL orders = new OrdersDAL();
        orders.insertOrder(orderId, orderTotal, customerId);
        
        ProductOrdersDAL productorders = new ProductOrdersDAL();
        for (CartItem item : cartItems) {
            Products product = new GetProductsDAL().getProductById(item.getPid());
            productorders.insertProductOrder(orderId, item.getPid(), item.getQuantity(), product.getPprice());
        }
        
	}
	private int generateRandomOrderId() {
        return (int) (Math.random() * 10000) + 1;
    }
	private double calculateTotalOrderCost(List<CartItem> cartItems) {
        double totalCost = 0;
        for (CartItem item : cartItems) {
            Products product = new GetProductsDAL().getProductById(item.getPid());
            if (product != null) {
                totalCost += item.getQuantity() * product.getPprice();
            }
        }
        return totalCost;
    }

}
