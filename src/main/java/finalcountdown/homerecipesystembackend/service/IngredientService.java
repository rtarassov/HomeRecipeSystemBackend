package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.Ingredient;
import finalcountdown.homerecipesystembackend.model.IngredientType;
import finalcountdown.homerecipesystembackend.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // @Transactional
    public void saveIngredient(Ingredient entity) {
        log.info("entity for saving", entity);
        ingredientRepository.save(entity);
    }

    public void updateIngredient(Long id, Ingredient entity) {
        log.info("updating ingredient", entity);
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent()) {
            entity.setId(ingredient.get().getId());
            ingredientRepository.save(entity);
        }
    }

    public List<Ingredient> readAllIngredients() {
        log.info("Trying to read all ingredients in database");

        var result = ingredientRepository.findAll();
        log.info("Ingredients saved from DB: " + result);
        return result;
    }


    @Transactional
    public boolean deleteIngredientById(Long id) {
        log.info("Trying to delete entity by id: [{}]", id);

        boolean result = false;
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    public Optional<Ingredient> readIngredientById(Long id) {
        log.info("trying to read ingredient by id: [{}]", id);

        var maybeIngredient = ingredientRepository.findById(id);

        log.info("found Ingredient: [{}]", maybeIngredient);
        return maybeIngredient;
    }
}
