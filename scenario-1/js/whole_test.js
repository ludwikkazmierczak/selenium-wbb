var webdriver = require('selenium-webdriver');

function wholeTest() {
    start = new Date().getTime();

    var driver = new webdriver.Builder().
        withCapabilities(webdriver.Capabilities.chrome()).
        build();

    driver.get('http://mediawiki119.wikia.com');
    driver.manage().window().maximize()
    var element = driver.findElement(webdriver.By.id("searchInput"))

    element.sendKeys("qa")
    element.sendKeys(webdriver.Key.RETURN)

    driver.wait(function (num) {
        return driver.isElementPresent(webdriver.By.xpath("//iframe[@title='VisualDNA Analytics']"));
    }, 20000);

    driver.findElement(webdriver.By.css("article h1 a")).click();

    driver.wait(function () {
        return driver.getTitle().then(function (title) {
            return title == "QA - Mediawiki 1.19 test Wiki";
        });
    }, 10000);

    driver.wait(function () {
        return driver.isElementPresent(webdriver.By.xpath("//iframe[@title='VisualDNA Analytics']"));
    }, 20000);

    var text = "ASDASD" + num;

    driver.findElement(webdriver.By.css("nav.contribute")).then(function (elem) {
        driver.actions().mouseMove(elem).mouseDown().mouseUp()
            .perform();
    });

    driver.findElement(webdriver.By.css("nav li a[data-id='edit']")).click();

    driver.wait(function () {
        return driver.isElementPresent(webdriver.By.css("div.cke_contents iframe"));
    }, 20000);

    driver.switchTo(driver.findElement(webdriver.By.css("div.cke_contents iframe")));
    driver.findElement(webdriver.By.css("body")).then(function (elem) {
        driver.actions().doubleClick(elem).sendKeys(webdriver.Key.BACK_SPACE)
            .sendKeys(text).perform();
    });
    driver.switchTo().defaultContent();
    driver.findElement(webdriver.By.css("input#wpSave")).click();

    driver.wait(function () {
        return driver.getTitle().then(function (title) {
            return title == "QA - Mediawiki 1.19 test Wiki";
        });
    }, 10000);

    driver.wait(function () {
        return driver.findElement(webdriver.By.css("#WikiaArticle p")).getText().then(function (pText) {
            return pText == text;
        });
    }, 5000);

    driver.quit().then(function () {
        console.log((new Date().getTime() - start) / 1000);
    });
}

var flow = webdriver.promise.controlFlow();

var num = 0;

for (var i = 0; i < 120; ++i) {
    flow.execute(wholeTest).then(function () {
        num += 1;
    })
}