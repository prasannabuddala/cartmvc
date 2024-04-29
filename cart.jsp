<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="servlets.CartItem" %>
<%@ page import="servlets.GetProductsDAL" %>
<%@ page import="servlets.Products" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
/* Style for the product container */
#product_container {
    border: 1px solid #ccc;
    padding: 10px;
    margin-bottom: 20px;
}

/* Style for the product name */
.product_name {
    font-weight: bold;
}

/* Style for the quantity and cost */
.quantity, .cost {
    margin-top: 5px;
}

/* Style for the buttons */
.button {
    padding: 5px 10px;
    cursor: pointer;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 3px;
    margin-right: 5px;
}

.button:hover {
    background-color: #0056b3;
}

</style>
</head>
<body>
<h1>Welcome to Cart</h1>
<%
    GetProductsDAL productsDAL = new GetProductsDAL(); // Instantiate GetProductsDAL
    List<CartItem> cartitems = (ArrayList<CartItem>) session.getAttribute("cartitems");
    if (cartitems != null) {
        for (CartItem item : cartitems) {
            Products product = productsDAL.getProductById(item.getPid()); // Fetch product details
%>
    <div id="product_<%= product.getPid() %>">
        <p>Product Name: <%= product.getPname() %></p>
        <p>Quantity: <span id="quantity_<%= product.getPid() %>"><%= item.getQuantity() %></span></p>
        <p>Cost: <%= product.getPprice() * item.getQuantity() %></p>
        <button class="decrease-btn" data-pid="<%= product.getPid() %>">-</button> 
        <button class="increase-btn" data-pid="<%= product.getPid() %>">+</button> 
    </div>
<%
        }
    }
%>
<button id="checkout-btn" class="button">Checkout</button>

<script>
$(document).ready(function() {
    $(".increase-btn").click(function() {
        var pid = $(this).data("pid");
        $.ajax({
            url: "IncreaseQuantityController",
            method: "GET",
            data: { pid: pid },
            success: function(data) {
                var quantityElement = $("#quantity_" + pid);
                var newQuantity = parseInt(quantityElement.text()) + 1;
                quantityElement.text(newQuantity);
            }
        });
    });

    $(".decrease-btn").click(function() {
        var pid = $(this).data("pid");
        $.ajax({
            url: "DecreaseQuantityController",
            method: "GET",
            data: { pid: pid },
            success: function(data) {
                var quantityElement = $("#quantity_" + pid);
                var newQuantity = parseInt(quantityElement.text()) - 1;
                if (newQuantity >= 0) {
                    quantityElement.text(newQuantity);
                }
            }
        });
    });
    $("#checkout-btn").click(function() {
        $.ajax({
            url: "CheckoutController", 
            method: "POST",
            data: { cartitems: JSON.stringify(<%= cartitems %> } },
            success: function(response) {
                alert("Checkout successful!"); 
            },
            error: function(xhr, status, error) {
                alert("Error during checkout: " + error); 
            }
        });
    });
});
</script>
</body>
</html>
