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
    PaymentPage paymentPage;
    CreditPage creditPage;

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
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        creditPage = paymentPage.creditButton();
        creditPage.creditPageVisible();
    }

    @Test
        //Успешное приобретение тура с помощью одобренной карты
    void successfulPurchaseOfATourUsingAnApprovedCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutAPositiveOperation();
    }

    @Test
        //Неуспешная попытка приобретение тура с помощью отклоненной карты
    void unsuccessfulAttemptToPurchaseATourUsingADeclinedCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getMessageAboutANegativeOperation();
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (8 цифр)
    void shouldVisibleNotificationWithEightDigitsOfTheCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateInvalidCardNumberWithEightDigits();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (1 цифра)
    void shouldVisibleNotificationWithASingleDigitCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateOneARandomNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (15 цифр)
    void shouldVisibleNotificationWithFifteenDigitsOfTheCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.generateInvalidCardNumberWithFifteenDigits();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с невалидным значением номера карты (16 нулей)
    void shouldVisibleNotificationWithACardOfSixteenZeros() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getInvalidCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
//Неуспешная попытка оплаты картой с незаполненным значением номера карты
    void shouldVisibleNotificationWithAnEmptyCard() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        String card = null;
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCardNumberField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием данных Владельца на русском языке
    void shouldVisibleNotificationWithTheFullNameInRussian() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("ru");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с частичным указанием данных Владельца
    void shouldVisibleNotificationWithTheFirstName() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generatePartOfTheName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Владелец
    void shouldVisibleNotificationWithAnEmptyName() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        String fullName = null;
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Поле обязательно для заполнения");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Владелец значения с цифрами
    void shouldVisibleNotificationWithANameWithNumbers() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.getInvalidNameWithNumbers();
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Владелец значения со спецсимволами
    void shouldVisibleNotificationWithANameWithSpecialCharacters() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.getInvalidNameWithЫpecialСharacterss();
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheNameField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц невалидного значения
    void shouldVisibleNotificationWithAnInvalidMonth() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateOneARandomNumber();
        var year = DataHelper.generateYear(3);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц предыдущего месяца текущего года
    void shouldVisibleNotificationWithThePastMonth() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-1);
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверно указан срок действия карты");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Месяц
    void shouldVisibleNotificationWithAnEmptyMonth() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        String month = null;
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Месяц нулей
    void shouldVisibleNotificationWithZerosInTheMonthField() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        String month = "00";
        var year = DataHelper.generateYear(0);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheMonthField("Неверно указан срок действия карты");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле Год истекшего срока
    void shouldVisibleNotificationWithLastYear() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(6);
        var year = DataHelper.generateYear(-1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheYearField("Истёк срок действия карты");
    }

    @Test
        //Неуспешная попытка приобретение тура по карте с указанием в поле Год будущего срока
    void shouldVisibleNotificationAfterFutureYears() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        firstPage.paymentButton();
        paymentPage = firstPage.paymentButton();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(9);
        var year = DataHelper.generateYear(7);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheYearField("Неверно указан срок действия карты");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем Год
    void shouldVisibleNotificationWithAnEmptyYear() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(6);
        String year = null;
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.getRandomValidCVC();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheYearField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле CVC/CVV невалидного значения (1 цифра)
    void shouldVisibleNotificationWithASingleDigitCVC() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.generateOneARandomNumber();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с указанием в поле CVC/CVV невалидного значения (2 цифры)
    void shouldVisibleNotificationWithATwoDigitCVC() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        var cvc = DataHelper.generateTwoARandomNumber();
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }

    @Test
        //Неуспешная попытка оплаты картой с незаполненным полем CVC/CVV
    void shouldVisibleNotificationWithAnEmptyCVC() {
        var firstPage = new FirstPage();
        firstPage.orderCardPreviewVisible();
        paymentPage = firstPage.paymentButton();
        paymentPage.paymentPageVisible();
        var card = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(10);
        var year = DataHelper.generateYear(1);
        var fullName = DataHelper.generateFullName("en");
        String cvc = null;
        paymentPage.userData(card, month, year, fullName, cvc);
        paymentPage.getErrorTextInTheCVCField("Неверный формат");
    }
}
