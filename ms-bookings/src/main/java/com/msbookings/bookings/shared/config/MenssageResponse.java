package com.msbookings.bookings.shared.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms")
public class MenssageResponse {
    private Map<String, String> messages;
    public static final String SP = ";";
    public static final String OK = "OK";
    public static final String BR400 = "BR400";
    public static final String F401 = "F401";
    public static final String E500 = "E500";
    public static final String E403 = "E403";
    public static final String E409 = "E409";
}
