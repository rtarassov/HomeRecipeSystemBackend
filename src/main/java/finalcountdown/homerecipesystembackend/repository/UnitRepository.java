package finalcountdown.homerecipesystembackend.repository;


import finalcountdown.homerecipesystembackend.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

}