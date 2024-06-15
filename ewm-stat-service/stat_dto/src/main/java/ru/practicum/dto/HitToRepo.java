package ru.practicum.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitToRepo {
    private String app;
    private String uri;
    private Long hits;
}
