package own.clean.buswaybill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import own.clean.buswaybill.entity.Driver;

@Repository
public interface DriverRepos extends JpaRepository<Driver, Long> {

}
