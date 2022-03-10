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
        return recipe.map(ResponseEntity::ok)
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
    public ResponseEntity<?> updateRecipeById(@PathVariable("id") Long id,
                                              @RequestBody RecipeRequest recipe) {
        log.info("updateRecipeById() called from controller");
        var recipeId = recipeService.updateRecipe(id, recipe);

            return ResponseEntity
                    .created(URI.create("/recipe/update/%d"
                    .formatted(recipeId)))
                    .body(recipe);
    }
}
