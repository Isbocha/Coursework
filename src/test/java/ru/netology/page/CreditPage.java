package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
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

    public void creditPageVisible() {
        orderCardPreview.shouldBe(visible);
        text.shouldBe(visible);
        text.shouldHave(Condition.exactText("Путешествие дня"));
        button.get(0).shouldBe(visible);
        button.get(1).shouldBe(visible);
        textPayPage.get(1).shouldHave(Condition.exactText("Кредит по данным карты"));
        button.get(2).shouldBe(visible);
    }

    public PaymentPage paymentButton() {
        button.get(0).click();
        return new PaymentPage();
    }

    public void creditButton() {
        button.get(1).click();
    }

    public void resumeButton() {
        button.get(2).click();
    }

    public void userData(String cardNumber, String month, String year, String name, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        yearField.setValue(year);
        nameField.setValue(name);
        cvcCodeField.setValue(cvc);
        button.get(2).click();
    }

    public void getMessageAboutAPositiveOperation() {
        positiveResponse.shouldBe(visible);
    }

    public void getMessageAboutANegativeOperation() {
        negativeResponse.shouldBe(visible);
    }

    public void getErrorTextInTheCardNumberField(String text) {
        cardNumberField.shouldBe(visible);
        cardNumberField.shouldHave(Condition.exactText(text));
    }

    public void getErrorTextInTheMonthField(String text) {
        monthField.shouldBe(visible);
        monthField.shouldHave(Condition.exactText(text));
    }

    public void getErrorTextInTheYearField(String text) {
        yearField.shouldBe(visible);
        yearField.shouldHave(Condition.exactText(text));
    }

    public void getErrorTextInTheNameField(String text) {
        nameField.shouldBe(visible);
        nameField.shouldHave(Condition.exactText(text));
    }

    public void getErrorTextInTheCVCField(String text) {
        cvcCodeField.shouldBe(visible);
        cvcCodeField.shouldHave(Condition.exactText(text));
    }
}
