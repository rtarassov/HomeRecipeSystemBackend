package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipe(Recipe recipeEntity) {
        log.info("recipe entity for savin", recipeEntity);
        recipeRepository.save(recipeEntity);
    }
}
