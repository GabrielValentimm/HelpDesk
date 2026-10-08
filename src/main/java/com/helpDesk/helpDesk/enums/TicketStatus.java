package com.helpDesk.helpDesk.enums;

import com.helpDesk.helpDesk.entity.Ticket;

import java.util.List;

public enum TicketStatus {
    OPEN, IN_PROCESS, WAITING_REQUESTER, RESOLVED, CLOSED;

    public boolean canChangeTo(TicketStatus next) {
        List<TicketStatus> allowed = switch (this) {
            case OPEN -> List.of(IN_PROCESS);
            case IN_PROCESS -> List.of(WAITING_REQUESTER, RESOLVED);
            case WAITING_REQUESTER -> List.of(IN_PROCESS);
            case  RESOLVED -> List.of(CLOSED, IN_PROCESS);
            case CLOSED -> List.of();
        };
        return allowed.contains(next);
    }
}
