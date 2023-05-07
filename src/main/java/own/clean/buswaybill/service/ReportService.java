package own.clean.buswaybill.service;


import java.text.ParseException;
import java.util.*;

public interface ReportService {
    HashMap<String, Integer> requestreportByBus(String fromDate, String toDate) throws ParseException;
    HashMap<String, Integer> requestreportByDriver(String fromDate, String toDate) throws ParseException;

    HashMap<String, Integer> requestreportByBusPlace(String fromDate, String toDate) throws ParseException;
}
