package own.clean.buswaybill.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class BusModel {

    private String stateNumber;
    private Long busPlace;

    @Override
    public String toString() {
        return "BusModel{" +
                "stateNumber='" + stateNumber + '\'' +
                ", busPlace=" + busPlace +
                '}';
    }
}
