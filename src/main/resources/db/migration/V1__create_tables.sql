
CREATE TABLE users (
     id            BIGSERIAL PRIMARY KEY,
     name          VARCHAR(100) NOT NULL,
     email         VARCHAR(150) NOT NULL UNIQUE,
     password_hash VARCHAR(255) NOT NULL,
     role          VARCHAR(20)  NOT NULL CHECK (role IN ('REQUESTER', 'TECHNICIAN', 'ADMIN')),
     active        BOOLEAN      NOT NULL DEFAULT TRUE,
     created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);


CREATE TABLE categories (
     id     BIGSERIAL PRIMARY KEY,
     name   VARCHAR(60) NOT NULL UNIQUE,
     active BOOLEAN     NOT NULL DEFAULT TRUE
);


CREATE TABLE sla_policies (
     id      BIGSERIAL PRIMARY KEY,
     priority      VARCHAR(20) NOT NULL UNIQUE CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
     response_minutes   INTEGER     NOT NULL,
     resolution_minutes INTEGER     NOT NULL
);


CREATE TABLE tickets (
     id                BIGSERIAL PRIMARY KEY,
     title             VARCHAR(150) NOT NULL CHECK (CHAR_LENGTH(title) BETWEEN 5 AND 150),
     description       TEXT         NOT NULL,
     status            VARCHAR(30)  NOT NULL DEFAULT 'OPEN' CHECK (status IN ('OPEN', 'IN_PROGRESS', 'WAITING_REQUESTER', 'RESOLVED', 'CLOSED')),
     priority          VARCHAR(20)  NOT NULL CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
     category_id       BIGINT       NOT NULL REFERENCES categories (id),
     requester_id      BIGINT       NOT NULL REFERENCES users (id),
     assignee_id       BIGINT       REFERENCES users (id),
     due_at            TIMESTAMP    NOT NULL,
     overdue           BOOLEAN      NOT NULL DEFAULT FALSE,
     first_response_at TIMESTAMP,
     resolved_at       TIMESTAMP,
     created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
     updated_at        TIMESTAMP    NOT NULL DEFAULT NOW()
);


CREATE TABLE comments (
     id         BIGSERIAL PRIMARY KEY,
     ticket_id  BIGINT    NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
     author_id  BIGINT    NOT NULL REFERENCES users (id),
     body       TEXT      NOT NULL,
     internal   BOOLEAN   NOT NULL DEFAULT FALSE,
     created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE attachments (
     id      BIGSERIAL PRIMARY KEY,
     ticket_id    BIGINT       NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
     file_name    VARCHAR(255) NOT NULL,
     content_type VARCHAR(100) NOT NULL,
     size_bytes   BIGINT       NOT NULL,
     storage_path VARCHAR(500) NOT NULL,
     created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);


CREATE TABLE ticket_history (
    id     BIGSERIAL PRIMARY KEY,
   ticket_id  BIGINT      NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
   changed_by BIGINT      REFERENCES users (id),
   field      VARCHAR(30) NOT NULL,
   old_value  VARCHAR(255),
   new_value  VARCHAR(255),
   changed_at TIMESTAMP   NOT NULL DEFAULT NOW()
);


CREATE INDEX idx_tickets_status    ON tickets (status);
CREATE INDEX idx_tickets_assignee  ON tickets (assignee_id);
CREATE INDEX idx_tickets_requester ON tickets (requester_id);
CREATE INDEX idx_tickets_due_at    ON tickets (due_at);