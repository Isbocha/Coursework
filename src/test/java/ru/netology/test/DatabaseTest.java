package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.FirstPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class DatabaseTest {
    PaymentPage paymentPage;

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanDatabase();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
    }


    @Test
    void shouldBeApprovedWithApprovedCard() {
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutAPositiveOperation();
        assertEquals("APPROVED", SQLHelper.getStatus());
    }

    @Test
    void shouldBeDeclinedWithDeclinedCard() {
        var card = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutANegativeOperation();
        assertEquals("DECLINED", SQLHelper.getStatus());
    }
}
