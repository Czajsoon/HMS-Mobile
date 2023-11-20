package hotel.management.system.be.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Room entity.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column
    private Integer level;

    @Column
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @Column
    private BigDecimal pricePerNight;

    @Column
    private Boolean balconyAvailable;

    @Column
    private Integer bedsNumber;

    @Column
    private String description;

    @Column
    private String comments;

}
