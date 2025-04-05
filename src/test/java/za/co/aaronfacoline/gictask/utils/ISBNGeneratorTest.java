package za.co.aaronfacoline.gictask.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ISBNGeneratorTest {

    @Test
    public void creationTest() {
        ISBNGenerator isbnGenerator = new ISBNGenerator();
        Assertions.assertNotNull(isbnGenerator);
    }

    @Test
    public void testGenerateISBN() {
        ISBNGenerator isbnGenerator = new ISBNGenerator();
        Assertions.assertEquals("9780306406157", isbnGenerator.generateISBN(978030640615L));
    }

    @Test
    public void testGenerateISBNSimple() {
        ISBNGenerator isbnGenerator = new ISBNGenerator();
        Assertions.assertEquals("0000000000017", isbnGenerator.generateISBN(1L));
    }

}

