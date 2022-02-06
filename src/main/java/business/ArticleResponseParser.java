package business;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ArticleResponseParser {

    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ArticlesResponse parse(String responseBody) {
        ArticlesResponse articlesResponse = null;

        try {

            articlesResponse = objectMapper.readValue(responseBody, ArticlesResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articlesResponse;
    }
}