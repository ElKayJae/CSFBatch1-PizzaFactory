package vttp2022.assessment.csf.orderbackend.repositories;

public class Queries {
    final static String SQL_INSERT_ORDER = """
            insert into orders(order_id, name, email, pizza_size, thick_crust, sauce, toppings, comments) 
            values(?,?,?,?,?,?,?,?)
            """;

    final static String SQL_FIND_ALL_ORDERS = "select * from orders where email = ?";
}
