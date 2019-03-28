
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RgsTest extends BaseTest {


    @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { "Misha", "Pysha","09111990", true }, { "IVAN", "PETROV","01011998", true} ,{ "PERCHIK", "LERCHIK","06081994", false}
            });
        }

        @Parameterized.Parameter // фамилия
        public  String lastName;

        @Parameterized.Parameter(1) // имя
        public  String firstName;

        @Parameterized.Parameter(2) // дата рождения
        public  String bDay;

        @Parameterized.Parameter(3) //планирует или нет активный отдых
        public  boolean rest;



    @Test
    public void rgs(){
        clicByXpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");
        scrollToObjectByXPath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]");
        fillForm(By.xpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]"), todayPlusTwoWeeks());
        clicByXpath("//*[contains(text(), '90 дней')]");
        fillForm(By.xpath("//*[contains(@class, 'form-control')][contains(@data-bind, 'attr: _params.fullName.attr,')]"), firstName + " " + lastName);
        fillForm(By.xpath("//input[@data-test-name='BirthDate']"), bDay);

        checkboxOnOffByXPath("//div[@class='toggle toggle-rgs off']", rest);
    }
    }
