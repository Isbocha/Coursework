package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private final ElementsCollection button = $$(By.cssSelector(".button"));
    private final SelenideElement text = $("h2");
    private final ElementsCollection textPayPage = $$(By.cssSelector("h3"));
    private final SelenideElement orderCardPreview = $(".Order_cardPreview__47B2k");
    private final SelenideElement cardNumberField = $(byText("Номер карты"))
            .parent()
            .find(".input__sub");
    private final SelenideElement monthField = $(byText("Месяц"))
            .parent()
            .find(".input__sub");
    private final SelenideElement yearField = $(byText("Год"))
            .parent()
            .find(".input__sub");
    private final SelenideElement nameField = $(byText("Владелец"))
            .parent()
            .find(".input__sub");
    private final SelenideElement cvcCodeField = $(byText("CVC/CVV"))
            .parent()
            .find(".input__sub");
    private final SelenideElement positiveResponse = $(byText("Операция одобрена Банком."));
    private final SelenideElement negativeResponse = $(byText("Операция не одобрена Банком."));
    private final ElementsCollection field = $$(By.cssSelector(".input__control"));


    public void paymentPageVisible() {
        orderCardPreview.shouldBe(visible);
        text.shouldBe(visible);
        text.shouldHave(Condition.exactText("Путешествие дня"));
        button.get(0).shouldBe(visible);
        button.get(1).shouldBe(visible);
        textPayPage.get(1).shouldHave(Condition.exactText("Оплата по карте"));
        button.get(2).shouldBe(visible);
    }

    public void paymentButton() {
        button.get(0).click();
    }

    public CreditPage creditButton() {
        button.get(1).click();
        return new CreditPage();
    }

    public void resumeButton() {
        button.get(2).click();
    }

    public void userData(String cardNumber, String month, String year, String name, String cvc) {
        field.get(0).setValue(cardNumber);
        field.get(1).setValue(month);
        field.get(2).setValue(year);
        field.get(3).setValue(name);
        field.get(4).setValue(cvc);
        button.get(2).click();
    }

    public void getMessageAboutAPositiveOperation() {
        positiveResponse.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void getMessageAboutANegativeOperation() {
        negativeResponse.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void getErrorTextInTheCardNumberField(String text) {
        cardNumberField.shouldHave(Condition.exactText(text));
        cardNumberField.shouldBe(visible);
    }

    public void getErrorTextInTheMonthField(String text) {
        monthField.shouldHave(Condition.exactText(text));
        monthField.shouldBe(visible);
    }

    public void getErrorTextInTheYearField(String text) {
        yearField.shouldHave(Condition.exactText(text));
        yearField.shouldBe(visible);
    }

    public void getErrorTextInTheNameField(String text) {
        nameField.shouldHave(Condition.exactText(text));
        nameField.shouldBe(visible);
    }

    public void getErrorTextInTheCVCField(String text) {
        cvcCodeField.shouldHave(Condition.exactText(text));
        cvcCodeField.shouldBe(visible);
    }
}
