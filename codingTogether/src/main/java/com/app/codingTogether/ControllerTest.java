package com.app.codingTogether;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerTest {
@GetMapping("/hello")
public ResponseEntity<String> firstEndpoint(){
	return ResponseEntity.status(HttpStatus.OK).body("all gucci");
}
}
