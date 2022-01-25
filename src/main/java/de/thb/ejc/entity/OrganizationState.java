package de.thb.ejc.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "organization_state")
public class OrganizationState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizationid", nullable = false)
    private Organization organizationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stateid", nullable = false)
    private State stateId;
}
