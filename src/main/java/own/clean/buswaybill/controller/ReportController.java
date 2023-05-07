package own.clean.buswaybill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import own.clean.buswaybill.service.ReportService;
import java.text.ParseException;
@RestController

@RequestMapping("api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/ViewByBus")
    public ResponseEntity<?> getReportByBus(
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "toDate", required = false) String toDate) {
        try {
            return  ResponseEntity.ok(reportService.requestreportByBus(date, toDate));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/ViewByDriver")
    public ResponseEntity<?> getReportByDriver(
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "toDate", required = false) String toDate)  {
        try {
            return  ResponseEntity.ok(reportService.requestreportByDriver(date, toDate));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/ViewByBusPlace")
    public ResponseEntity<?> getReportByBusPlace(
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "toDate", required = false) String toDate
    ) {
        try {
            return  ResponseEntity.ok(reportService.requestreportByBusPlace(date, toDate));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


}
