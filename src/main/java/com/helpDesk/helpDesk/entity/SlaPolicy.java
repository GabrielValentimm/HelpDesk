package com.helpDesk.helpDesk.entity;

import com.helpDesk.helpDesk.enums.Priority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sla_policy")
@Getter
@Setter
@NoArgsConstructor
public class SlaPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Enumerated
    private Priority priority;

    @Column(name = "reponse_minutes")
    private int responseMinutes;

    @Column(name = "resolution_minutes")
    private int resolutionMinutes;
}
