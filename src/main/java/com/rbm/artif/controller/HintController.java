package com.rbm.artif.controller;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.dto.HintsDTO;
import com.rbm.artif.dto.ResponseHintDTO;
import com.rbm.artif.service.HintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/hint")
public class HintController {
    
    @Autowired
    private Environment environment;


    @Autowired
    private HintServiceImpl hintService;

    @PutMapping("/")
    public ResponseEntity<ResponseHintDTO> getHint(@RequestBody HintsDTO hintsDTO, HttpServletRequest request) throws ArtifException {
        String hinturl = environment.getProperty("HINT_API");
        String authHeader = request.getHeader("Authorization");

        if(authHeader==null || !authHeader.contains("Bearer ") || hinturl.equals("")){
          return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }

        ResponseHintDTO obj= hintService.getHint(hinturl,authHeader,hintsDTO);

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping("/close-session")
    public void deleteHint(HintsDTO deleteDTO, HttpServletRequest request){
        String hintURL= environment.getProperty("HINT_API");

        String authHeader= request.getHeader("Authorization");

//        webClientBuilder
//                .build()
//                .delete()
//                .uri(hintURL+"/delete-session"+deleteDTO.getSessionId())
//                .header("Authorization",authHeader)
//                .header("Content-Type","application/json")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();



    }
}
