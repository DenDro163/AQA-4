import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;


public class CardDeliveryTest {
    private int dayRange = 3;// Дата встречи не ранее этого количества дней
    String date = LocalDate.now().plusDays(dayRange).format(ofPattern("dd.MM.yyyy"));// Определяем дату на сегодня, добавляем 3 дня и форматируем ее
    private int badDayRange1 = 2;//Косячный интервал для тестов, все что ниже тоже
    String badDate1 = LocalDate.now().plusDays(badDayRange1).format(ofPattern("dd.MM.yyyy"));
    private int badDayRange2 = -4;
    String badDate2 = LocalDate.now().plusDays(badDayRange2).format(ofPattern("dd.MM.yyyy"));


    @BeforeEach
    void setUp() {//Открываем страницу.
        //   Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTestCardDeliveryForm() {//Тестируем форму валидными значениями
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    //Тестируем пустые поля.
    @Test
    void shouldTestWarnIfAllFieldsEmpty() {
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfCityFieldEmpty() {
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfNameFieldEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfPhoneFieldEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfCheckBoxEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    //Тестируем неккоректный ввод данных.

    @Test
    void shouldTestWarnIfBadCityProvince() {
        $("[data-test-id=city] input").setValue("Тольятти");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadCityEnglish() {
        $("[data-test-id=city] input").setValue("Tomsk");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadDateLess() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(badDate1);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadDateMinus() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(badDate2);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadUserNameEnglish() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Ivanov Denis");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadPhoneLess() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+793704");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadPhoneMore() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+123574784845");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }

    @Test
    void shouldTestWarnIfBadPhoneLetters() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+sdgtykdenvr");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid").shouldBe(exist);
    }
}
