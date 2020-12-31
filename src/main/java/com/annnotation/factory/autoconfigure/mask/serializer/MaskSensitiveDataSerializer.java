package com.annnotation.factory.autoconfigure.mask.serializer;

import com.annnotation.factory.autoconfigure.mask.enums.MaskingStrategies;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MaskSensitiveDataSerializer extends StdSerializer<String> {
    private static final long serialVersionUID = 1L;
    private final MaskingStrategies strategy;
    private final int numberOfCharacters;
    private static final String MASKING_SELECTION_CRITERIA = "[a-zA-Z_0-9]";
    private static final String MASK = "X";

    public MaskSensitiveDataSerializer(MaskingStrategies maskingStrategies, int numberOfCharacters) {
        super(String.class);
        this.strategy = maskingStrategies;
        this.numberOfCharacters = numberOfCharacters;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {


        String newMaskedValue;

        MaskingStrategies finalStrategy = (value.length() > numberOfCharacters ? strategy : MaskingStrategies.MASK_ALL);

        switch (finalStrategy) {
            case MASK_INITIAL:
                newMaskedValue = value.substring(0, numberOfCharacters)
                        .replaceAll(MASKING_SELECTION_CRITERIA, MASK)
                        .concat(value.substring(numberOfCharacters));
                break;

            case MASK_LAST:
                int subStringLength = value.length() - numberOfCharacters;
                newMaskedValue = value.substring(0, subStringLength)
                        .concat(value.substring(subStringLength).replaceAll(MASKING_SELECTION_CRITERIA, MASK));
                break;

            case MASK_ALL:
                newMaskedValue = value.replaceAll(MASKING_SELECTION_CRITERIA, MASK);
                break;

            case SKIP_INITIAL:
                newMaskedValue = value.substring(0, numberOfCharacters)
                        .concat(value.substring(numberOfCharacters)
                                .replaceAll(MASKING_SELECTION_CRITERIA, MASK));
                break;

            case SKIP_LAST:
                subStringLength = value.length() - numberOfCharacters;
                newMaskedValue = value.substring(0, subStringLength)
                        .replaceAll(MASKING_SELECTION_CRITERIA, MASK)
                        .concat(value.substring(subStringLength));
                break;

            default:
                newMaskedValue = value;
                break;
        }

        gen.writeString(newMaskedValue);
    }
}

