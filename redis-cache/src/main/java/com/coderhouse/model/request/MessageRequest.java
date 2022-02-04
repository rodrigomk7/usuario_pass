package com.coderhouse.model.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String description;
}
