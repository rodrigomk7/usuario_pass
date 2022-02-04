package com.coderhouse.model.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
}
