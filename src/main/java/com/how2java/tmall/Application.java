package com.how2java.tmall;
import com.how2java.tmall.util.PortUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application extends SpringBootServletInitializer{
    static {
        PortUtil.checkPort(6379,"Redis 服务端",true);
    }
    //继承SpringBootServletInitializer，打包成war包
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}
