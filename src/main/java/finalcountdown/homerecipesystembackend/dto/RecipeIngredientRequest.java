package finalcountdown.homerecipesystembackend.dto;

import lombok.Data;

@Data
public class RecipeIngredientRequest {
    private Long ingredientId;
    private Long recipeId;
}
