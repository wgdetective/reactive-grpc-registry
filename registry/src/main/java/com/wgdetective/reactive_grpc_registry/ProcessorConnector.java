package com.wgdetective.reactive_grpc_registry;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Wladimir Litvinov
 */
@RequiredArgsConstructor
@Data
public class ProcessorConnector {
	private final String processorSever;
	private final int processorPort;

	private boolean active = false;
	private ReactorProcessorGrpc.ReactorProcessorStub processorConnection;

	public synchronized void createConnection() {
		if (!active) {
			final ManagedChannel channel = ManagedChannelBuilder
				.forAddress(processorSever, processorPort)
				.usePlaintext().build();
			processorConnection = ReactorProcessorGrpc.newReactorStub(channel);
			active = true;
		}
	}

}
