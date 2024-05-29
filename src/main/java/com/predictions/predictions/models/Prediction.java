package com.predictions.predictions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private int nShares;

    @ManyToOne
    @JoinColumn(name = "security_id")
    private Security security;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
