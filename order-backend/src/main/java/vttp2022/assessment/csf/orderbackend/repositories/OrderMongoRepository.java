package vttp2022.assessment.csf.orderbackend.repositories;


import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderMongoRepository {
    
    @Autowired
    MongoTemplate template;

    //db.orders.insert({name : "name", email : "email .....,"})
    public String insertOrder(Order order){
        Document toInsert = order.toDocument();
        Document inserted = template.insert(toInsert, "orders");
        String id = inserted.getObjectId("_id").toString();
        return id;
    }
    
    //db.orders.find( email : "email"})
    public List<Order> findAllOrders(String email){
        Criteria c = Criteria.where("email").is(email);
        Query q = Query.query(c);
        List<Document> docList = template.find(q, Document.class, "orders");
        List<Order> orderList = docList.stream().map(d -> Order.fromDocument(d)).toList();
        
        return orderList;
    }
    
    
}
