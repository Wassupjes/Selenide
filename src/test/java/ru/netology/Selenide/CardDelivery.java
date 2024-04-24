package ru.netology.Selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;




public class CardDelivery {

    private String dateMeeting(long date, String pattern) {
        return LocalDate.now().plusDays(date).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCardDeliveryPositive() throws InterruptedException{
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Казань");
        String meeting = dateMeeting(3,"dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(meeting);
        $("[data-test-id=name] input").setValue("Владимиров Василий");
        $("[data-test-id=phone] input").setValue("+79888888888");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + meeting));
    }
}
