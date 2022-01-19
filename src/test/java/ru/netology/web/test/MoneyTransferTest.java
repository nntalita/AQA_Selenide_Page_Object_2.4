package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

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
    void shouldTransferMoneyBetweenOwnCardsOnLimit() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        int amount = 10000;  // сумма перевода
        var initialFirstCardBalans = dashboardPage.getCardBalance(0); // начальный баланс карты 1
        var initialSecondCardBalans = dashboardPage.getCardBalance(1);  // начальный баланс карты 2
        //        перевод с первой карты на вторую
        dashboardPage.transfer(amount, 0, 1);
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        assertEquals(initialFirstCardBalans - amount, actualFirstCardBalance);
        var actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(initialSecondCardBalans + amount, actualSecondCardBalans);
        // обратный перевод
        dashboardPage.transfer(amount, 1, 0);
        actualFirstCardBalance = dashboardPage.getCardBalance(0);
        assertEquals(initialFirstCardBalans, actualFirstCardBalance);
        actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(initialSecondCardBalans, actualSecondCardBalans);
    }

      @Test
    void shouldTransferMoneyBetweenOwnCardsUnderLimit() throws InterruptedException {
        var dashboardPage = new DashboardPage();
        int amount = 100000;  // сумма перевода
        var initialFirstCardBalans = dashboardPage.getCardBalance(0); // начальный баланс карты 1
        var initialSecondCardBalans = dashboardPage.getCardBalance(1);  // начальный баланс карты 2
        //        перевод с первой карты на вторую
        dashboardPage.transfer(amount, 0, 1);
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        assertEquals(initialFirstCardBalans, actualFirstCardBalance);
        var actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(initialSecondCardBalans, actualSecondCardBalans);
        // обратный перевод
        dashboardPage.transfer(amount, 1, 0);
        actualFirstCardBalance = dashboardPage.getCardBalance(0);
        assertEquals(initialFirstCardBalans, actualFirstCardBalance);
        actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(initialSecondCardBalans, actualSecondCardBalans);
    }
}
