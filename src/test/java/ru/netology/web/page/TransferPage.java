package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Thread.sleep;
import static ru.netology.web.data.DataHelper.CardNumbers;

public class TransferPage {
    private final SelenideElement amountTransfer = $("[data-test-id=amount] input");
    private final SelenideElement whereFrom = $("[data-test-id=from] input");
    private final SelenideElement topUpButton = $("[data-test-id=action-transfer]");
    private final SelenideElement heading = $("[data-test-id=dashboard]");


    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void transfer(int amount, int numberDebitCard) throws InterruptedException {
//, DataHelper.CardNumbers cardNumbers

//        amountTransfer.shouldBe(appear); // появилось поле ввода
//        amountTransfer.doubleClick().sendKeys(Keys.BACK_SPACE);
//        amountTransfer.doubleClick().sendKeys(Keys.BACK_SPACE);

        amountTransfer.sendKeys(Integer.toString(amount));

//        whereFrom.sendKeys(cardNumbers[debitCardIndex]);
        whereFrom.sendKeys(CardNumbers.getCardNumber(numberDebitCard - 1));
//        whereFrom.sendKeys(Keys.BACK_SPACE, Integer.toString(cardNumber));



        topUpButton.click();
        sleep(2000);

      heading.shouldBe(visible);
    }
}
