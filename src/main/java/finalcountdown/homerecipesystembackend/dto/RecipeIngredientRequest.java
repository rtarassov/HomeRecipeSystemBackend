package finalcountdown.homerecipesystembackend.dto;

import lombok.Data;

@Data
public class RecipeIngredientRequest {
    private Float quantity;
    private Long ingredientId;
    private Long recipeId;
    private Long unitId;
}
