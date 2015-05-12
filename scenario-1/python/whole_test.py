import time

from selenium import webdriver
from selenium.webdriver import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By


class whole_test:
    def run_test(self):
        for n in range(120):
            start = time.time()
            driver = webdriver.Chrome(service_args=["--verbose", "--log-path=chromelog.log"])

            driver.get("http://mediawiki119.wikia.com")
            driver.maximize_window()
            elem = driver.find_element_by_id("searchInput")
            elem.send_keys("qa")
            elem.send_keys(Keys.RETURN)

            WebDriverWait(driver, 20).until(
                EC.presence_of_element_located((By.XPATH, "//iframe[@title='VisualDNA Analytics']")))
            driver.find_element_by_css_selector("article h1 a").click()
            WebDriverWait(driver, 10).until(EC.title_is("QA - Mediawiki 1.19 test Wiki"))

            text = "ASDASD" + str(n)

            # driver.get(driver.current_url + "?action=edit")
            WebDriverWait(driver, 20).until(
                EC.presence_of_element_located((By.XPATH, "//iframe[@title='VisualDNA Analytics']")))

            contribute_button = driver.find_element(By.CSS_SELECTOR, "nav.contribute")

            ActionChains(driver).click(contribute_button).move_to_element(
                contribute_button.find_element(By.CSS_SELECTOR, "li a[data-id='edit']")).click().perform()

            WebDriverWait(driver, 20).until(
                EC.visibility_of_element_located((By.CSS_SELECTOR, "div.cke_contents iframe")))
            driver.switch_to.frame(driver.find_element_by_css_selector("div.cke_contents iframe"))
            driver.find_element_by_tag_name("body").clear()
            driver.find_element_by_tag_name("body").send_keys(text)
            driver.switch_to.default_content()
            driver.find_element_by_css_selector("input#wpSave").click()

            WebDriverWait(driver, 10).until(EC.title_is("QA - Mediawiki 1.19 test Wiki"))

            assert text in driver.find_element_by_css_selector("#WikiaArticle p").text
            driver.quit()
            print(time.time() - start)

if __name__ == "__main__":
    whole_test().run_test()