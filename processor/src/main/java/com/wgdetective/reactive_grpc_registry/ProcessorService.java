package com.wgdetective.reactive_grpc_registry;

import com.salesforce.grpc.contrib.spring.GrpcService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Wladimir Litvinov
 */
@Log
@AllArgsConstructor
@Service
@GrpcService
public class ProcessorService extends ReactorProcessorGrpc.ProcessorImplBase {
	@Override
	public Mono<RegistryProto.ProcessorMessage> process(final Mono<RegistryProto.ProcessorMessage> request) {
		return request.map(r -> RegistryProto.ProcessorMessage.newBuilder()
			.mergeFrom(r)
			.setMessageBody("Message processed: " + r.getMessageBody())
			.build());
	}

	@Override
	public Flux<RegistryProto.ProcessorMessage> processStream(final Flux<RegistryProto.ProcessorMessage> request) {
		return request;
	}
}
