package ru.netology.web.data;

import lombok.Value;

public class DataHelper {



    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    @Value
    public static class CardNumbers {
//        private String cardNumber;



        public static String getCardNumber(int numberOfCard){

            String[] cardNumbers = {"5559 0000 0000 0001", "5559 0000 0000 0002"};

            return  cardNumbers[numberOfCard];
        }



//
////        String[] cardNumbers = {"5559 0000 0000 0001", "5559 0000 0000 0002"};
//        private ArrayList cardNumbers = new ArrayList<>();
//
//        public ArrayList getCardNumbers(int index) {
//            cardNumbers.add("5559 0000 0000 0001");
//            cardNumbers.add("5559 0000 0000 0002");
//            return (ArrayList) cardNumbers.get(index);
//        }
    }


    ;
}