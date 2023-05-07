package own.clean.buswaybill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import own.clean.buswaybill.entity.Waybill;


import java.time.LocalDateTime;
import java.util.List;

public interface WaybillRepos extends JpaRepository<Waybill, Long> {


//    List<Waybill> findByWaybillDateBetween(LocalDateTime start, LocalDateTime end);
    @Query(value = "SELECT * FROM waybill WHERE waybill.public.waybill.waybill_date >= :startDate AND waybill.public.waybill.waybill_date <= :endDate", nativeQuery = true)
    List<Waybill> findAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
