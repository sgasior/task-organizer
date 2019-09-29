package pl.edu.kopalniakodu.todoapp.service.utill;

import java.util.Random;

public class RandomURLGeneratorImpl {


    public static String generateRandomUrl() {
        int minASCI = 97;
        int maxASCINumber = 122;
        int targetStringLength = 10;


        StringBuilder stringBuilder = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            stringBuilder.append((char) randomInRange(minASCI, maxASCINumber));
        }
        return stringBuilder.toString();
    }

    /**
     * @param min include
     * @param max exclude
     * @return random number in range min and max
     */
    private static int randomInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
