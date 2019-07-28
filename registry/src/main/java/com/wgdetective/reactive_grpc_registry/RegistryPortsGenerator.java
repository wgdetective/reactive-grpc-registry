package com.wgdetective.reactive_grpc_registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Wladimir Litvinov
 */
@Component
public class RegistryPortsGenerator {
	@Value("${port}")
	private int lastUsed;

	public synchronized int getPort() {
		return ++lastUsed;
	}
}
