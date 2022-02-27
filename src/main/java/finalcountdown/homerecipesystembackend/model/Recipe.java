package finalcountdown.homerecipesystembackend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany
    private List<RecipeIngredient> recipeIngredients;

    @OneToOne(cascade = CascadeType.ALL)
    private PrepMethod prepMethod;

    private String note;
}
