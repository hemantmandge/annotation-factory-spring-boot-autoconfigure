package com.example.mask.config;

import com.example.mask.introspector.MaskSensitiveDataAnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MaskObjectMapperConfig {

    @Primary
    @Autowired(required = true)
    public void objectMapper(ObjectMapper objectMapper) {
        AnnotationIntrospector serializationAnnotationIntrospector
                = AnnotationIntrospector.pair(objectMapper.getSerializationConfig().getAnnotationIntrospector(), new MaskSensitiveDataAnnotationIntrospector());
        objectMapper.setAnnotationIntrospectors(serializationAnnotationIntrospector, objectMapper.getDeserializationConfig().getAnnotationIntrospector());
    }
}
