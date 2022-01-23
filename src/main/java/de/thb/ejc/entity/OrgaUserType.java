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
@Entity(name = "usertype_orga_user")
public class OrgaUserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "usertypeid")
    private UserType userType;

    @OneToOne
    @JoinColumn(name = "orgaid")
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

}
