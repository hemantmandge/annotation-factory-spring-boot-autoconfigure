package com.annnotation.factory.autoconfigure.mask.introspector;

import com.annnotation.factory.autoconfigure.mask.annotation.MaskSensitiveData;
import com.annnotation.factory.autoconfigure.mask.serializer.MaskSensitiveDataSerializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class MaskSensitiveDataAnnotationIntrospector extends NopAnnotationIntrospector {
    private static final long serialVersionUID = 1L;

    @Override
    public Object findSerializer(Annotated am) {
        MaskSensitiveData annotation = am.getAnnotation(MaskSensitiveData.class);
        if (annotation != null) {
            return new MaskSensitiveDataSerializer(annotation.strategy(), annotation.numberOfCharacters());
        }

        return null;
    }


    @Override
    public Object findDeserializer(Annotated am) {
        return null;
    }
}
