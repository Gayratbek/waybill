package own.clean.buswaybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import own.clean.buswaybill.entity.Bus;

import own.clean.buswaybill.model.BusModel;
import own.clean.buswaybill.service.CrudService;


import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bus")
public class BusController {
    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<Bus>> getAllBusRecords(){

        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping(value = "{busId}")
    public ResponseEntity<?> getBusRecordById(@PathVariable(value = "busId") Long busId){
        Optional<Bus> bus = crudService.findById(busId);
        if (bus.isPresent()){
            return ResponseEntity.ok(bus.get());
        }
        else {
            return ResponseEntity.badRequest().body("Avtobus stansiya topilmadi");
        }
    }
    @PostMapping
    public ResponseEntity<?> createBusRecord(@RequestBody BusModel busRecord){
        Bus bus = crudService.save(busRecord);
        if (bus == null ) return ResponseEntity.badRequest().body("Avtobus stansiya topilmadi");
        else
        {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/bus").toUriString());
            return ResponseEntity.created(uri).body(bus);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBusRecord(@RequestParam(name = "id") Long busId ,@RequestBody BusModel busRecord){
        if (busRecord == null || busId == null){
            return ResponseEntity.badRequest().body("Bus record yoki Id null bulmaslik kerak");
        }
        Optional<Bus> optionalBus = crudService.findById(busId);
        if(optionalBus.isPresent()){
            Bus bus = crudService.update(busRecord, optionalBus.get());
            if (bus == null ){
                return ResponseEntity.badRequest().body("Avtobusni yangilay olmadi");
            }
            else {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/bus").toUriString());
                return ResponseEntity.created(uri).body(bus);
            }
        }
        else {
            return ResponseEntity.badRequest().body( "(" +  busId + ") ID li  avtobus topilmadi");
        }
    }
    @DeleteMapping(value = "{Id}")
    public ResponseEntity<?> deleteBusById(@PathVariable(value = "Id") Long busRecordId) throws RuntimeException {
        if (crudService.findById(busRecordId).isEmpty()) {
            return ResponseEntity.badRequest().body("Avtobusni yangilay olmadi");
        }
        crudService.deleteById(busRecordId);
        return ResponseEntity.ok("(" + busRecordId + ") ID li Avtobus uchirildi");
    }





}
