package vttp2022.assessment.csf.orderbackend.models;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.bson.Document;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private String orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(String orderId) { this.orderId = orderId; }
	public String getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order createOrder(JsonObject o){
		Order order = new Order();
		order.setName(o.getString("name"));
		order.setEmail(o.getString("email"));
		order.setSauce(o.getString("sauce"));
		JsonArray arr = o.getJsonArray("toppings");
		List<String> toppings = IntStream.range(0, arr.size())
										.mapToObj(i -> arr.getString(i)).toList();
		// for (int i = 0; i < arr.size(); i++) {
		// 	toppings.add(arr.getString(i));
		// }
		order.setToppings(toppings);

		if (o.getString("base").equals("thick")){
			order.setThickCrust(true);
		} else {
			order.setThickCrust(false);
		}
		order.setSize(o.getInt("size"));
		order.setComments(o.getString("comments", null));

		return order;
	}

	public Document toDocument(){
		Document d = new Document();
		d.put("name",getName());
		d.put("email",getEmail());
		d.put("base",isThickCrust());
		d.put("sauce",getSauce());
		d.put("size",getSize());
		d.put("toppings",getToppings());
		d.put("comments", getComments());
		return d;
	}

	public static Order fromDocument(Document d){
		Order order = new Order();
		order.setOrderId(d.getObjectId("_id").toString());
		order.setName(d.getString("name"));
		order.setEmail(d.getString("email"));
		order.setComments(d.getString("comments"));
		order.setSauce(d.getString("sauce"));
		order.setThickCrust(d.getBoolean("base"));
		System.out.println(d.getInteger("size"));
		order.setSize(d.getInteger("size"));
		List<String> toppings = d.getList("toppings", String.class);
		order.setToppings(toppings);
		return order;
	}

	public static Order fromRowSet(SqlRowSet rs){
		Order order = new Order();
		System.out.println("from row set");
		order.setOrderId(rs.getString("order_id").toString());
		order.setName(rs.getString("name"));
		order.setEmail(rs.getString("email"));
		order.setComments(rs.getString("comments"));
		order.setSauce(rs.getString("sauce"));
		order.setThickCrust(rs.getBoolean("thick_crust"));
		order.setSize(rs.getInt("pizza_size"));
		String[] toppingsArray = rs.getString("toppings").split(",");
		List<String> toppings = new LinkedList<>();
		for (String s : toppingsArray) toppings.add(s);
		order.setToppings(toppings);
		return order;
	}
}
