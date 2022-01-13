package de.thb.ejc.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "stateid")
    private de.thb.ejc.entity.State state;

    @OneToOne
    @JoinColumn(name = "userTypeid")
    private de.thb.ejc.entity.UserType userType;

    @Column(name = "uid")
    private String uid;

    @Column(name = "email")
    private String email;

}
