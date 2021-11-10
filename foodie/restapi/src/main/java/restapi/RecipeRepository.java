package restapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import core.Recipe;

@EnableJpaRepositories
interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
