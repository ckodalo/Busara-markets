package com.predictions.predictions.models;

import com.predictions.predictions.enums.TradeAction;
import jakarta.persistence.*;
import lombok.*;
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

//    private String value;

    private int nShares;

    @ManyToOne
    @JoinColumn(name = "security_id")
    private Security security;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private TradeAction action;


    @Override
    public String toString() {
        return "Trade{" +
                "action=" + action +
                '}';
    }

}
