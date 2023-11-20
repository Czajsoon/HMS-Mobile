package hotel.management.system.be.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * The type Account entity.
 */
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Boolean isUsing2FA;

    @Column
    private Boolean is2FaConfigured;

    @Column
    private String secret=null;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountRole role;

}

