import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver driver;

    @Before
    public void setUp() throws Exception {

        System.out.println("Тест по селениум!");




        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");
        //driver = new FirefoxDriver();




        String url = "https://www.rgs.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(url);

    }

    @After
    public void close() {
        driver.quit();
    }

    public void fillForm(By locator, String value) {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(locator).sendKeys(value);


    }

    public static String todayPlusTwoWeeks() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, 14);
        String newDate = dateFormat.format(instance.getTime());
        return newDate;
    }

    public void clicByXpath(String xPath) {
        driver.findElement(By.xpath(xPath)).click();

    }

    public void clicByName(String name) {
        driver.findElement(By.name(name)).click();

    }

    public String getTextByXpath(String xPath) {
        return driver.findElement(By.xpath(xPath)).getText();

    }

    public void scrollToObjectByXPath(String xPath) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(xPath)));
    }

    public void compareText(By locator, String expect) {
       Assert.assertTrue("Искомого текста НЕТ: " + expect + ". Вместо него: " + driver.findElement(locator).getText(), driver.findElement(locator).getText().contains(expect));
        System.out.println("Искомый текст ЕСТЬ: " + expect);
    }
}
