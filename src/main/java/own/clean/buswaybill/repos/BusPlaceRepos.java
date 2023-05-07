package own.clean.buswaybill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import own.clean.buswaybill.entity.BusPlace;

import java.util.List;

public interface BusPlaceRepos extends JpaRepository<BusPlace, Long> {


}
