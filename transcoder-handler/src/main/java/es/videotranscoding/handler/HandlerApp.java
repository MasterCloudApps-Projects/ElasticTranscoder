package es.videotranscoding.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class HandlerApp {

    public static void main(String[] args) {
        SpringApplication.run(HandlerApp.class, args);
        log.info(" --------- App SpringBoot Started ------- ");
    }

}