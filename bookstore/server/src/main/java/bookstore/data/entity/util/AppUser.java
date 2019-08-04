package bookstore.data.entity.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Column(unique = true)
    private String username;
    private String email;
    @JsonIgnore
    private String password;
}
