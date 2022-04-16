import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.ExactText;
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

    public String generateDate(int dayRange) {
        return LocalDate.now().plusDays(dayRange).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {//Открываем страницу.
        //   Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTestCardDeliveryForm() {//Тестируем форму валидными значениями
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(3)));
    }

    //Тестируем пустые поля.
    @Test
    void shouldTestWarnIfAllFieldsEmpty() {
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWarnIfCityFieldEmpty() {
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWarnIfNameFieldEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWarnIfPhoneFieldEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWarnIfCheckBoxEmpty() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $(".button__text").click();
        $(".input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    //Тестируем неккоректный ввод данных.

    @Test
    void shouldTestWarnIfBadCityProvince() {//
        $("[data-test-id=city] input").setValue("Тольятти");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestWarnIfBadCityEnglish() {
        $("[data-test-id=city] input").setValue("Tomsk");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestWarnIfBadDateLess() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(2));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestWarnIfBadDateMinus() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(-4));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestWarnIfBadUserNameEnglish() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Ivanov Denis");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWarnIfBadPhoneLess() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+793704");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWarnIfBadPhoneMore() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+123574784845");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWarnIfBadPhoneLetters() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+sdgtykdenvr");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
