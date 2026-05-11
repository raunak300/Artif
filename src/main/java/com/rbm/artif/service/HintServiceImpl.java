package com.rbm.artif.service;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.dto.HintsDTO;
import com.rbm.artif.dto.ResponseHintDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class HintServiceImpl {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ResponseHintDTO getHint(String hinturl, String authHeader, HintsDTO hintsDTO) throws ArtifException {
        ResponseHintDTO obj=null;

        try{
            //System.out.println(hinturl);
            obj = webClientBuilder
                    .build()
                    .post()
                    .uri(hinturl + "/getHint")
                    .header("Authorization", authHeader)
                    .header("Content-Type", "application/json")
                    .bodyValue(hintsDTO)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), res ->
                            res.bodyToMono(String.class)
                                    .map(err -> new ArtifException("Client error: " + err))
                    )
                    .onStatus(status -> status.is5xxServerError(), res ->
                            res.bodyToMono(String.class)
                                    .map(err -> new ArtifException("Server error: " + err))
                    )
                    .bodyToMono(ResponseHintDTO.class)
                    .block(Duration.ofSeconds(10) );
        }
        catch (RuntimeException e) {
            throw new ArtifException(e.getMessage());
        }



        return obj;
    }





}
