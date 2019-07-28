package reactive_grpc_registry;

import com.salesforce.grpc.contrib.spring.GrpcService;
import com.wgdetective.reactive_grpc_registry.ReactorProcessorGrpc;
import com.wgdetective.reactive_grpc_registry.RegistryProto;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
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
}
