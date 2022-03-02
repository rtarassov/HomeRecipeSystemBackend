package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.dto.RecipeIngredientRequest;
import finalcountdown.homerecipesystembackend.repository.IngredientRepository;
import finalcountdown.homerecipesystembackend.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void addIngredientToRecipe(RecipeIngredientRequest recipeIngredientRequest) {
        try {
            var ingredientObject = ingredientRepository.getById(recipeIngredientRequest.getIngredientId());
            var recipeObject = recipeRepository.getById(recipeIngredientRequest.getRecipeId());
            recipeObject.getRecipeIngredients().add(ingredientObject);
            recipeRepository.save(recipeObject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
