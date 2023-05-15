package cn.t.common.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.cloud.openfeign.support.PageJacksonModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

public class ObjectMapperUtil {

    public static ObjectMapper buildObjectMapper(PropertyNamingStrategy namingStrategy) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(namingStrategy)
                .build();
        extraDefaultConfig(objectMapper);
        return objectMapper;
    }

    public static void extraDefaultConfig(ObjectMapper objectMapper) {
        DateTimeFormatter deserializerDateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        DateTimeFormatter serializerDateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
        JavaTimeModule java8TimeModule = new JavaTimeModule();
        java8TimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(deserializerDateTimeFormatter));
        java8TimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(serializerDateTimeFormatter));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java8TimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        java8TimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        objectMapper.registerModule(java8TimeModule);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(simpleDateFormat);

        objectMapper.registerModule(new PageJacksonModule());

        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new FeignBeanSerializerModifier()));
    }

    private static class FeignBeanSerializerModifier extends BeanSerializerModifier {

        private static final NullArrayJsonSerializer nullArrayJsonSerializer = new NullArrayJsonSerializer();

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            for (BeanPropertyWriter beanProperty : beanProperties) {
                if (isArrayType(beanProperty)) {
                    beanProperty.assignNullSerializer(nullArrayJsonSerializer);
                }
            }
            return beanProperties;
        }
        private boolean isArrayType(BeanPropertyWriter writer) {
            Class<?> clazz = writer.getType().getRawClass();
            return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
        }
    }

    private static class NullArrayJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    }
}
