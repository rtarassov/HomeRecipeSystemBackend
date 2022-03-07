package finalcountdown.homerecipesystembackend.dto;

import lombok.Data;

@Data
public class RecipeRequest {
    private String name;
    private String note;
    private Long prepMethodId;
}
