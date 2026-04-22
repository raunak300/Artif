package com.rbm.artif.Exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    String message;

    public ErrorResponse(String msg) {
        this.message=msg;
    }

    public String getMessage() {
        return message;
    }
}
