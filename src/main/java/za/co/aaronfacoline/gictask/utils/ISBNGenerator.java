package za.co.aaronfacoline.gictask.utils;

import org.springframework.stereotype.Component;

@Component
public class ISBNGenerator {

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

}
