package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$("ul.list li");

    public DashboardPage() {
        cards.shouldHave(size(2));
    }

    public String getCardBalance(String cardNumber) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        SelenideElement card = cards.findBy(partialText(last4));
        String text = card.getText();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        String lastNumber = null;
        while (matcher.find()) {
            lastNumber = matcher.group();
        }
        if (lastNumber != null) {
            return lastNumber;
        }
        throw new IllegalStateException("Balance not found for card " + cardNumber);
    }

    public void clickReplenish(String cardNumber) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        SelenideElement card = cards.findBy(partialText(last4));
        card.$("[data-test-id='action-deposit']").click();
    }
}