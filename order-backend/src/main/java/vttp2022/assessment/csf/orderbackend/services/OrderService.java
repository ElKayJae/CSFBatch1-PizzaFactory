package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderMongoRepository;
import vttp2022.assessment.csf.orderbackend.repositories.OrderSqlRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderMongoRepository mongoRepo;

	@Autowired
	private OrderSqlRepository sqlRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(JsonObject o) {
		Order order = Order.createOrder(o);
		String id = mongoRepo.insertOrder(order);
		System.out.println(id);
	}

	public boolean createOrderSQL(JsonObject o) {
		Order order = Order.createOrder(o);
		return sqlRepo.insertOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		List<Order> orderList = mongoRepo.findAllOrders(email);
		List<OrderSummary> summaryList = orderList.stream().map(o -> calculatePrice(o)).toList();

		return summaryList;
	}

	public List<OrderSummary> getOrdersByEmailSQL(String email) {
		// Use priceSvc to calculate the total cost of an order
		List<Order> orderList = sqlRepo.getAllOrders(email);
		List<OrderSummary> summaryList = orderList.stream().map(o -> calculatePrice(o)).toList();

		return summaryList;
	}

	public OrderSummary calculatePrice(Order order){
        System.out.println("size >>>>>>" + order.getSize());
        Float sum = 0f;
        sum += priceSvc.size(order.getSize());
        priceSvc.sauce(order.getSauce());
        if (order.isThickCrust() == true){
            sum += priceSvc.thickCrust();
        } else {
            priceSvc.thinCrust();
        }
        for (String t : order.getToppings()){
            sum += priceSvc.topping(t);
        }
        
        return OrderSummary.createOrderSummary(order, sum);
    }
}
