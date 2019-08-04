package bookstore.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String street;
    @NotNull
    private String postcode;
    @NotNull
    private String city;

    public Address(String street, String postcode, String city) {
        this.street = street;
        this.postcode = postcode;
        this.city = city;
    }

}
