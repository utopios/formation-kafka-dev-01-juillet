package com.example.demobasekafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAvro {
    private User User;
}
