package com.flight.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
