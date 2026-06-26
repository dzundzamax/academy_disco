package com.betacom.disco.dtos;

import java.time.LocalDate;
import java.util.List;

public record EventResponseDto (
    Long id,
    String title,
    String musicGenre,
    Integer minimunAge,
    Integer basePrice,
    Double vipPrice,
    LocalDate date,
    List<String> imageUrls
) {}
