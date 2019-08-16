package com.wgdetective.reactive_grpc_registry;

import com.salesforce.grpc.contrib.spring.GrpcServerHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveGrpcProcessorApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ReactiveGrpcProcessorApplication.class, args);
	}

	@Autowired
	private GrpcHostFactory grpcHostFactory;

	@Bean(initMethod = "start")
	public GrpcServerHost grpcServerHost() {
		return grpcHostFactory.create();
	}
}
