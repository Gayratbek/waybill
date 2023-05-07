package own.clean.buswaybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import own.clean.buswaybill.entity.Route;
import own.clean.buswaybill.model.RouteModel;
import own.clean.buswaybill.service.CrudService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/route")
public class RouteController {
    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<Route>> getAllrouteRecords(){

        return ResponseEntity.ok(crudService.findRoutes());
    }

    @GetMapping(value = "{routeId}")
    public ResponseEntity<?> getrouteRecordById(@PathVariable(value = "routeId") Long routeId){
        Optional<Route> route = crudService.findRouteById(routeId);
        if (route.isPresent()){
            return ResponseEntity.ok(route.get());
        }
        else {
            return ResponseEntity.badRequest().body("Route topilmadi");
        }
    }
    @PostMapping
    public ResponseEntity<?> createrouteRecord(@RequestBody RouteModel routeRecord){
        Route route = crudService.saveRoute(routeRecord);
        if (route == null ) return ResponseEntity.badRequest().body("Avtobus stansiya topilmadi");
        else
        {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/route").toUriString());
            return ResponseEntity.created(uri).body(route);
        }
    }

    @PutMapping
    public ResponseEntity<?> updaterouteRecord(@RequestParam(name = "id", required = false) Long routeId, @RequestBody RouteModel routeRecord){
        if (routeRecord == null || routeId == null){
            return ResponseEntity.badRequest().body("route yoki Id null bulmaslik kerak");
        }
        Optional<Route> optionalroute = crudService.findRouteById(routeId);
        if(optionalroute.isPresent()){
            Route route = crudService.updateRoute(routeRecord, optionalroute.get());
            if (route == null ){
                return ResponseEntity.badRequest().body("Route ma'lumotlarini yangilay olmadi");
            }
            else {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/route").toUriString());
                return ResponseEntity.created(uri).body(route);
            }
        }
        else {
            return ResponseEntity.badRequest().body( "(" +  routeId + ") ID li  route topilmadi");
        }
    }
    @DeleteMapping(value = "{Id}")
    public ResponseEntity<?> deleterouteById(@PathVariable(value = "Id") Long routeRecordId) throws RuntimeException {
        if (crudService.findRouteById(routeRecordId).isEmpty()) {
            return ResponseEntity.badRequest().body("Route ma'lumotlarini yangilay olmadi");
        }
        crudService.deleteRouteById(routeRecordId);
        return ResponseEntity.ok("(" + routeRecordId + ") ID li route uchirildi");
    }





}
