package com.wgdetective.reactive_grpc_registry;

import com.google.protobuf.Empty;
import com.salesforce.grpc.contrib.spring.GrpcService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Wladimir Litvinov
 */
@AllArgsConstructor
@GrpcService
@Service
public class RegistryService extends ReactorRegistryGrpc.RegistryImplBase {
	private final RegistryPortsGenerator registryPortsGenerator;

	private final Map<String, ProcessorConnector> registeredProcessors = new HashMap<>();

	@Override
	public Mono<RegistryProto.RegisterResponse> register(final Mono<RegistryProto.RegisterRequest> request) {
		return request.flatMap(r -> {
			final RegistryProto.RegisterResponse registerResponse = RegistryProto.RegisterResponse.newBuilder()
				.setPort(registryPortsGenerator.getPort())
				.setServer(r.getServer())
				.build();
			final ProcessorConnector processorConnector =
				new ProcessorConnector(r.getServer(), registerResponse.getPort());
			registeredProcessors.put(r.getName(), processorConnector);
			return Mono.just(registerResponse);
		});
	}

	public Mono<List<String>> getAllRegisteredProcessors() {
		return Mono.just(registeredProcessors.entrySet().stream()
							 .map(e -> MessageFormat.format("{0} -> {1}:{2}", e.getKey(),
															e.getValue().getProcessorSever(),
															e.getValue().getProcessorPort()))
							 .collect(Collectors.toList()));
	}

	@Override
	public Mono<Empty> notifyAboutSuccessfulRegistration(final Mono<RegistryProto.RegisterRequest> request) {
		return request.map(r -> {
			registeredProcessors.get(r.getName()).createConnection();
			return Empty.getDefaultInstance();
		});
	}

	public Mono<RegistryProto.ProcessorMessage> process(final String target, final String messageBody) {
		return registeredProcessors.get(target).getProcessorConnection().process(Mono.just(
			RegistryProto.ProcessorMessage.newBuilder()
				.setMessageId(UUID.randomUUID().toString())
				.setMessageBody(messageBody)
				.build()));
	}
}
