CREATE TABLE IF NOT EXISTS users
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email     VARCHAR(254)                            NOT NULL,
    user_name VARCHAR(250)                            NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    cat_name VARCHAR(200)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_categories_name UNIQUE (cat_name)
);

CREATE TABLE IF NOT EXISTS location
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat double                                  NOT NULL,
    lon double                                  NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS event
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  int                                     NOT NULL,
    request_moderation BOOLEAN DEFAULT TRUE,
    state_event        VARCHAR(50)                             NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_category_id FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_initiator_id FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_location_id FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS request
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR(50)                             NOT NULL,

    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT fk_request_event_id FOREIGN KEY (event_id) REFERENCES event (id) ON DELETE CASCADE,
    CONSTRAINT fk_request_requester_id FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(200)                            NOT NULL,

    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilations_id BIGINT NOT NULL,
    events_id       BIGINT NOT NULL,

    CONSTRAINT pk_compilations_events PRIMARY KEY (compilations_id, events_id),
    CONSTRAINT fk_compilations FOREIGN KEY (compilations_id) REFERENCES compilations (id) ON DELETE CASCADE,
    CONSTRAINT fk_events FOREIGN KEY (events_id) REFERENCES event (id) ON DELETE CASCADE
);