package own.clean.buswaybill.service;

import own.clean.buswaybill.entity.*;
import own.clean.buswaybill.model.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CrudService {

    //    CRUD for bus table
    List<Bus> findAll();
    Optional<Bus> findById(Long busId);
    Bus save(BusModel busRecord);
    Bus update(BusModel busRecord, Bus existingBusRecord);
    void deleteById(Long busRecordId);

    //    CRUD for bus station table
    List<BusPlace> findBusPlaceAll();
    BusPlace saveBusPlace(BusPlaceModel busPlace);
    BusPlace updateBusPlace(BusPlaceModel busPlace, BusPlace existingBusPlace);
    Optional<BusPlace> findBusPlaceById(Long busPlaceId);
    void deleteBusPlaceById(Long busPlaceId);

    //    CRUD for driver table
    List<Driver> findDrivers();
    Optional<Driver> findDriverById(Long driverId);
    Driver saveDriver(DriverModel driverRecord);
    Driver updateDriver(DriverModel driverRecord, Driver driver);
    void deleteDriverById(Long driverRecordId);

    //    CRUD for route table
    List<Route> findRoutes();
    Optional<Route> findRouteById(Long routeId);
    Route saveRoute(RouteModel routeRecord);
    Route updateRoute(RouteModel routeRecord, Route route);
    void deleteRouteById(Long routeRecordId);

    //    CRUD for waybill table
    List<Waybill> findBills();
    Optional<Waybill> findBillById(Long billId);
    Waybill saveBill(WayBillModel billRecord) throws ParseException;
    Waybill updateBill(WayBillModel billRecord, Waybill waybill) throws ParseException;
    void deleteBillById(Long billRecordId);
}
