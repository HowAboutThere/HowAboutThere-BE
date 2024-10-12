package com.kosa2team.howaboutthere.trip.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kosa2team.howaboutthere.trip.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripService {

    private final ChatClient chatClient;

    @Value("classpath:/templates/themaPrompt.st")
    private Resource themaPrompt;

    @Value("classpath:/templates/touristSpotPrompt.st")
    private Resource spotPrompt;

    @Value("classpath:/templates/itineraryPrompt.st")
    private Resource itineraryPrompt;

    /**
     * thema 생성
     * @param dto : TripInfoDto로 startDate, endDate, budget, isDomestic 받아옴
     * @return List<ThemaDto> : 생성된 thema 리스트 반환
     */
    public List<ThemaDto> generateThema(TripInfoDto dto) {
        // dto to map
        ObjectMapper object = new ObjectMapper();
        Map<String,Object> map = object.convertValue(dto,Map.class);
        // promptTemplate 호출 후 데이터 삽입
        PromptTemplate pt = new PromptTemplate(themaPrompt);
        Prompt prompt = pt.create(map);
        //bedrock client 호출 후 ThemaDto에 넣어 리스트로 반환
        List<ThemaDto> response= chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<ThemaDto>>() {});
        return response;
    }

    /**
     * tourist spot 생성
     * @param dto : ThemaDto로 region, thema 받아옴
     * @return String : 생성된 tourist spot 반환
     */
    public List<TouristSpotDto> generateSpot(ThemaDto dto) {

        ObjectMapper object = new ObjectMapper();
        Map<String, Object> map = object.convertValue(dto,new TypeReference<>() {});
        PromptTemplate pt = new PromptTemplate(spotPrompt);
        Prompt prompt = pt.create(map);
        List<TouristSpotDto> response = chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<TouristSpotDto>>() {});
        return response;

    }

    public List<ItineraryDto> generateItinerary(ItineraryInfoDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", dto.startDate());
        map.put("endDate", dto.endDate());

        List<String> touristspots = new ArrayList<>();
        for (TouristSpotDto spotsList : dto.touristspots()) {
            touristspots.add(spotsList.spotname());
        }
        map.put("touristspots", touristspots);

        PromptTemplate pt = new PromptTemplate(itineraryPrompt);
        Prompt prompt = pt.create(map);
        List<ItineraryDto>response = chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<ItineraryDto>>() {});
        return response;
    }
}
