package tests;

import base.BaseTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

public class RandomDataTest{

    @Test
    public void testWithRandomTaskTitle() {
        Faker faker = new Faker();

        // Generate a random title using Faker
        String randomTitle = faker.book().title();  // e.g., "The Winds of Dune"

        // You could also generate other random data:
        String randomName = faker.name().fullName();
        String randomEmail = faker.internet().emailAddress();
        String randomSentence = faker.lorem().sentence();

        // Print them out to verify
        System.out.println("Random task title: " + randomTitle);
        System.out.println("Random name: " + randomName);
        System.out.println("Random email: " + randomEmail);
        System.out.println("Random comment: " + randomSentence);

        // In a real test: you'd use `randomTitle` in a form input
    }
}
