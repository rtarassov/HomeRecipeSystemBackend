package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.dto.IngredientRequest;
import finalcountdown.homerecipesystembackend.model.Ingredient;
import finalcountdown.homerecipesystembackend.model.IngredientType;
import finalcountdown.homerecipesystembackend.repository.IngredientRepository;
import finalcountdown.homerecipesystembackend.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;

    public IngredientService(IngredientRepository ingredientRepository, UnitRepository unitRepository) {
        this.ingredientRepository = ingredientRepository;
        this.unitRepository = unitRepository;
    }

    @Transactional
    public Long saveIngredient(IngredientRequest entity) {
        log.info("entity for saving: [{}]", entity);

        try {
            var unitObject = unitRepository.getById(entity.getUnitId());
            var ingredientObject = new Ingredient();
            ingredientObject.setName(entity.getName());
            ingredientObject.setQuantity(entity.getQuantity());
            ingredientObject.setIngredientType(IngredientType.valueOf(entity.getIngredientType()));
            ingredientObject.setUnit(unitObject);

            ingredientRepository.save(ingredientObject);
            return ingredientObject.getId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void updateIngredient(Long id, Ingredient entity) {
        log.info("updating ingredient: [{}]", entity);
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent()) {
            entity.setId(ingredient.get().getId());
            ingredientRepository.save(entity);
        }
    }

    public List<Ingredient> readAllIngredients() {
        log.info("Trying to read all ingredients in database");

        var result = ingredientRepository.findAll();
        log.info("Ingredients found from DB: [{}]", result);
        return result;
    }


    @Transactional
    public boolean deleteIngredientById(Long id) {
        log.info("Trying to delete ingredient by id: [{}]", id);

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
