package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.model.RecipeIngredient;
import finalcountdown.homerecipesystembackend.service.RecipeIngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/recipe-ingredient")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipeIngredient(@RequestBody RecipeIngredient newRecipeIngredient) {
        log.info("New recipeIngredient to save: [{}]", newRecipeIngredient);
        recipeIngredientService.saveRecipeIngredient(newRecipeIngredient);

        return ResponseEntity.created(URI.create("/recipe-ingredient/create/%d"
                .formatted(newRecipeIngredient.getId())))
                .body(newRecipeIngredient);
    }

    @GetMapping("/read-all")
    public List<RecipeIngredient> findAllRecipeIngredients() {
        log.info("findAllRecipeIngredients() was called from RecipeIngredientController");
        return recipeIngredientService.readAllRecipeIngredients();
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<RecipeIngredient> findRecipeIngredientById(@PathVariable("id") Long recipeIngredientId) {
        log.info("Trying to find ingredient entity by id: [{}]", recipeIngredientId);
        var recipeIngredient = recipeIngredientService.readRecipeIngredientById(recipeIngredientId);
        return recipeIngredient.map(recipeIngredient1 -> ResponseEntity.ok(recipeIngredient1))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteRecipeIngredientById(@PathVariable("id") Long id) {
        log.info("Trying to delete recipeIngredient by id: [{}]", id);
        boolean deleted = recipeIngredientService.deleteRecipeIngredientById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RecipeIngredient> updateRecipeIngredientById(@PathVariable("id") Long id,
                                                                       @RequestBody RecipeIngredient recipeIngredient) {
        log.info("updateRecipeIngredientById() was called from RecipeIngredientController");
        recipeIngredientService.updateRecipeIngredient(id, recipeIngredient);
        return ResponseEntity.created(URI.create("/ingredient/create/%d"
                .formatted(recipeIngredient.getId())))
                .body(recipeIngredient);
    }
}
