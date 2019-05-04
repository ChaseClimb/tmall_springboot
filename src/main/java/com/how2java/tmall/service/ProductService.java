package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.util.Page4Navigator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    public void add(Product bean) {
        productDAO.save(bean);
    }

    public void delete(int id) {
        productDAO.delete(id);
    }

    public Product get(int id) {
        return productDAO.findOne(id);
    }

    public void update(Product bean) {
        productDAO.save(bean);
    }

    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    /**
     * 为多个分类填充产品集合
     *
     * @param categorys
     */
    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }

    public void fill(Category category) {
        List<Product> products = listByCategory(category);
        //用于首页底部图片展示
        productImageService.setFirstProdutImages(products);
        category.setProducts(products);
    }

    /**
     * 把分类下的产品集合，按照8个为一行，拆成多行
     *
     * @param categorys
     */
    public void fillByRow(List<Category> categorys) {
        for (Category category : categorys) {
            fillByRow(category);
        }
    }

    private void fillByRow(Category category) {
        int productNumberEachRow = 8;
        List<Product> products = category.getProducts();
        List<List<Product>> productsByRow = new ArrayList<>();
        for (int i = 0; i < products.size(); i += productNumberEachRow) {
            int endIndex = i + productNumberEachRow;
            endIndex = endIndex > products.size() ? products.size() : endIndex;
            List<Product> productsOfEachRow = products.subList(i, endIndex);
            productsByRow.add(productsOfEachRow);
        }
        category.setProductsByRow(productsByRow);
    }

    private List<Product> listByCategory(Category category) {
        return productDAO.findByCategoryOrderById(category);
    }


    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);

    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    public List<Product> search(String keyword, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        List<Product> products = null;
        if (StringUtils.isNotBlank(keyword) && !"null".equals(keyword)) {
            products = productDAO.findByNameLike("%" + keyword + "%", pageable);
        } else {
            products = productDAO.findAll(pageable).getContent();
            System.out.println("66");
        }
        return products;
    }


}