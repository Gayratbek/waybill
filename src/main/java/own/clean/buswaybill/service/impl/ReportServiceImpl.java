package own.clean.buswaybill.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import own.clean.buswaybill.entity.Bus;
import own.clean.buswaybill.entity.BusPlace;
import own.clean.buswaybill.entity.Driver;
import own.clean.buswaybill.entity.Waybill;
import own.clean.buswaybill.repos.WaybillRepos;
import own.clean.buswaybill.service.ReportService;

import jakarta.transaction.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final WaybillRepos waybillRepos;

    @Override
    public HashMap<String, Integer> requestreportByBus(String fromDate, String toDate) throws ParseException {
        HashMap<String, Integer> bybus = new HashMap<>();
        List<Waybill> waybillList = findWaybillList(fromDate, toDate);
        for (Iterator<Waybill> it = waybillList.iterator(); it.hasNext(); ) {
            Bus bus = it.next().getBus();
            putRecordsintoHash(bybus, bus.getStateNumber());
        }
        return bybus;
    }

    @Override
    public HashMap<String, Integer> requestreportByDriver(String fromDate, String toDate) throws ParseException {
        List<Waybill> waybillList = findWaybillList(fromDate, toDate);
        HashMap<String, Integer> byDriver = new HashMap<>();
        for (Iterator<Waybill> it = waybillList.iterator(); it.hasNext(); ) {
            Driver driver = it.next().getDriver();
            putRecordsintoHash(byDriver, driver.getFio());
        }
        return byDriver;
    }

    @Override
    public HashMap<String, Integer> requestreportByBusPlace(String fromDate, String toDate) throws ParseException {
        HashMap<String, Integer> byBusPlace = new HashMap<>();
        List<Waybill> waybillList = findWaybillList(fromDate, toDate);
        for (Iterator<Waybill> it = waybillList.iterator(); it.hasNext();) {
            BusPlace busPlace = it.next().getBus().getBusPlace();
            putRecordsintoHash(byBusPlace, busPlace.getName());
        }
        return byBusPlace;
    }

    private List<Waybill> findWaybillList(String fromDate, String toDate) throws ParseException {
        List<Waybill> waybillList;
        if (fromDate == null && toDate == null){
            waybillList = waybillRepos.findAll();
        }
        else {
            LocalDateTime start, end;
            if (toDate == null){
                start = convertionLocalDatTime(fromDate, 0, 0, 0);
                end = convertionLocalDatTime(fromDate, 23, 59, 59);
            } else if (fromDate == null) {
                start = convertionLocalDatTime(toDate, 0, 0, 0);
                end = convertionLocalDatTime(toDate, 23, 59, 59);
            }
            else{
                start = convertionLocalDatTime(fromDate, 0, 0, 0);
                end = convertionLocalDatTime(toDate, 23, 59, 59);
            }
            waybillList = waybillRepos.findAllBetweenDates(start,end);
        }
        return waybillList;
    }

    private void putRecordsintoHash(HashMap<String, Integer> byBusPlace, String keyvalue) {
        if (byBusPlace.containsKey(keyvalue)){
            byBusPlace.put(keyvalue, byBusPlace.get(keyvalue).intValue() + 1);
        }
        else byBusPlace.put(keyvalue, 1);
    }

    private LocalDateTime convertionLocalDatTime(String date, int hour, int minute, int second) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        LocalDateTime localDateTime = Instant.ofEpochMilli(date1.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime.of(LocalDate.from(localDateTime), LocalTime.of(hour, minute, second));
    }

}
