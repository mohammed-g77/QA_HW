package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
 CalculatorTest.class,ProductTest.class,UserServiceTest.class,RecipeBookTest.class
})
class TestSuite {
}
