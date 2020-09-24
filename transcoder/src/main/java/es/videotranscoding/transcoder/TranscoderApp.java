package es.videotranscoding.transcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TranscoderApp {

    public static void main(String[] args) {
        SpringApplication.run(TranscoderApp.class, args);
        log.info(" --------- App SpringBoot Started ------- ");
    }
}