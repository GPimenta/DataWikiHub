package org.wikipedia.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String timestamp = jsonElement.getAsString();
        if (timestamp.endsWith("Z")) {
            timestamp = timestamp.substring(0, timestamp.length() - 1);
        }
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME) + "Z");
    }
}
