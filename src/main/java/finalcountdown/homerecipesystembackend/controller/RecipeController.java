package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.model.Recipe;
import finalcountdown.homerecipesystembackend.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody Recipe newRecipe) {
        log.info("New recipe to save", newRecipe);
        recipeService.saveRecipe(newRecipe);
        return ResponseEntity.created(URI.create("/recipe/create/%d"
                .formatted(newRecipe.getId())))
                .body(newRecipe);
    }
}
