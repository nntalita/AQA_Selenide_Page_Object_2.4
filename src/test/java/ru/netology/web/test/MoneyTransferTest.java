package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() throws InterruptedException {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        //        перевод с первой карты на вторую
        dashboardPage.transfer(10000, 0, 1);
        var actualFirstCardBalans = dashboardPage.getCardBalance(0);
        assertEquals(0, actualFirstCardBalans);
        var actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(20000, actualSecondCardBalans);
        // обратный перевод
        dashboardPage.transfer(10000, 1, 0);
        actualFirstCardBalans = dashboardPage.getCardBalance(0);
        assertEquals(10000, actualFirstCardBalans);
        actualSecondCardBalans = dashboardPage.getCardBalance(1);
        assertEquals(10000, actualSecondCardBalans);
    }
}
