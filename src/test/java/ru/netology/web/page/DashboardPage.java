package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Thread.*;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item");
    private final SelenideElement amountTransfer = $("[data-test-id=amount] input");
    private final SelenideElement whereFrom = $("[data-test-id=from] input");
    private final SelenideElement topUpButton = $("[data-test-id=action-transfer]");
    private final ElementsCollection cardSelectionTopUpButton = $$("[data-test-id=action-deposit]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    String[] cardNumbers = {"5559 0000 0000 0001", "5559 0000 0000 0002"};

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int index) {
        val text = cards.get(index).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void transfer(int amount, int debitCardIndex, int creditCardIndex) throws InterruptedException {
        //        метод перевода с карты на карту -
        heading.shouldBe(visible);
        cardSelectionTopUpButton.get(creditCardIndex).click();  // нажать на кнопку перевести у нужной карты
        amountTransfer.shouldBe(appear); // появилось поле ввода
        amountTransfer.doubleClick().sendKeys(Keys.BACK_SPACE);
        amountTransfer.doubleClick().sendKeys(Keys.BACK_SPACE);

        amountTransfer.sendKeys(Integer.toString(amount));
        sleep(2000);
        whereFrom.sendKeys(cardNumbers[debitCardIndex]);
        whereFrom.sendKeys(Keys.BACK_SPACE,Integer.toString(debitCardIndex + 1));

        sleep(2000);
        topUpButton.click();
        sleep(2000);
        heading.shouldBe(visible);
    }
}
