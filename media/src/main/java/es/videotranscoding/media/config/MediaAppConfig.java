package es.videotranscoding.media.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MediaAppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}