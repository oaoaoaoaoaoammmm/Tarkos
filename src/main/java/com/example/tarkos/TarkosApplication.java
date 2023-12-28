package com.example.tarkos;

import com.example.tarkos.props.keys.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class TarkosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarkosApplication.class, args);
    }

}
