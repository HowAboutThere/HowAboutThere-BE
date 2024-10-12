package com.kosa2team.howaboutthere.trip.dto;

/**
 *
 * @param startDate     // 여행 시작일
 * @param endDate       // 여행 종료일
 * @param budget        // 야행 예산
 * @param isDomestic    // 국내/외 여부
 */
public record TripInfoDto(String startDate, String endDate, int budget, Boolean isDomestic) {
}
