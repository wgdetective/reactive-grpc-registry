package reactive_grpc_registry;

import com.salesforce.grpc.contrib.spring.GrpcServerHost;
import com.wgdetective.reactive_grpc_registry.ReactorRegistryGrpc;
import com.wgdetective.reactive_grpc_registry.RegistryProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Wladimir Litvinov
 */
@AllArgsConstructor
@Log
@Component
public class GrpcHostFactory {

	private final ProcessorConfiguration configuration;

	public GrpcServerHost create() {
		final ManagedChannel channel = ManagedChannelBuilder
			.forAddress(configuration.getRegistryServer(), configuration.getRegistryPort())
			.usePlaintext().build();
		final ReactorRegistryGrpc.ReactorRegistryStub stub = ReactorRegistryGrpc.newReactorStub(channel);
		final Mono<RegistryProto.RegisterRequest> req = Mono.just(buildRegistryMessage());
		final Mono<RegistryProto.RegisterResponse> register = stub.register(req);

		//TODO rewrite it
		final int port = register.block().getPort();
		log.info("Listening for gRPC on port " + port);
		final GrpcServerHost grpcServerHost = new GrpcServerHost(port);

		stub.notifyAboutSuccessfulRegistration(req).block();

		return grpcServerHost;
	}

	private RegistryProto.RegisterRequest buildRegistryMessage() {
		return RegistryProto.RegisterRequest.newBuilder()
			.setName("Processor-" + UUID.randomUUID().toString())
			.setServer(configuration.getProcessorServiceServer())
			.build();
	}
}
