package es.videotranscoding.transcoder.status.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranscoderStatusConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
