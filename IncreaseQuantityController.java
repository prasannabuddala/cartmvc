package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class IncreaseQuantityController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int prodId = Integer.parseInt(request.getParameter("pid"));
       
        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartitems");
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                if (item.getPid() == prodId) {
                    item.setQuantity(item.getQuantity() + 1);
                    break;
                }
            }
        }

        session.setAttribute("cartitems", cartItems);
        
        // Send response to update the UI
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("success");
    }
}
