import org.junit.*;

import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public  static WebDriver driver;

    @BeforeClass
    public static void setUp() throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Тест по селениум!");

        int res = 1;

        switch (res){
            case 1: System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe"); driver = new ChromeDriver(); break;
            case 2: System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe"); driver = new FirefoxDriver(); break;
        }




        //System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");
        //driver = new FirefoxDriver();




        String url = "https://www.rgs.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(url);

        clicByXpath("//ol/li/a[contains(text(), 'Страхование')]");
        clicByXpath("//*[contains(text(), 'Выезжающим за рубеж')]");
        scrollToObjectByXPath("//a[contains(text(), 'Рассчитать')]");
        clicByXpath("//a[contains(text(), 'Рассчитать')]");

        Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class, 'page')]/span[@class='h1']")))); // ждем, когда появится нужный нам объект

        compareText(By.xpath("//div[contains(@class, 'page')]/span[@class='h1']"), "Страхование выезжающих за рубеж");



        scrollToObjectByXPath("//button/*[contains(@class, 'content-title')]");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button/*[contains(@class, 'content-title')]")))); // ждем, когда появится нужный нам объект
        //scrollToObjectByXPath("//button/*[contains(@class, 'content-title')]"); // вейт и скрол менял местами (был крол потом вейт)

        clicByXpath("//button/*[contains(@class, 'content-title')]");

        fillForm(By.xpath("//*[contains(@class, 'actual-input')]"), "шенген");
        scrollToObjectByXPath("//*[contains(@class, 'actual-input')]");
        clicByXpath("//*[contains(@class, 'tt-menu tt')]");
        clicByName("ArrivalCountryList");
        clicByXpath("//*[contains(text(), 'Испания')]");
        clicByName("ArrivalCountryList");

    }

    @AfterClass
    public static void close() {
        Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
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
        driver.quit();
    }

    public static void fillForm(By locator, String value) {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(locator).sendKeys(value);


    }

    public  String todayPlusTwoWeeks() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, 14);
        String newDate = dateFormat.format(instance.getTime());
        return newDate;
    }

    public static void clicByXpath(String xPath) {
        driver.findElement(By.xpath(xPath)).click();

    }

    public static void clicByName(String name) {
        driver.findElement(By.name(name)).click();

    }

    public static String getTextByXpath(String xPath) {
        return driver.findElement(By.xpath(xPath)).getText();

    }

    public static void scrollToObjectByXPath(String xPath) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(xPath)));
    }

    public static void compareText(By locator, String expect) {
       Assert.assertTrue("Искомого текста НЕТ: " + expect + ". Вместо него: " + driver.findElement(locator).getText(), driver.findElement(locator).getText().contains(expect));
        System.out.println("Искомый текст ЕСТЬ: " + expect);
    }





}

