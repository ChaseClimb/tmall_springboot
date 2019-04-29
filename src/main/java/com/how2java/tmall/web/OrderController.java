package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page4Navigator;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    /*
    *
    *

    {
    "totalPages": 1,
    "number": 0,
    "totalElements": 1,
    "size": 5,
    "numberOfElements": 1,
    "content": [
        {
            "id": 1,
            "user": {
                "id": 3,
                "password": "password3",
                "name": "测试用户3",
                "salt": null,
                "anonymousName": "测***3"
            },
            "orderCode": "201608241638122609867",
            "address": "某某市，某某区，某某街道，某某号 ",
            "post": "610000",
            "receiver": "某某某",
            "mobile": "15111111111",
            "userMessage": null,
            "createDate": 1483027200000,
            "payDate": null,
            "deliveryDate": 1556547587000,
            "confirmDate": null,
            "status": "waitConfirm",
            "orderItems": [
                {
                    "id": 2,
                    "product": {
                        "id": 88,
                        "category": {
                            "id": 83,
                            "name": "平板电视",
                            "products": null,
                            "productsByRow": null
                        },
                        "name": "Hisense/海信 LED49EC320A 49吋led液晶电视机智能网络平板电视50",
                        "subTitle": "新品特惠 十核智能 内置WiFi 咨询有惊喜",
                        "originalPrice": 2799,
                        "promotePrice": 1679.4,
                        "stock": 90,
                        "createDate": 1469695418000,
                        "firstProductImage": {
                            "id": 643,
                            "type": "single"
                        }
                    },
                    "order": null,
                    "user": {
                        "id": 3,
                        "password": "password3",
                        "name": "测试用户3",
                        "salt": null,
                        "anonymousName": "测***3"
                    },
                    "number": 2
                },
                {
                    "id": 1,
                    "product": {
                        "id": 87,
                        "category": {
                            "id": 83,
                            "name": "平板电视",
                            "products": null,
                            "productsByRow": null
                        },
                        "name": "Konka/康佳 LED32S1卧室32吋安卓智能无线WIFI网络液晶平板电视机",
                        "subTitle": "32吋电视机 8核智能 网络 全国联保 送货上门",
                        "originalPrice": 1799,
                        "promotePrice": 1104.35,
                        "stock": 98,
                        "createDate": 1471077812000,
                        "firstProductImage": {
                            "id": 10202,
                            "type": "single"
                        }
                    },
                    "order": null,
                    "user": {
                        "id": 3,
                        "password": "password3",
                        "name": "测试用户3",
                        "salt": null,
                        "anonymousName": "测***3"
                    },
                    "number": 3
                }
            ],
            "total": 6671.8496,
            "totalNumber": 5,
            "statusDesc": "待收货"
        }
    ],
    "first": true,
    "last": true,
    "navigatePages": 5,
    "navigatepageNums": [
        1
    ],
    "hasContent": true,
    "hasNext": false,
    "hasPrevious": false
}
    * */
    @GetMapping("/orders")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, 5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.WAIT_CONFIRM);
        orderService.update(o);
        return Result.success(o);
    }
}