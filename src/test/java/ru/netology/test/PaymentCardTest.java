package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.FirstPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class PaymentCardTest {
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @AfterAll
    static void removeListener() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void openAllPageTest() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        paymentPage.creditButton();
        var creditPage = new CreditPage();
        creditPage.creditPageVisible();
    }

    @Test
        //Успешное приобретение тура с помощью одобренной карты
    void successfulPurchaseOfATourUsingAnApprovedCardTest() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutAPositiveOperation();
    }

    @Test
        //Неуспешная попытка приобретение тура с помощью отклоненной карты
    void unsuccessfulAttemptToPurchaseATourUsingADeclinedCardTest() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutANegativeOperation();
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (8 цифр)
    void test4() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateInvalidCardNumberWithEightDigits();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (1 цифра)
    void test5() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateOneARandomNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (15 цифр)
    void test6() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateInvalidCardNumberWithFifteenDigits();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (16 нулей)
    void test7() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getInvalidCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
//Неуспешная попытка оплаты картой с незаполненным значением номера карты
    void test8() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        String card = null;
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием данных Владельца на русском языке
    void test9() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("ru");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с частичным указанием данных Владельца
    void test10() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generatePartOfTheName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Владелец
    void test11() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        String fullName = null;
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Поле обязательно для заполнения");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Владелец значения с цифрами
    void test12() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        String fullName = "4565432 Е6521jdfhud";
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Владелец значения со спецсимволами
    void test13() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        String fullName = " M%%:* 6546544?*?;@#%&";
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц невалидного значения
    void test14() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateOneARandomNumber();
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц предыдущего месяца текущего года
    void test15() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверно указан срок действия карты");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Месяц
    void test16() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        String month = null;
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц нулей
    void test17() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        String month = "00";
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверно указан срок действия карты");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Год истекшего срока
    void test18() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(6);
        var year = DataHelper.generateYear(-1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheYearField("Истёк срок действия карты");
    }

    @Test
        //Успешная попытка приобретение тура по карте с указанием в поле Год будущего срока
    void test19() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(9);
        var year = DataHelper.generateYear(7);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutAPositiveOperation();
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Год
    void test20() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(6);
        String year = null;
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheYearField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле CVC/CVV невалидного значения (1 цифра)
    void test21() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.generateOneARandomNumber();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле CVC/CVV невалидного значения (2 цифры)
    void test22() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.generateTwoARandomNumber();
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем CVC/CVV
    void test23() {
        var firstPage = new FirstPage();
        var paymentPage = new PaymentPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        String cvc = null;
        paymentPage.userData2(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }
}
