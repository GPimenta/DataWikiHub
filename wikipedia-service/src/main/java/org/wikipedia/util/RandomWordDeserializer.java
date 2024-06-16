package org.wikipedia.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.wikipedia.model.RandomWord;

import java.lang.reflect.Type;
import java.util.List;

public class RandomWordDeserializer implements JsonDeserializer<RandomWord> {
    @Override
    public RandomWord deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<String> words = jsonDeserializationContext.deserialize(jsonElement, List.class);
        return new RandomWord(words);
    }
}
