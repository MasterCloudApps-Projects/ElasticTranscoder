package es.videotranscoding.storage.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageAppConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}