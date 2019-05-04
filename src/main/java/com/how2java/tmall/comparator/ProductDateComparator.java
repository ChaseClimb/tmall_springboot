package com.how2java.tmall.comparator;
 
import java.util.Comparator;
 
import com.how2java.tmall.pojo.Product;

/**
 * 把 评价数量多的放前面
 */
public class ProductDateComparator implements Comparator<Product>{
 
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }
 
}