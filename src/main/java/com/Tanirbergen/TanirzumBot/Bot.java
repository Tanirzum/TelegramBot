package com.Tanirbergen.TanirzumBot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("application.properties")
@Data
public class Bot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
}
