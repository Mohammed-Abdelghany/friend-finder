package com.example.friendfinder.helper;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")

public class JwtStructure {
    public  String SECRET_KEY ;
    public  Duration duration;
}
