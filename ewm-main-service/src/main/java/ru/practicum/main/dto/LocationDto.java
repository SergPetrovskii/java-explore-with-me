package ru.practicum.main.dto;


import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private float lat;

    private float lon;
}
