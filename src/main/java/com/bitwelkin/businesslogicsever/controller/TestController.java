package com.bitwelkin.businesslogicsever.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String testMethod() {
		return "test";
	}
}
