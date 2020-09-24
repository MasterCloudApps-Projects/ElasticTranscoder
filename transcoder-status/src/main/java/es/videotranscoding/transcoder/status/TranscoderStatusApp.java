package es.videotranscoding.transcoder.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class TranscoderStatusApp {

    public static void main(String[] args) {
        SpringApplication.run(TranscoderStatusApp.class, args);
        log.info(" --------- App SpringBoot Started ------- ");
    }
}