package br.com.dea.management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello World", description = "Hello World API")
public class HelloWorldController {
    @Operation(summary = "Get hello world message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @RequestMapping(value = "hello-world", method = RequestMethod.GET)
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("HELLO, WORLD!!!");
    }
}
