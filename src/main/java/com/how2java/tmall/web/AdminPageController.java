package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Created by chen
 * @date 2019/3/20 10:02
 */
@Controller
//页面跳转专用控制器
public class AdminPageController {
    //客户端跳转
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }

    //服务器跳转
    @GetMapping(value="/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }

    //跳转编辑页面
    @GetMapping(value="/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
    }


}
