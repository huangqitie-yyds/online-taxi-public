package com.huangqitie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huangqitie.mapper")
public class ServiceDriverUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDriverUserApplication.class, args);
	}

}
