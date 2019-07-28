package reactive_grpc_registry;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Wladimir Litvinov
 */
@Component
@Getter
public class ProcessorConfiguration {
	private final String processorServiceServer;
	private final String registryServer;
	private final int registryPort;

	public ProcessorConfiguration(@Value("${processor.server}") final String processorServiceServer,
								  @Value("${registry.server}") final String registryServer,
								  @Value("${registry.port}") final int registryPort) {
		this.processorServiceServer = processorServiceServer;
		this.registryServer = registryServer;
		this.registryPort = registryPort;
	}
}
