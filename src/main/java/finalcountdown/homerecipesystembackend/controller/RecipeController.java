package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.dto.RecipeRequest;
import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequest newRecipe) {
        log.info("New recipe to save [{}]", newRecipe);
        var id = recipeService.saveRecipe(newRecipe);
        return ResponseEntity.created(URI.create("/recipe/create/%d"
                .formatted(id)))
                .body(newRecipe);
    }

    @GetMapping("/read-all")
    public List<Recipe> findAllRecipes() {
        log.info("findAllRecipes() was called from controller");
        return recipeService.readAllRecipes();
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Recipe> findRecipeById(@PathVariable("id") Long recipeId) {
        log.info("trying to find ingredient entity by id: [{}]", recipeId);
        var recipe = recipeService.readRecipeById(recipeId);
        return recipe.map(recipe1 -> ResponseEntity.ok(recipe1))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable("id") Long id) {
        log.info("trying to delete recipe by id: [{}]", id);
        boolean deleted = recipeService.deleteRecipeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        log.info("updateRecipeById() called from controller");
        boolean updated = recipeService.updateRecipe(id, recipe);
        if (updated) {
            return ResponseEntity
                    .created(URI.create("/recipe/create/%d"
                    .formatted(recipe.getId())))
                    .body(recipe);
        }
        return ResponseEntity.notFound().build();
    }
}
