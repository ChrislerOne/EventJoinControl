package de.thb.ejc.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "user_events")
public class UserEvent {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "eventId", nullable = false)
    private Event eventId;

}
