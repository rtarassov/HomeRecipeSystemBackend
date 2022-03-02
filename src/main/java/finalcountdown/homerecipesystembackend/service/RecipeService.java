package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipe(Recipe recipeEntity) {
        log.info("recipe entity for saving", recipeEntity);
        recipeRepository.save(recipeEntity);
    }

    public List<Recipe> readAllRecipes() {
        log.info("Trying to read all recipes in database");
        var result = recipeRepository.findAll();
        log.info("Recipes saved from DB: " + result);
        return result;
    }

    public Optional<Recipe> readRecipeById(Long id) {
        log.info("trying to read recipe by id: [{}]", id);
        var maybeRecipe = recipeRepository.findById(id);
        log.info("found Ingredient: [{}]", maybeRecipe);
        return maybeRecipe;
    }
}
