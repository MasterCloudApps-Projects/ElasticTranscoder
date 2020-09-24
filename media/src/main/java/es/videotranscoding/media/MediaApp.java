package es.videotranscoding.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class MediaApp {

    public static void main(String[] args) {
        SpringApplication.run(MediaApp.class, args);
        log.info(" --------- App SpringBoot Started ------- ");
    }

}