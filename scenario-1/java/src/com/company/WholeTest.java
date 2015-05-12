package com.company;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WholeTest {

  public static void main(String args[]) {
    System.setProperty("webdriver.chrome.silentOutput", "true");
    System.setProperty("webdriver.chrome.verboseLogging", "true");
    System.setProperty("webdriver.chrome.logfile", "chromelog.log");

    for (int i = 0; i < 120; i++) {
      long start = new Date().getTime();

      WebDriver driver = new ChromeDriver(DesiredCapabilities.chrome());

      driver.get("http://www.mediawiki119.wikia.com");
      driver.manage().window().maximize();
      WebElement searchInput = driver.findElement(By.id("searchInput"));
      searchInput.sendKeys("qa");
      searchInput.findElement(By.id("searchInput")).sendKeys(Keys.RETURN);

      new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By
          .xpath("//iframe[@title='VisualDNA Analytics']")));
      driver.findElement(By.cssSelector("article h1 a")).click();

      new WebDriverWait(driver, 10).until(ExpectedConditions
          .titleIs("QA - Mediawiki 1.19 test Wiki"));

      String text = "ASDASD" + i;

      WebElement contributeButton = driver.findElement(By.cssSelector("nav.contribute"));

      new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By
          .xpath("//iframe[@title='VisualDNA Analytics']")));

      new Actions(driver).click(contributeButton)
          .moveToElement(contributeButton.findElement(By.cssSelector("li a[data-id='edit']")))
          .click().perform();

      new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By
          .cssSelector("div.cke_contents iframe")));
      driver.switchTo().frame(driver.findElement(By.cssSelector("div.cke_contents iframe")));
      driver.findElement(By.tagName("body")).clear();
      driver.findElement(By.tagName("body")).sendKeys(text);
      driver.switchTo().defaultContent();
      driver.findElement(By.cssSelector("input#wpSave")).click();

      new WebDriverWait(driver, 10).until(ExpectedConditions
          .titleIs("QA - Mediawiki 1.19 test Wiki"));

      assert driver.findElement(By.cssSelector("#WikiaArticle p")).getText().equals(text);

      driver.quit();
      System.out.println((new Date().getTime() - start) / 1000f);
    }
  }
}
