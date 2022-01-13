package de.thb.ejc.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "event_state")
public class EventState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "eventId", nullable = false)
    private Event eventId;

    @ManyToOne
    @JoinColumn(name = "stateId")
    private State stateId;

}
