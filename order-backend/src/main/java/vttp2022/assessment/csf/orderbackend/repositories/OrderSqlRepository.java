package vttp2022.assessment.csf.orderbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

import static vttp2022.assessment.csf.orderbackend.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderSqlRepository {

    @Autowired
    JdbcTemplate template;

    public boolean insertOrder(Order order){
        String id = UUID.randomUUID().toString().substring(0,8);
        List<String> toppingsList = order.getToppings();
        StringBuilder sb = new StringBuilder();
        for (String s : toppingsList){
            sb.append(s);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        String toppings = sb.toString();
        int count = template.update(SQL_INSERT_ORDER, id, order.getName(), order.getEmail(), order.getSize(),
                                    order.isThickCrust(), order.getSauce(), toppings ,order.getComments());

        return count > 0;
    }

    public List<Order> getAllOrders(String email){
        SqlRowSet rs = template.queryForRowSet(SQL_FIND_ALL_ORDERS, email);
        List<Order> orderList = new LinkedList<>();
        while (rs.next()){
            orderList.add(Order.fromRowSet(rs)) ;
        }
        return orderList;
    }
}
