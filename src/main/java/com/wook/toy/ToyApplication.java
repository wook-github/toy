package com.wook.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy									// AOP는 관점 지향 프로그래밍, 스프링이 자동으로 개발자의 메서드를 호출하기 전에 가로챌 수 있게 하는 옵션
@SpringBootApplication									// Spring Boot 프로그램이라는 것을 나타냄.
public class ToyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}

}
