package com.jeanleman.userAuthFender.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;
}
