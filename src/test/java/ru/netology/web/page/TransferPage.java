package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.web.data.DataHelper.CardNumbers;

public class TransferPage {
    private final SelenideElement amountTransfer = $("[data-test-id=amount] input");
    private final SelenideElement whereFrom = $("[data-test-id=from] input");
    private final SelenideElement topUpButton = $("[data-test-id=action-transfer]");
    private final SelenideElement heading = $("[data-test-id=dashboard]");


    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void transfer(int amount, int numberDebitCard) {

        amountTransfer.sendKeys(Integer.toString(amount));
        whereFrom.sendKeys(CardNumbers.getCardNumber(numberDebitCard - 1));
        topUpButton.click();

    }
}
