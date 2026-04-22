package com.rbm.artif.controller;

import com.rbm.artif.dto.HintsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/hint")
public class HintController {
    
    @Autowired
    private Environment environment;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PutMapping("/")
    public void getHint(@RequestBody HintsDTO hintsDTO, HttpServletRequest request) {
        String hinturl = environment.getProperty("HINT_API");
        String authHeader = request.getHeader("Authorization");
        webClientBuilder
                .build()
                .post()
                .uri(hinturl + "/getHint")
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .bodyValue(hintsDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
