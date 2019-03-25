
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
                    { "Misha", "Pysha","09111990", true }, { "IVAN", "PETROV","01011998", false} ,{ "PERCHIK", "LERCHIK","06081994", true}
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




        scrollToObjectByXPath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");
        clicByXpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");


        scrollToObjectByXPath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]");
        do fillForm(By.xpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]"), todayPlusTwoWeeks());
        while (getTextByXpath("//span[contains(text(), 'Заполните')]").contains("Заполните"));

        clicByXpath("//*[contains(text(), '90 дней')]");

        do fillForm(By.xpath("//*[contains(@class, 'form-control')][contains(@data-bind, 'attr: _params.fullName.attr,')]"), firstName + " " + lastName);
        while (getTextByXpath("//*[contains(@class, 'form-control')][contains(@data-bind, 'attr: _params.fullName.attr,')]").equals(firstName + " " + lastName));

        do fillForm(By.xpath("//input[@data-test-name='BirthDate']"), bDay);
        while (getTextByXpath("//input[@data-test-name='BirthDate']").equals(bDay));


        if (!driver.findElements(By.xpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class=\"calc-vzr-toggle-risk-group\"]//div[@class=\"toggle off toggle-rgs\"]")).isEmpty()) {
            clicByXpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class=\"calc-vzr-toggle-risk-group\"]//div[@class=\"toggle off toggle-rgs\"]");
            clicByXpath("//div[contains(@class,'toggle toggle-rgs')]");
        }

        if (!driver.findElements(By.xpath("//div[contains(@class,'toggle toggle-rgs off')]")).isEmpty() && rest == true)
        {
            clicByXpath("//div[contains(@class,'toggle toggle-rgs')]");
        } else if (driver.findElements(By.xpath("//div[contains(@class,'toggle toggle-rgs off')]")).isEmpty() && rest == false)
        {
            clicByXpath("//div[contains(@class,'toggle toggle-rgs')]");
        }





























    }

}
