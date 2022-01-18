package org.sydneygael.example.domain;

import java.time.LocalDateTime;

public record Operation(OperationType operationType, Amount amount, LocalDateTime date) {

}