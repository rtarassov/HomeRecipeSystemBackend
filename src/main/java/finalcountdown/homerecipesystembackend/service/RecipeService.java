package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.dto.RecipeIngredientRequest;
import finalcountdown.homerecipesystembackend.dto.RecipeRequest;
import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.repository.IngredientRepository;
import finalcountdown.homerecipesystembackend.repository.PrepMethodRepository;
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
    private final PrepMethodRepository prepMethodRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, PrepMethodRepository prepMethodRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.prepMethodRepository = prepMethodRepository;
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

    @Transactional
    public Long saveRecipe(RecipeRequest recipeEntity) {
        log.info("recipe entity for saving [{}]", recipeEntity);

        try {
            var prepMethodObject = prepMethodRepository.getById(recipeEntity.getPrepMethodId());
            var recipeObject = new Recipe();
            recipeObject.setName(recipeEntity.getName());
            recipeObject.setNote(recipeEntity.getNote());
            recipeObject.setPrepMethod(prepMethodObject);
            recipeRepository.save(recipeObject);
            return recipeObject.getId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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

    @Transactional
    public boolean updateRecipe(Long id, Recipe entity) {
        log.info("updating recipe: [{}]", entity);
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            entity.setId(recipe.get().getId());
            recipeRepository.save(entity);
            return true;
        }
        return false;
    }
}
