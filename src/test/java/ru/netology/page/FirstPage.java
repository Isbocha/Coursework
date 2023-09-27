package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FirstPage {
    private final ElementsCollection button = $$(By.cssSelector(".button"));
    private final SelenideElement text = $("h2");
    private final SelenideElement orderCardPreview = $(".Order_cardPreview__47B2k");


    public void orderCardPreviewVisible() {
        orderCardPreview.shouldBe(visible);
        text.shouldBe(visible);
        text.shouldHave(Condition.exactText("Путешествие дня"));
        button.get(0).shouldBe(visible);
        button.get(1).shouldBe(visible);
    }

    public PaymentPage paymentButton () {
        button.get(0).click();
        return new PaymentPage();
    }

    public CreditPage creditButton () {
        button.get(1).click();
        return new CreditPage();
    }
}