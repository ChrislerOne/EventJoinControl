package de.thb.ejc.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "usertypes")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    //TODO Vervollständigen

    private String name;

}

