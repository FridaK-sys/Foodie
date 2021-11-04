package restapi;

import org.springframework.data.jpa.repository.JpaRepository;
import core.Recipe;

interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
