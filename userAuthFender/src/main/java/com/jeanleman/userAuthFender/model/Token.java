package com.jeanleman.userAuthFender.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    @NonNull
    private String token;
    @JoinColumn(name = "user_id")
    private long userId;
    private LocalDateTime created;

}
