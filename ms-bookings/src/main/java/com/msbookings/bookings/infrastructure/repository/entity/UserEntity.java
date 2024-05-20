package com.msbookings.bookings.infrastructure.repository.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.msbookings.bookings.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String document;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private List<BookingEntity> bookings;

    public void addbooking(BookingEntity bookingEntity) {
        if (Objects.isNull(bookings) || bookings.isEmpty()) {
            bookings = new ArrayList<>();
            bookings.add(bookingEntity);
        } else {
            if (bookings.stream().noneMatch(booking -> booking.getId().equals(bookingEntity.getId())))
                bookings.add(bookingEntity);
        }
    }

    public User toUser() {
        return User.builder().id(id)
                .name(name).lastName(lastName).document(document).email(email)
                .build();
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.document = user.getDocument();
        this.email = user.getEmail();
    }
}
