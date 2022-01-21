package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferMoneyBetweenFromFirstCardsOnLimit() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        int amount = 1;
        int debitCard = 1;
        int creditCard = 2;
        var initialFirstCardBalans = dashboardPage.getCardBalance(debitCard);
        var initialSecondCardBalans = dashboardPage.getCardBalance(creditCard);
        dashboardPage.selectTopUpButton(creditCard);
        transferPage.transfer(amount, debitCard);
        var actualFirstCardBalance = dashboardPage.getCardBalance(debitCard);
        assertEquals(initialFirstCardBalans - amount, actualFirstCardBalance);
        var actualSecondCardBalans = dashboardPage.getCardBalance(creditCard);
        assertEquals(initialSecondCardBalans + amount, actualSecondCardBalans);
    }

    @Test
    void shouldTransferMoneyFromSecondCardsOnLimit() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        int amount = 1000;
        int debitCard = 2;
        int creditCard = 1;
        var initialFirstCardBalans = dashboardPage.getCardBalance(1);
        var initialSecondCardBalans = dashboardPage.getCardBalance(2);
        dashboardPage.selectTopUpButton(creditCard);
        transferPage.transfer(amount, debitCard);
        var actualFirstCardBalance = dashboardPage.getCardBalance(1);
        assertEquals(initialFirstCardBalans + amount, actualFirstCardBalance);
        var actualSecondCardBalans = dashboardPage.getCardBalance(2);
        assertEquals(initialSecondCardBalans - amount, actualSecondCardBalans);

    }

    @Test
    void shouldTransferMoneyFromSecondCardsUnderLimit() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        int amount = 100000;
        int debitCard = 1;
        int creditCard = 2;
        var initialFirstCardBalans = dashboardPage.getCardBalance(debitCard);
        var initialSecondCardBalans = dashboardPage.getCardBalance(creditCard);
        dashboardPage.selectTopUpButton(creditCard);
        transferPage.transfer(amount, debitCard);
        var actualFirstCardBalance = dashboardPage.getCardBalance(1);
        assertEquals(initialFirstCardBalans, actualFirstCardBalance);
        var actualSecondCardBalans = dashboardPage.getCardBalance(2);
        assertEquals(initialSecondCardBalans, actualSecondCardBalans);
    }
}
