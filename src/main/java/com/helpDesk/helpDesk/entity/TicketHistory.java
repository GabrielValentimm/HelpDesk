package com.helpDesk.helpDesk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "/ticket+history")
@Getter
@Setter
@NoArgsConstructor
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    /** Quem fez a alteração. Nulo = o sistema (agendador). */

    @ManyToOne(fetch = FetchType.LAZY) // LAZY: carrega o relacionamento somente quando ele for acessado.
    @JoinColumn(name = "changed_by")
    private User changedBy;


    private String field;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;


    @Column(name = "changed_at")
    private LocalDateTime changedAt = LocalDateTime.now();
}
