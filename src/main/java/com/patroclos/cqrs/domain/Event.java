package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;

public interface Event {

    LocalDateTime created();
}
