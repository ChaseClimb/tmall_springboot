package com.how2java.tmall.service;
 
import com.how2java.tmall.dao.OrderDAO;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class OrderService {
    public static final String WAIT_PAY = "waitPay";
    public static final String WAIT_DELIVERY = "waitDelivery";
    public static final String WAIT_CONFIRM = "waitConfirm";
    public static final String WAIT_REVIEW = "waitReview";
    public static final String FINISH = "finish";
    public static final String DELETE = "delete";

    @Autowired OrderDAO orderDAO;
     
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =orderDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
 
    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    //将订单项的订单属性设置为空

    //比如有个 order, 拿到它的 orderItems， 然后再把这些orderItems的order属性，设置为空，
    //在把一个Order转换为json的同时，会把其对应的 orderItems 转换为 json数组， 而 orderItem对象上有 order属性，
    // 这个order 属性又会被转adminHeader换为 json对象 无限递归
    private void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems= order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }
 
    public Order get(int oid) {
        return orderDAO.findOne(oid);
    }
 
    public void update(Order bean) {
        orderDAO.save(bean);
    }
 
}