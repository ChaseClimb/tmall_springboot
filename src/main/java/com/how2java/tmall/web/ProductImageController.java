package com.how2java.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.ImageUtil;

@RestController
public class ProductImageController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    CategoryService categoryService;

    /*
        展示单张图片和详情图片
     */
    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam("type") String type, @PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        if (ProductImageService.TYPE_SINGLE.equals(type)) {
            List<ProductImage> singles = productImageService.listSingleProductImages(product);
            return singles;
        } else if (ProductImageService.TYPE_DETAIL.equals(type)) {
            List<ProductImage> details = productImageService.listDetailProductImages(product);
            return details;
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/productImages")
    public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type,
                      MultipartFile image, HttpServletRequest request) throws Exception {
        ProductImage bean = new ProductImage();
        Product product = productService.get(pid);
        bean.setProduct(product);
        bean.setType(type);

        productImageService.add(bean);
        String folder = "img/";
        if (ProductImageService.TYPE_SINGLE.equals(bean.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        //File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(request.getServletContext().getRealPath(folder), bean.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            //  浏览器提交来的图片文件，有可能是png,gif,bmp等非jpg格式的图片。
            // 仅仅修改文件的后缀名有可能会导致显示异常。借助 ImageUtil工具类 真正保证文件的格式是jpg的。
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ProductImageService.TYPE_SINGLE.equals(bean.getType())) {
            String imageFolderSmall = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolderMiddle = request.getServletContext().getRealPath("img/productSingle_middle");
            File fSmall = new File(imageFolderSmall, fileName);
            File fMiddle = new File(imageFolderMiddle, fileName);
            if (!fSmall.getParentFile().exists()) {
                fSmall.getParentFile().mkdirs();
            }
            if (!fMiddle.getParentFile().exists()) {
                fMiddle.getParentFile().mkdirs();
            }
            //中等图片
            ImageUtil.resizeImage(file, 56, 56, fSmall);
            //小图片
            ImageUtil.resizeImage(file, 217, 190, fMiddle);
        }

        return bean;
    }

    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        if (ProductImageService.TYPE_SINGLE.equals(bean.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }

        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        //F:\IDEA_SPACE\tmall_springboot\src\main\webapp\img\
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if (ProductImageService.TYPE_SINGLE.equals(bean.getType())) {
            String imageFolderSmall = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolderMiddle = request.getServletContext().getRealPath("img/productSingle_middle");
            File fSmall = new File(imageFolderSmall, fileName);
            File fMiddle = new File(imageFolderMiddle, fileName);
            fSmall.delete();
            fMiddle.delete();
        }

        return null;
    }

}