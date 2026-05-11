package com.rbm.artif.dto;

import com.rbm.artif.utilities.Premium;
import lombok.Data;

@Data
public class HintsDTO {
    String sessionId;
    String Question;
    String prevSolution;
    UsersDTO user;
}
