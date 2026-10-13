package com.ejemplo.banco.lab1v20206.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public String healthCheck() {
		return "HEALTH CHECK OK!";
	}

	@GetMapping("/version")
	public String version() {
		return "The actual version is 1.0.0";
	}
}
