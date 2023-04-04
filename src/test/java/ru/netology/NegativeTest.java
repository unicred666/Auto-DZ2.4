package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;


public class NegativeTest {

    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(5);
    LocalDate invalidDate = today.plusDays(-5);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }

    @Test
    public void negativeTestСityFalse() {

        $("[data-test-id='city'] input").setValue("Баку");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void negativeTestСityTypo() {

        $("[data-test-id='city'] input").setValue("Уфо");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void negativeTestNoCity() {

        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

//        $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Поле обязательно для заполнения"));
    }

    @Test
    public void negativeTestInvalidDate() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(invalidDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    // ввод несуществующей даты (например 32 число)
    public void negativeTestInvalidDate2() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys("32.01.2023");
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Неверно введена дата"));
    }

    @Test
    public void negativeTestTodayData() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(today));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void negativeTestNoDate() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Неверно введена дата"));
    }

    @Test
    public void negativeTestInvalidName() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Zukov Ivan");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void negativeTestInvalidNameЁ() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жукова Алёна");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void negativeTestInvalidTel() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+7999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void negativeTestInvalidTelNotPlus() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("89999999999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void negativeTestEmptyCheckbox() {

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Жуков Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");

        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//label[@data-test-id='agreement']").should(cssClass("input_invalid"));
    }
}

