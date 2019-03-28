import org.junit.*;

import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.TestProperties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static WebDriver driver;
    public static Properties properties = TestProperties.getInstance().getProperties();
    protected static String baseUrl;

    @BeforeClass
    public static void setup() {
        switch (properties.getProperty("browser2")) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                break;
            case "explorer":
                System.setProperty("webdriver.ie.driver", properties.getProperty("webdriver.ie.driver"));
                driver = new InternetExplorerDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
        }
        baseUrl = properties.getProperty("app.url");
        driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        clicByXpath("//ol/li/a[contains(text(), 'Страхование')]");
        clicByXpath("//*[contains(text(), 'Выезжающим за рубеж')]");
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
    }

    @AfterClass
    public static void close() {
        Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
        scrollToObjectByXPath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']");
        checkboxOnOffByXPath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']", true);
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
        do driver.findElement(locator).sendKeys(value);
        while (driver.findElement(locator).getText().equals(value));
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
        scrollToObjectByXPath(xPath);
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

    public static void checkboxOnOffByXPath (String xPath, boolean button){
        clicByXpath("//div[contains(@data-bind,'activeRestOrSportsToggle')]/div[contains(@class,  'toggle-rgs')]");

        if (button == true && driver.findElement(By.xpath(xPath)).isEnabled()){
            clicByXpath(xPath);
        } else if (button == false && !driver.findElement(By.xpath(xPath)).isEnabled()){
            clicByXpath(xPath);
        }
    }
}

