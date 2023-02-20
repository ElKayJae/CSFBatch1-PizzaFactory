package vttp2022.assessment.csf.orderbackend.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class OrderSummary {
	private String orderId;
	private String name;
	private String email;
	private Float amount;

	public void setOrderId(String orderId) { this.orderId = orderId; }
	public String getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setAmount(Float amount) { this.amount = amount; }
	public Float getAmount() { return this.amount; }

	public static OrderSummary createOrderSummary( Order order, Float sum){
		OrderSummary summary = new OrderSummary();
        summary.setAmount(sum);
        summary.setEmail(order.getEmail());
        summary.setOrderId(order.getOrderId());
        summary.setName(order.getName());
		return summary;
	}

	public JsonObject toJson(){
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("name", getName());
		builder.add("email", getEmail());
		builder.add("orderid", getOrderId());
		builder.add("price", getAmount());
		
		return builder.build();
	}

}
