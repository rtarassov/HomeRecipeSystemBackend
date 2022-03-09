package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.dto.IngredientRequest;
import finalcountdown.homerecipesystembackend.dto.RecipeIngredientRequest;
import finalcountdown.homerecipesystembackend.model.Ingredient;
import finalcountdown.homerecipesystembackend.service.IngredientService;
import finalcountdown.homerecipesystembackend.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200/") //TODO: Remove this line when I don't host on my own computer anymore.
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createIngredient(@RequestBody IngredientRequest newIngredient) {
        log.info("New ingredient to save: [{}]", newIngredient);
        var id = ingredientService.saveIngredient(newIngredient);
        return ResponseEntity.created(URI.create("/ingredient/create/%d"
                        .formatted(id)))
                .body(newIngredient);
    }

    @GetMapping("/read-all")
    public List<Ingredient> findAllIngredients() {
        log.info("findAllIngredients() was called from controller");
        return ingredientService.readAllIngredients();
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Ingredient> findIngredientById(@PathVariable("id") Long ingredientId) {
        log.info("trying to find ingredient entity by id: [{}]", ingredientId);
        var ingredient = ingredientService.readIngredientById(ingredientId);
        return ingredient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // localhost:8080/ingredient/delete-by-id/1
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable("id") Long id) {
        log.info("trying to delete ingredient by id: [{}]", id);
        boolean deleted = ingredientService.deleteIngredientById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ingredient> updateIngredientById(@PathVariable("id") Long id,
                                                           @RequestBody Ingredient ingredient) {
        log.info("updateIngredientById() called from controller");
        boolean updated = ingredientService.updateIngredient(id, ingredient);
        if (updated) {
            return ResponseEntity.created(URI.create("/ingredient/create/%d"
                            .formatted(ingredient.getId())))
                    .body(ingredient);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add-ingredient-to-recipe")
    public ResponseEntity<Void> addIngredientToRecipe(@RequestBody RecipeIngredientRequest recipeIngredientRequest) {
        recipeService.addIngredientToRecipe(recipeIngredientRequest);
        return ResponseEntity.ok().build();
    }
}
