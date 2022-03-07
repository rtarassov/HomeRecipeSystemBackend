package finalcountdown.homerecipesystembackend.repository;

import finalcountdown.homerecipesystembackend.model.PrepMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrepMethodRepository extends JpaRepository<PrepMethod, Long> {
}
