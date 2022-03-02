package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.dto.RecipeIngredientRequest;
import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.repository.IngredientRepository;
import finalcountdown.homerecipesystembackend.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void addIngredientToRecipe(RecipeIngredientRequest recipeIngredientRequest) {
        try {
            var ingredientObject = ingredientRepository.getById(recipeIngredientRequest.getRecipeId());
            var recipeObject = recipeRepository.getById(recipeIngredientRequest.getIngredientId());
            recipeObject.getRecipeIngredients().add(ingredientObject);
            recipeRepository.save(recipeObject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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

    @Transactional
    public boolean deleteRecipeById(Long id) {
        log.info("Trying to delete recipe entity by id: [{}]", id);

        boolean result = false;
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            result = true;
        }
        return result;
    }
}
