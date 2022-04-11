package me.resheno.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

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


}