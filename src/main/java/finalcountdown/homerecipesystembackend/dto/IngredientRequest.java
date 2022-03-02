package finalcountdown.homerecipesystembackend.dto;

import lombok.Data;

@Data
public class IngredientRequest {
    private String ingredientType;
    private String name;
    private Float quantity;
    private Long unitId;
}
