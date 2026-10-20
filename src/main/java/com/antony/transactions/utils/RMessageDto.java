package com.antony.transactions.utils;

import org.springframework.http.HttpStatus;

public record RMessageDto(HttpStatus status, String msg) {
}
