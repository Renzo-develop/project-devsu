package utils;

import com.devsu.msusuario.entity.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestUtils {

    public static <T> T jacksonConvertJSONFileToObject(File file, Class<T> classType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDateFormat(new StdDateFormat())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                .enable(JsonParser.Feature.ALLOW_COMMENTS)
                .readValue(FileUtils.readFileToString(file, StandardCharsets.UTF_8), classType);

    }

    public static <T> List<T> buildListFronJSONFile(File file, Class<T> classType) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.readValue(FileUtils.readFileToString(file, StandardCharsets.UTF_8), new TypeReference<List<T>>(){});
    }

    public static ApiResponse buildApiResponse(String message, Object response) {
        return ApiResponse.builder()
                .message(message)
                .response(response)
                .build();
    }
}
