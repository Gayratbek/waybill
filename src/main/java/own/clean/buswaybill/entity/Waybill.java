package own.clean.buswaybill.entity;


import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//yo'l varaqasi
public class Waybill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String waybillNumber;

    @Column(nullable = false )
    private Instant waybillDate;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Waybill waybill = (Waybill) o;
        return id != null && Objects.equals(id, waybill.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
