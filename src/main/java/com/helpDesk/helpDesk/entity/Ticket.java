package com.helpDesk.helpDesk.entity;

import com.helpDesk.helpDesk.enums.TicketStatus;
import jakarta.annotation.Priority;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "/tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    public boolean overdue;

    @Column(name = "first_response_At")
    private LocalDateTime firstResponseAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolved_at;

    // Utilizado para quando o chamado estiver em WAITING_REQUESTER (sla pausado)

    @Column(name = "sla_paused_at")
    private LocalDateTime slaPaused_at;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;





    @PrePersist  // → executa antes de salvar um novo registro no banco.
    public void prePersist(){
        if (created_at == null ) {
            created_at = LocalDateTime.now();  // Se estiver null, ele coloca a data e hora atuais:
        }
        updated_at = created_at; // a data de atualização será igual à data de criação.
    }


    @PrePersist
    public void preUpdate() {
        updated_at = LocalDateTime.now();
    }
}
