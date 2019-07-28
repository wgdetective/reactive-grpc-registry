package com.wgdetective.reactive_grpc_registry;

import com.salesforce.grpc.contrib.spring.GrpcServerHost;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log
public class ReactiveGrpcRegistryApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ReactiveGrpcRegistryApplication.class, args);
	}

	@Bean(initMethod = "start")
	public GrpcServerHost grpcServerHost(@Value("${port}") final int port) {
		log.info("Listening for gRPC on port " + port);
		return new GrpcServerHost(port);
	}

}
