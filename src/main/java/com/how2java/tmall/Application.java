package com.how2java.tmall;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
    //继承SpringBootServletInitializer，打包成war包
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);    	
    }
}
