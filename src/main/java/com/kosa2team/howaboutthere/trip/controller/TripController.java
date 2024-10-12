package com.kosa2team.howaboutthere.trip.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kosa2team.howaboutthere.trip.dto.*;
import com.kosa2team.howaboutthere.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("trip/thema")
    public ResponseEntity<List<ThemaDto>> generateThema(@RequestBody TripInfoDto dto){
        return ResponseEntity.ok(tripService.generateThema(dto));
    }

    @GetMapping("trip/spot")
    public ResponseEntity<List<TouristSpotDto>> generateSpot(@RequestBody ThemaDto dto){
        return ResponseEntity.ok(tripService.generateSpot(dto));
    }

    @GetMapping("trip/itinerary")
    public ResponseEntity<List<ItineraryDto>> generateItinerary(@RequestBody ItineraryInfoDto dto){
        return ResponseEntity.ok(tripService.generateItinerary(dto));
    }

}
