package com.dong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.dong.system.mapper"})
public class Erp2020Application {

    /**
     * 项目启动
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Erp2020Application.class, args);
    }

}
