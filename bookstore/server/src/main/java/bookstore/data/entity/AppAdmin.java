package bookstore.data.entity;

import bookstore.data.entity.util.AppUser;
import bookstore.data.entity.util.Name;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class AppAdmin {

    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private Name name;
    @Embedded
    private AppUser user;

    public AppAdmin(Name name, AppUser user){
        this.name = name;
        this.user = user;
    }
}
