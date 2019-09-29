package pl.edu.kopalniakodu.todoapp.service.utill;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomURLGeneratorImplTest {

    @Test
    public void generateRandomUrl() {

        int minASCI = 97;
        int maxASCINumber = 122;
        int targetStringLength = 10;


        int numberOfGeneratedUrls = 1000;
        Set<String> generatedRandomUrls = new HashSet<>();

        for (int i = 0; i < numberOfGeneratedUrls; i++) {
            String generatedUrl = RandomURLGeneratorImpl.generateRandomUrl();
            generatedRandomUrls.add(generatedUrl);

            for (int k = 0; k < generatedUrl.length(); k++) {
                char c = generatedUrl.charAt(k);
                if ((int) c < minASCI || (int) c > maxASCINumber) {
                    Assertions.fail("Number has bad ascii range");
                }
            }
        }

        assertThat(generatedRandomUrls.size()).isEqualTo(numberOfGeneratedUrls);
        assertThat(RandomURLGeneratorImpl.generateRandomUrl().length()).isEqualTo(targetStringLength);


    }


}