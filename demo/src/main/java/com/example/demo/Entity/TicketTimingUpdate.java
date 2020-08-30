package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TicketTimingUpdate {

    @NotNull
    @JsonFormat(shape =JsonFormat.Shape.STRING ,pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }
}
