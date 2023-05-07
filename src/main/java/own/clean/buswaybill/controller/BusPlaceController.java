package own.clean.buswaybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import own.clean.buswaybill.entity.BusPlace;
import own.clean.buswaybill.model.BusPlaceModel;
import own.clean.buswaybill.service.CrudService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/busplace")
public class BusPlaceController {

    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<BusPlace>> getAllBusPlaces(){
        return ResponseEntity.ok(crudService.findBusPlaceAll());
    }

    @GetMapping(value = "{busPlaceId}")
    public ResponseEntity<?> getBusPlaceById(@PathVariable(value = "busPlaceId") Long busPlaceId){
        Optional<BusPlace> busPlace = crudService.findBusPlaceById(busPlaceId);
        if (busPlace.isPresent()){
            return ResponseEntity.ok(busPlace.get());
        }
        else {
            return ResponseEntity.badRequest().body("Avtobus stansiya topilmadi");
        }
    }

    @PostMapping
    public ResponseEntity<?> createBusPlace(@RequestBody BusPlaceModel busPlaceRecord){

        BusPlace busPlace = crudService.saveBusPlace(busPlaceRecord);
        if (busPlace == null ) return ResponseEntity.badRequest().body("Avtobus stansiya topilmadi");
        else
        {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/bus").toUriString());
            return ResponseEntity.created(uri).body(busPlace);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBusPlace(@RequestParam(name = "id") Long busPlaceId , @RequestBody BusPlaceModel busPlaceRecord ){

        if (busPlaceRecord == null || busPlaceId == null){
            return ResponseEntity.badRequest().body("Bus record yoki Id null bulmaslik kerak");
        }
        Optional<BusPlace> optionalBusPlace = crudService.findBusPlaceById(busPlaceId);
        if(optionalBusPlace.isPresent()){
            BusPlace busPlace = crudService.updateBusPlace(busPlaceRecord, optionalBusPlace.get());
            if (busPlace == null ){
                return ResponseEntity.badRequest().body("Avtobusni yangilay olmadi");
            }
            else {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/bus").toUriString());
                return ResponseEntity.created(uri).body(busPlace);
            }
        }
        else {
            return ResponseEntity.badRequest().body( "Shu ID li (" +  busPlaceId + ") avtobus topilmadi");
        }

    }

    @DeleteMapping(value = "{Id}")
    public ResponseEntity<?> deleteBusPlaceById(@PathVariable(value = "Id") Long busPlaceId) throws RuntimeException {
        if (crudService.findBusPlaceById(busPlaceId).isEmpty()) {
            return ResponseEntity.badRequest().body("Avtobus saroyni uchira olmadi");
        }
        crudService.deleteBusPlaceById(busPlaceId);
        return ResponseEntity.ok(" (" + busPlaceId + ") ID li Avtobus saroy uchirildi");
    }


}

