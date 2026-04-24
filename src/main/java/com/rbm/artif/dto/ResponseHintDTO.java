package com.rbm.artif.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@RequiredArgsConstructor
public class ResponseHintDTO {
    public String question;
    public String hintProvided;
    public int hintCount;
    public String context;
    public List<String> hintTag= new ArrayList<>();
}
