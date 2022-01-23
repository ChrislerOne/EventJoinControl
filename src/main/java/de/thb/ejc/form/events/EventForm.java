package de.thb.ejc.form.events;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventForm {

    private String name;

    private int organizationid;

    private LocalDateTime eventstart;

    private LocalDateTime eventend;

}
