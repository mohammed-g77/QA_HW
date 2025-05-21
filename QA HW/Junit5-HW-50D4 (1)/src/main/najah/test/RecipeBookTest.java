package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RecipeBookTest {

    RecipeBook recipeBook;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
    }

    @Test
    @DisplayName("Should add a new recipe successfully")
    void testAddRecipe() {
        Recipe r = new Recipe();
        r.setName("Mocha");
        boolean added = recipeBook.addRecipe(r);
        assertAll(
            () -> assertTrue(added),
            () -> assertEquals("Mocha", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("Should not add a duplicate recipe")
    void testAddDuplicateRecipe() {
        Recipe r1 = new Recipe(); r1.setName("Espresso");
        Recipe r2 = new Recipe(); r2.setName("Espresso");
        assertAll(
            () -> assertTrue(recipeBook.addRecipe(r1)),
            () -> assertFalse(recipeBook.addRecipe(r2))
        );
    }

    @Test
    @DisplayName("Should delete an existing recipe and return its name")
    void testDeleteRecipe() {
        Recipe r = new Recipe(); r.setName("Latte");
        recipeBook.addRecipe(r);
        String deletedName = recipeBook.deleteRecipe(0);
        assertAll(
            () -> assertEquals("Latte", deletedName),
            () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }

    @Test
    @DisplayName("Should return null when trying to delete a non-existing recipe")
    void testDeleteInvalidRecipe() {
        assertNull(recipeBook.deleteRecipe(3));
    }

    @Test
    @DisplayName("Should edit an existing recipe and return its old name")
    void testEditRecipe() {
        Recipe r1 = new Recipe(); r1.setName("Tea");
        Recipe r2 = new Recipe(); r2.setName("Herbal Tea");
        recipeBook.addRecipe(r1);
        String oldName = recipeBook.editRecipe(0, r2);
        assertAll(
            () -> assertEquals("Tea", oldName),
            () -> assertEquals("", recipeBook.getRecipes()[0].getName()) // because newRecipe name is set to ""
        );
    }

    @Test
    @DisplayName("Should return null when trying to edit a non-existing recipe")
    void testEditInvalidRecipe() {
        Recipe r = new Recipe();
        assertNull(recipeBook.editRecipe(1, r));
    }

    @ParameterizedTest
    @CsvSource({
        "Coffee1,0", "Coffee2,1", "Coffee3,2", "Coffee4,3"
    })
    @DisplayName("Should add multiple unique recipes up to capacity")
    void testAddMultipleRecipes(String name, int index) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        assertTrue(recipeBook.addRecipe(recipe));
    }

    @Test
    @Timeout(1)
    @DisplayName("Adding a recipe should complete within 1 second")
    void testAddRecipeTimeout() {
        Recipe recipe = new Recipe();
        recipe.setName("QuickBrew");
        assertTimeout(Duration.ofSeconds(1), () -> recipeBook.addRecipe(recipe));
    }
}
