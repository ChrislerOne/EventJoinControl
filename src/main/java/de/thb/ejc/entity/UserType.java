package de.thb.ejc.entity;

import javax.persistence.*;

@Table(name = "userTypes")
@Entity
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    //TODO Vervollständigen


    // TODO Entfernen
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}