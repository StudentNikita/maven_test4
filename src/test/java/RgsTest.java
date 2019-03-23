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
                    { "Misha", "Pysha","09111990", true }, { "PERCHIK", "LERCHIK","06081994", false }
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

        clicByXpath("//ol/li/a[contains(text(), 'Страхование')]");
        clicByXpath("//*[contains(text(), 'Выезжающим за рубеж')]");
        scrollToObjectByXPath("//a[contains(text(), 'Рассчитать')]");
        clicByXpath("//a[contains(text(), 'Рассчитать')]");

        Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class, 'page')]/span[@class='h1']")))); // ждем, когда появится нужный нам объект

        compareText(By.xpath("//div[contains(@class, 'page')]/span[@class='h1']"), "Страхование выезжающих за рубеж");


        scrollToObjectByXPath("//button/*[contains(@class, 'content-title')]");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button/*[contains(@class, 'content-title')]")))); // ждем, когда появится нужный нам объект


        clicByXpath("//button/*[contains(@class, 'content-title')]");

        fillForm(By.xpath("//*[contains(@class, 'actual-input')]"), "шенген");
        clicByXpath("//*[contains(@class, 'tt-menu tt')]");
        clicByName("ArrivalCountryList");
        clicByXpath("//*[contains(text(), 'Испания')]");
        clicByName("ArrivalCountryList");
        scrollToObjectByXPath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");
        clicByXpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");

        do fillForm(By.xpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]"), todayPlusTwoWeeks());
        while (getTextByXpath("//span[contains(text(), 'Заполните')]").contains("Заполните"));

        clicByXpath("//*[contains(text(), '90 дней')]");

        fillForm(By.xpath("//div[@data-fi-input-mode=\"combined\"]//div[@class=\"form-group\"]//input[@class=\"form-control\" != @disabled]"), firstName + " " + lastName);

        fillForm(By.xpath("//input[@data-test-name='BirthDate']"), bDay);
        scrollToObjectByXPath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class='calc-vzr-toggle-risk-group']//div[@class='toggle off toggle-rgs']");


       if (rest == true) clicByXpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class='calc-vzr-toggle-risk-group']//div[@class='toggle off toggle-rgs']");



        scrollToObjectByXPath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']");


        if (!driver.findElement(By.xpath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']")).isSelected())  // проверка на "галочку" у чекбокса, если галочка не стоит, то ставим
        {
            clicByXpath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']");

        }

        clicByXpath("//div[contains(@class, 'valid')]/button[contains(text(), 'Рассчитать')]");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains (text(),'Комфорт')]"))));
        scrollToObjectByXPath("//h1[contains (text(),'Кальку')]");


        compareText(By.xpath("//div/span[@data-bind]/span[contains(@class, 'text-bold')]"), "Многократные поездки в течение года");
        compareText(By.xpath("//span/span/strong[@data-bind='text: Name']"), "Шенген");
        compareText(By.xpath("//strong[@data-bind=\"text: LastName() + ' ' + FirstName()\"]"), "LERCHIK" + " PERCHIK");
        compareText(By.xpath("//strong[@data-bind=\" text: BirthDay.repr('moscowRussianDate')\"]"), "06." + "08." + "1994");
        
    }

}
