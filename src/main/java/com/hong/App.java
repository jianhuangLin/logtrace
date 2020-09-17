package com.hong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;
import java.util.Set;

@SpringBootApplication
@MapperScan("com.hong.mapper")//使用MapperScan批量扫描所有的Mapper接口；
//@ComponentScan(basePackages = { "com.hong.*" })
public class App extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);

	}


//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(App.class);
//	}

}
