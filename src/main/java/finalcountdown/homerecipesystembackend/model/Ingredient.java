package finalcountdown.homerecipesystembackend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientType ingredientType;
}
