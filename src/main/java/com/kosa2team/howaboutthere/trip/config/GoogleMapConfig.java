package com.kosa2team.howaboutthere.trip.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

@Configuration
public class GoogleMapConfig {

    public String apiKey=getSecret();



    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }




    public String getSecret(){

        String secretName = "prod/google/api";
        Region region = Region.of("ap-northeast-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> secretMap;
        try {
            secretMap = objectMapper.readValue(getSecretValueResponse.secretString(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return secretMap.get("Google_API_KEY");
    }
}
