import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ComplexElementsTest {
    String twoLettersForSearch = "вл";
    String city = "Владимир";

    private int dayRange = 7;// Дата встречи не ранее этого количества дней
    private LocalDate currentDate = LocalDate.now();// Дата на сегодня
    LocalDate deliveryDate = LocalDate.now().plusDays(dayRange);// Определяем дату на сегодня, добавляем 7 дней


    @BeforeEach
    void setUp() {//Открываем страницу.
        //   Configuration.headless = true;
        open("http://localhost:9999");
    }

    //Выбор даты на неделю вперед с текущей даты через инструменты календаря
    void setDateStepSevenDays() {
        $(".calendar-input__custom-control").click();
        if (currentDate.getMonthValue() != deliveryDate.getMonthValue()) {
            $("[data-step]").click();
        }
        $$("td.calendar__day").find(text(deliveryDate.format(ofPattern("d")))).click();
    }


    // Ввод 2-х букв в поле город и выбор нужного из выпадающего списка.
    @Test
    void shouldTestCardDeliveryForm() {
        $("[data-test-id=city] input").setValue(twoLettersForSearch);
        $$(".menu-item").find(exactText(city)).click();
        setDateStepSevenDays();
        $("[data-test-id=name] input").setValue("Денисов Андрей");
        $("[data-test-id=phone] input").setValue("+79370400780");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
    }
}
