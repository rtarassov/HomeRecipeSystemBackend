package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.RecipeIngredient;
import finalcountdown.homerecipesystembackend.repository.RecipeIngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecipeIngredientService {
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public void saveRecipeIngredient(RecipeIngredient entity) {
        log.info("recipeIngredient to save: [{}] ", entity);
        recipeIngredientRepository.save(entity);
    }

    @Transactional
    public void updateRecipeIngredient(Long id, RecipeIngredient entity) {
        log.info("Updating ingredient: [{}]", entity);
        var recipeIngredient = recipeIngredientRepository.findById(id);
        if (recipeIngredient.isPresent()) {
            entity.setId(recipeIngredient.get().getId());
            recipeIngredientRepository.save(entity);
        }
    }

    public List<RecipeIngredient> readAllRecipeIngredients() {
        log.info("Trying to read all recipeIngredients in database");

        var result = recipeIngredientRepository.findAll();
        log.info("RecipeIngredients found from DB: [{}]", result);
        return result;
    }

    @Transactional
    public boolean deleteRecipeIngredientById(Long id) {
        log.info("Trying to delete recipeIngredient by id: [{}]", id);

        boolean result = false;
        if (recipeIngredientRepository.existsById(id)) {
            recipeIngredientRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    public Optional<RecipeIngredient> readRecipeIngredientById(Long id) {
        log.info("Trying to read recipeIngredient by id: [{}]", id);

        var maybeRecipeIngredient = recipeIngredientRepository.findById(id);

        log.info("Found recipeIngredient: [{}]", maybeRecipeIngredient);
        return maybeRecipeIngredient;
    }

}
