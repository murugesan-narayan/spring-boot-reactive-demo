package com.muru.dcb.reactive.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class SpringBootReactiveDemoApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		SpringApplication.run(SpringBootReactiveDemoApplication.class, args);
	}

}
