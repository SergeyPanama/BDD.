package ru.netology.steps;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardsPage;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selenide.open;

public class TemplateSteps {
    
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardInfoFirst = DataHelper.getFirstCard();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCard();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int  randomSum = DataHelper.getSumForTopUpFirstCard();
        cardsPage.topUpFirstCard();
        val transferPage =new TransferPage();
        transferPage.topUp(randomSum);
        val balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst + randomSum;
        int expectedSecond= initialBalanceSecond - randomSum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferMoneyfromFirstToSecond() {
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardInfoFirst = DataHelper.getFirstCard();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCard();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int  randomSum = DataHelper.getSumForTopUpSecondCard();
        cardsPage.topUpSecondCard();
        val transferPage =new TransferPage();
        transferPage.topUp(randomSum);
        val balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - randomSum;
        int expectedSecond= initialBalanceSecond + randomSum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldNotTransferIfBalanseNotEnough() {
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val cardsPage = new CardsPage();
        val cardInfoFirst = DataHelper.getFirstCard();
        val initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCard();
        val initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int  randomSum = DataHelper.getInvalidSumForTopUpSecondCard();
        cardsPage.topUpSecondCard();
        val transferPage =new TransferPage();
        transferPage.topUp(randomSum);
        val balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        val balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - randomSum;
        int expectedSecond= initialBalanceSecond + randomSum;
        Assertions.assertFalse(expectedFirst < 0);
        assertEquals(initialBalanceSecond, balanceSecond);
    }
}
