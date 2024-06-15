package ru.practicum.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitStatDto {
    private String app;
    private String uri;
    private Long hits;
}
