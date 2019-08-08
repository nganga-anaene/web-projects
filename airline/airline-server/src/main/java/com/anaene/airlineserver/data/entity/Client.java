package com.anaene.airlineserver.data.entity;

import com.anaene.airlineserver.data.entity.util.Name;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue
    private long id;
    private Name name;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToOne
    private Passenger passenger;

    public Client(Name name,String username, String password, Passenger passenger) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.passenger = passenger;
    }
}
