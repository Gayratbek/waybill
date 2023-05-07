package own.clean.buswaybill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import own.clean.buswaybill.entity.Driver;
import own.clean.buswaybill.model.DriverModel;
import own.clean.buswaybill.service.CrudService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/driver")
public class DriverController {
    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<Driver>> getAlldriverRecords(){

        return ResponseEntity.ok(crudService.findDrivers());
    }

    @GetMapping(value = "{driverId}")
    public ResponseEntity<?> getdriverRecordById(@PathVariable(value = "driverId") Long driverId){
        Optional<Driver> driver = crudService.findDriverById(driverId);
        if (driver.isPresent()){
            return ResponseEntity.ok(driver.get());
        }
        else {
            return ResponseEntity.badRequest().body("Shofyor topilmadi");
        }
    }
    @PostMapping
    public ResponseEntity<?> createDriverRecord(@RequestBody DriverModel driverRecord){

        Driver driver = crudService.saveDriver(driverRecord);
        if (driver == null ) return ResponseEntity.badRequest().body("Shofyor topilmadi");
        else
        {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/driver").toUriString());
            return ResponseEntity.created(uri).body(driver);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDriverRecord(@RequestParam(name = "id") Long driverId, @RequestBody DriverModel driverRecord){
        if (driverRecord == null || driverId == null){
            return ResponseEntity.badRequest().body("Driver yoki Id null bulmaslik kerak");
        }
        Optional<Driver> optionaldriver = crudService.findDriverById(driverId);
        if(optionaldriver.isPresent()){
            Driver driver = crudService.updateDriver(driverRecord, optionaldriver.get());
            if (driver == null ){
                return ResponseEntity.badRequest().body("Shofyor ma'lumotlarini yangilay olmadi");
            }
            else {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/driver").toUriString());
                return ResponseEntity.created(uri).body(driver);
            }
        }
        else {
            return ResponseEntity.badRequest().body( "(" +  driverId + ") ID li  shofyor topilmadi");
        }
    }
    @DeleteMapping(value = "{Id}")
    public ResponseEntity<?> deleteDriverById(@PathVariable(value = "Id") Long driverRecordId) throws RuntimeException {
        if (crudService.findDriverById(driverRecordId).isEmpty()) {
            return ResponseEntity.badRequest().body("Shofyor ma'lumotlarini yangilay olmadi");
        }
        crudService.deleteDriverById(driverRecordId);
        return ResponseEntity.ok("(" + driverRecordId + ") ID li shofyor uchirildi");
    }





}
