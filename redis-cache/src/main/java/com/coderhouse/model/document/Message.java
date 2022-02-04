package com.coderhouse.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("message")
public class Message implements Serializable {

    @Id
    private String id;
    private String description;

}
