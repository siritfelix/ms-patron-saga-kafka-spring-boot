package com.msflightsreservations.flightsreservations.infrastructure.mq.dto;

import java.io.Serializable;

import com.msflightsreservations.flightsreservations.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto implements Serializable {
    private String id;
    private String name;
    private String lastName;
    private String document;
    private String email;

    public User toUser() {
        return User.builder()
                .id(id)
                .name(lastName)
                .lastName(lastName)
                .document(document)
                .email(email)
                .build();
    }

    public UserDto(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.document = user.getDocument();
        this.email = user.getEmail();
    }
}
