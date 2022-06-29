package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.netology.page.DashboardPage;

@NoArgsConstructor

public class DataHelper {

    public static int getSumForTopUpFirstCard(){
        CardInfo cardInfoSecond = getSecondCard();
        int balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        Faker faker = new Faker();
        int randomSum = faker.random().nextInt(1, balanceSecond);
        return randomSum;
    }

    public static int getSumForTopUpSecondCard(){
        CardInfo cardInfoFirst = getFirstCard();
        int balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst)+1;
        Faker faker = new Faker();
        int randomSum = faker.random().nextInt(balanceFirst);
        return randomSum;
    }

    public static int getInvalidSumForTopUpFirstCard(){
        CardInfo cardInfoSecond = getSecondCard();
        int balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond)+1;
        Faker faker = new Faker();
        int randomSum = faker.random().nextInt(balanceSecond,1000000);
        return randomSum;
    }

    public static int getInvalidSumForTopUpSecondCard(){
        CardInfo cardInfoFirst = getFirstCard();
        int balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        Faker faker = new Faker();
        int randomSum = faker.random().nextInt(balanceFirst,1000000);
        return randomSum;
    }
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class CardInfo {
        private String id;
        private String cardNumber;
    }


    public static CardInfo getFirstCard(){
        return new CardInfo("0001", "5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard(){
        return new CardInfo("0002", "5559 0000 0000 0002");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }
}
