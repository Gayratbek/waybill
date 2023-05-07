package own.clean.buswaybill.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import own.clean.buswaybill.entity.*;
import own.clean.buswaybill.model.*;
import own.clean.buswaybill.repos.*;

import own.clean.buswaybill.service.CrudService;

import jakarta.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CrudServiceImpl implements CrudService {

    private final BusPlaceRepos busPlaceRepos;
    private final BusRepos busRepos;
    private final DriverRepos  driverRepos;
    private final RouteRepos routeRepos;
    private final WaybillRepos waybillRepos;

    //    CRUD for Bus place table
    @Override
    public List<BusPlace> findBusPlaceAll() {

        return busPlaceRepos.findAll();
    }

    @Override
    public BusPlace saveBusPlace(BusPlaceModel busPlaceModel) {
        BusPlace busPlace = new BusPlace();
        busPlace.setName(busPlaceModel.getName());
        return busPlaceRepos.save(busPlace);
    }

    @Override
    public BusPlace updateBusPlace(BusPlaceModel busPlace, BusPlace existingBusPlace) {
        existingBusPlace.setName(busPlace.getName());
        return busPlaceRepos.save(existingBusPlace);
    }

    @Override
    public Optional<BusPlace> findBusPlaceById(Long busPlaceId) {
        return busPlaceRepos.findById(busPlaceId);
    }

    @Override
    public void deleteBusPlaceById(Long busPlaceId) {

        busPlaceRepos.deleteById(busPlaceId);
    }

    //    CRUD for bus table
    @Override
    public List<Bus> findAll() {
        return busRepos.findAll();
    }
    @Override
    public Optional<Bus> findById(Long busId) {
        return busRepos.findById(busId);
    }
    @Transactional
    @Override
    public Bus save(BusModel busRecord) {
        Optional<BusPlace> busPlace = busPlaceRepos.findById(busRecord.getBusPlace());
        if (busPlace.isPresent()){
            Bus newBus= Bus.builder()
                    .stateNumber(busRecord.getStateNumber())
                    .busPlace(busPlace.get())
                    .build();
            busRepos.save(newBus);
            return newBus;
        }
        return null;

    }
    @Override
    public Bus update(BusModel busRecord,Bus existingBusRecord) {
        if (busRecord.getStateNumber()!= null ){
            existingBusRecord.setStateNumber(busRecord.getStateNumber());
        }
        if (busRecord.getBusPlace()!= null ){
            Optional<BusPlace> busPlace = busPlaceRepos.findById(busRecord.getBusPlace());
            if (busPlace.isPresent()){
                existingBusRecord.setBusPlace(busPlace.get());
            }
        }
        return busRepos.save(existingBusRecord);
    }
    @Override
    public void deleteById(Long busRecordId) {
        busRepos.deleteById(busRecordId);
    }

//    CRUD for driver table
    @Override
    public List<Driver> findDrivers() {
        return driverRepos.findAll();
    }
    @Override
    public Optional<Driver> findDriverById(Long driverId) {
        return driverRepos.findById(driverId);
    }

    @Override
    public Driver saveDriver(DriverModel driverRecord) {
        Optional<BusPlace> busPlace = busPlaceRepos.findById(driverRecord.getBusPlace());
        if (busPlace.isPresent()){
            Driver newDriver= Driver.builder()
                    .fio(driverRecord.getFio())
                    .phoneNumber(driverRecord.getPhoneNumber())
                    .busPlace(busPlace.get())
                    .build();
            return driverRepos.save(newDriver);
        }
        return null;

    }

    @Override
    public Driver updateDriver(DriverModel driverRecord, Driver existingdriver) {
        if (driverRecord.getFio() != null ){
            existingdriver.setFio(driverRecord.getFio());
        }
        if (driverRecord.getPhoneNumber() != null ){
            existingdriver.setPhoneNumber(driverRecord.getPhoneNumber());
        }

        if (driverRecord.getBusPlace()!= null ){
            Optional<BusPlace> busPlace = busPlaceRepos.findById(driverRecord.getBusPlace());
            if (busPlace.isPresent()){
                existingdriver.setBusPlace(busPlace.get());
            }
        }
        return driverRepos.save(existingdriver);
    }

    @Override
    public void deleteDriverById(Long driverRecordId) {
        driverRepos.deleteById(driverRecordId);
    }

 //    CRUD for Routes table
    @Override
    public List<Route> findRoutes() {
        return routeRepos.findAll();
    }
    @Override
    public Optional<Route> findRouteById(Long routeId) {
        return routeRepos.findById(routeId);
    }
    @Override
    public Route saveRoute(RouteModel routeRecord) {
        Route route = Route.builder()
                .routeName(routeRecord.getRouteName())
                .description(routeRecord.getDescription())
                .build();
        return routeRepos.save(route);
    }
    @Override
    public Route updateRoute(RouteModel routeRecord, Route existingRoute) {
        if (routeRecord.getDescription() != null ){
            existingRoute.setDescription(routeRecord.getDescription());
        }
        if (routeRecord.getRouteName() != null ){
            existingRoute.setRouteName(routeRecord.getRouteName());
        }
        return routeRepos.save(existingRoute);
    }
    @Override
    public void deleteRouteById(Long routeRecordId) {
        routeRepos.deleteById(routeRecordId);
    }

    //    CRUD for Waybill table
    @Override
    public List<Waybill> findBills() {
        return waybillRepos.findAll();
    }

    @Override
    public Optional<Waybill> findBillById(Long billId) {
        return waybillRepos.findById(billId);
    }

    @Override
    public Waybill saveBill(WayBillModel billRecord) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(billRecord.getWaybillDate());
        Optional<Bus> bus = busRepos.findById(billRecord.getBus());
        Optional<Driver> driver = driverRepos.findById(billRecord.getDriver());
        Optional<Route> route = routeRepos.findById(billRecord.getRoute());

        if (bus.isPresent() && driver.isPresent() && route.isPresent()){
            Waybill waybill = Waybill.builder()
                    .waybillNumber(billRecord.getWaybillNumber())
                    .waybillDate(date.toInstant())
                    .bus(bus.get())
                    .driver(driver.get())
                    .route(route.get())
                    .build();
            return waybillRepos.save(waybill);
        }
        else return null;
    }
    @Override
    public Waybill updateBill(WayBillModel billRecord, Waybill existingbill) throws ParseException {
        existingbill.setWaybillNumber(billRecord.getWaybillNumber());
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(billRecord.getWaybillDate());
        existingbill.setWaybillDate(date.toInstant());
        Optional<Bus> bus = busRepos.findById(billRecord.getBus());
        Optional<Driver> driver = driverRepos.findById(billRecord.getDriver());
        Optional<Route> route = routeRepos.findById(billRecord.getRoute());
        if (bus.isPresent() && driver.isPresent() && route.isPresent()){
            existingbill.setBus(bus.get());
            existingbill.setDriver(driver.get());
            existingbill.setRoute(route.get());
            return waybillRepos.save(existingbill);
        }
        else  return null;
    }

    @Override
    public void deleteBillById(Long billRecordId) {

        waybillRepos.deleteById(billRecordId);
    }


}
