package me.resheno.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.valueOf;

public class parameterizedTestResheno {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void beforeEach() {
        open("https://resheno.me");
        Configuration.fastSetValue = true;
    }


    @AfterEach
    void afterEach() {
        closeWebDriver();
    }

    @ValueSource(strings = {"шиномонтажа", "моек"})
    @ParameterizedTest(name = "Проверка открытия вкладки {0} через меню Решения")
    void reshenoOpenSolution(String solution) {
        $(".nav-menu-list button", 0).hover();
        $(byText(solution)).click();
        $("h1").shouldHave(text(solution));
    }

    @CsvSource(value = {
            "Тест Рус/ 9005000505/ auto@test.ru/ коммент/ Спасибо, мы скоро с вами свяжемся!",
            "Test Eng/ 9005000505/ auto@test.ru/ comment/ Спасибо, мы скоро с вами свяжемся!"
    },
            delimiter = '/')
    @ParameterizedTest(name = "Проверка отправки заявки с главной страницы {0}")
    void reshenoTestLeads(String clientName, String phoneNumber, String clientEmail, String clientComment, String testResult) {
        $(".header-action").click();
        $(".wrap-form-group input", 0).setValue(clientName);
        $(".wrap-form-group input", 1).setValue(phoneNumber);
        $(".wrap-form-group input", 2).setValue(clientEmail);
        $(".input-textarea").setValue(clientComment);
        $(".form button").click();
        $(".modal-content").shouldHave(text(testResult));
    }

    static Stream<Arguments> searchTyresTest() {
        return Stream.of(
                Arguments.of("Москва", "175", "65", "Nokian"),
                Arguments.of("Санкт-Петербург", "225", "75", "MICHELIN")
        );
    }

    @MethodSource(value = "searchTyresTest")
    @ParameterizedTest(name = "Проверка поиска шин")
    void calculatorTest(String city, String width, String height, String car) {
        open("/katalog-tovarov/shiny");
        $(withText(city)).click();
        $(".location-delivery").shouldHave(text(city));
        $(".multiselect__tags").click();
        $(byText(width)).click();
        $(".multiselect__tags", 1).click();
        $(byText(height)).click();
        $(byText(car)).click();
        $(".product-title").shouldHave(text(car));
        $(".product-name").shouldHave(text(width + "/" + height));

}

}
