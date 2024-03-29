package com.covid.api.exceptions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime date;
    private String message;
    private StackTraceElement[] stackTrace;
}
