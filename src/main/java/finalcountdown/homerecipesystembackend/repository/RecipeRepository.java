package finalcountdown.homerecipesystembackend.repository;

import finalcountdown.homerecipesystembackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
