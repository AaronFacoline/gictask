package za.co.aaronfacoline.gictask.utils;

import org.springframework.stereotype.Component;

@Component
public class ISBNGenerator {

    /**
     * Generates a 13-digit ISBN using the provided a Long as the basis.
     * The method calculates the initial 12 digits based on the provided value
     * and appends a checksum digit calculated using the ISBN-13 standard.
     *
     * @param value the numerical value used to generate the initial 12 digits of the ISBN
     * @return a string representing the complete 13-digit ISBN
     */
    public String generateISBN(Long value){
        String numberFormat = "%012d";
        String ISBN12 = String.format(numberFormat, value);
        char[] charArray = ISBN12.toCharArray();
        int sum = 0;
        for (int i = 0; i < charArray.length; i++) {
            if(i%2 == 0){
                sum += (charArray[i] - '0');
            }
            else {
                sum += ((charArray[i] - '0')  * 3);
            }
        }
        return ISBN12 + ((10 - (sum % 10)) % 10);

    }

    /**
     * Validates whether a given ISBN-13 string adheres to the ISBN-13 standard.
     * The method checks the length of the input and verifies the checksum digit.
     *
     * @param isbn the 13-character string representing the ISBN to be validated
     * @return true if the ISBN is valid according to the ISBN-13 standard, false otherwise
     */
    public boolean validateISBN(String isbn){
        char[] charArray = isbn.toCharArray();
        if(charArray.length != 13){
            return false;
        }
        int sum = 0;
        for (int i = 0; i < charArray.length - 1; i++) {
            if(i%2 == 0){
                sum += (charArray[i] - '0');
            }
            else {
                sum += ((charArray[i] - '0')  * 3);
            }
        }
        int caluclatedCheckDigit = (10 - (sum % 10)) % 10;
        int validationDigit = charArray[12] - '0';

        return caluclatedCheckDigit == validationDigit;
    }

}
