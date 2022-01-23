package de.thb.ejc.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "events")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizationid", nullable = false)
    private Organization organizationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stateid", nullable = false)
    private State stateId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "eventstart", nullable = false)
    private LocalDateTime eventStart;

    @Column(name = "eventend", nullable = true)
    private LocalDateTime eventEnd;

}
