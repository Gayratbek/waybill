package own.clean.buswaybill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import own.clean.buswaybill.entity.Bus;

@Repository
public interface BusRepos extends JpaRepository<Bus, Long> {
}
