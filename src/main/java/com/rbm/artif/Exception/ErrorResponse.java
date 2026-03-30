package com.rbm.artif.Exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    String message;

    public ErrorResponse(String msg) {
        this.message=msg;
    }
}
