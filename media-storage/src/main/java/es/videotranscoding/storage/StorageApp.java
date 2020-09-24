package es.videotranscoding.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StorageApp {

    public static void main(String[] args) {
        SpringApplication.run(StorageApp.class, args);
        log.info(" --------- App SpringBoot Started ------- ");
    }

}