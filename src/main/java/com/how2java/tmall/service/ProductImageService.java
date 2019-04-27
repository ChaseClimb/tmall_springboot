package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductImageDAO;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    public static final String TYPE_SINGLE = "single";
    public static final String TYPE_DETAIL = "detail";

    @Autowired
    ProductImageDAO productImageDAO;

    @Autowired
    ProductService productService;

    public void add(ProductImage bean) {
        productImageDAO.save(bean);
    }

    public void delete(int id) {
        productImageDAO.delete(id);
    }

    public ProductImage get(int id) {
        return productImageDAO.findOne(id);
    }

    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, TYPE_SINGLE);
    }

    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, TYPE_DETAIL);
    }


    public void setFirstProdutImages(List<Product> products) {
        for (Product product : products) {
            setFirstProdutImage(product);
        }
    }

    public void setFirstProdutImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if (!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
            product.setFirstProductImage(new ProductImage());
        }
    }

}