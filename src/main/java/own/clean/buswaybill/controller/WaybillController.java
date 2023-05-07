package own.clean.buswaybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import own.clean.buswaybill.entity.Waybill;
import own.clean.buswaybill.model.WayBillModel;
import own.clean.buswaybill.service.CrudService;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/waybill")
public class WaybillController {
    @Autowired
    private CrudService crudService;

    @GetMapping
    public ResponseEntity<List<Waybill>> getAllbillRecords(){
        return ResponseEntity.ok(crudService.findBills());
    }

    @GetMapping(value = "{billId}")
    public ResponseEntity<?> getbillRecordById(@PathVariable(value = "billId") Long billId){
        Optional<Waybill> bill = crudService.findBillById(billId);
        if (bill.isPresent()){
            return ResponseEntity.ok(bill.get());
        }
        else {
            return ResponseEntity.badRequest().body("Avtobill stansiya topilmadi");
        }
    }
    @PostMapping
    public ResponseEntity<?> createBillRecord(@RequestBody WayBillModel billRecord){
        Waybill bill = null;
        try {
            bill = crudService.saveBill(billRecord);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (bill == null ) return ResponseEntity.badRequest().body("Shofyor, Avtobus yoki Route  topilmadi");
        else
        {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/bill").toUriString());
            return ResponseEntity.created(uri).body(bill);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBillRecord(@RequestParam(name = "id") Long billId, @RequestBody WayBillModel billRecord){
        if (billRecord == null || billId == null){
            return ResponseEntity.badRequest().body("bill record yoki Id null bulmaslik kerak");
        }
        Optional<Waybill> optionalbill = crudService.findBillById(billId);
        if(optionalbill.isPresent()){
            Waybill bill = null;
            try {
                bill = crudService.updateBill(billRecord, optionalbill.get());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (bill == null ){
                return ResponseEntity.badRequest().body("Way bill ni yangilay olmadi");
            }
            else {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/bill").toUriString());
                return ResponseEntity.created(uri).body(bill);
            }
        }
        else {
            return ResponseEntity.badRequest().body( "(" +  billId + ") ID li  Waybill topilmadi");
        }
    }
    @DeleteMapping(value = "{Id}")
    public ResponseEntity<?> deleteBillById(@PathVariable(value = "Id") Long billRecordId) throws RuntimeException {
        if (crudService.findBillById(billRecordId).isEmpty()) {
            return ResponseEntity.badRequest().body("WayBillni topa olmadi olmadi");
        }
        crudService.deleteBillById(billRecordId);
        return ResponseEntity.ok("(" + billRecordId + ") ID li Waybill uchirildi");
    }

}
