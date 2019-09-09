package com.wgdetective.reactive_grpc_registry;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Wladimir Litvinov
 */
@AllArgsConstructor
@RestController
@RequestMapping("/registry")
public class RegistryController {
	private final RegistryService registryService;

	@GetMapping("/all")
	private Mono<List<String>> getAll() {
		return registryService.getAllRegisteredProcessors();
	}

	@GetMapping("/call")
	private Mono<String> call(@RequestParam final String target,
							  @RequestParam final String body) {
		return registryService.process(target, body).map(RegistryProto.ProcessorMessage::getMessageBody);
	}
}
