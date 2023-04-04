package ru.netology.web;

import com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

class PositiveTest {

    SelenideElement notification = $x("//div[@data-test-id='notification']");

//    LocalDate today = LocalDate.now();
//    LocalDate minData = today.plusDays(3);
//    LocalDate newDate = today.plusDays(5);
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }


    @Test
    public void positiveTest() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String currentDate = generateDate(5,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + currentDate));

    }

    @Test
    public void positiveTestDefaultDate() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String currentDate = generateDate(3,"dd.MM.yyyy");
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + currentDate));

    }
}