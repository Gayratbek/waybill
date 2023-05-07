package own.clean.buswaybill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import own.clean.buswaybill.entity.Route;

@Repository
public interface RouteRepos extends JpaRepository<Route, Long> {
}
