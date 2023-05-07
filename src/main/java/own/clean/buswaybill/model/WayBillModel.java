package own.clean.buswaybill.model;

import lombok.Data;
import java.util.Date;

@Data
public class WayBillModel {

    private String waybillNumber;
    private String waybillDate;

    private Long bus;
    private Long driver;
    private Long route;
}
