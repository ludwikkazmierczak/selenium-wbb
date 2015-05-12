require "selenium-webdriver"
require 'time'

for i in 0..119
  start = Time.now.to_f
  driver = Selenium::WebDriver.for :chrome, :service_log_path => "chromelog.log"
  driver.navigate.to "http://mediawiki119.wikia.com"
  driver.manage.window.maximize

  element = driver.find_element(:id => 'searchInput')
  element.send_keys 'qa'
  element.send_keys :return

  Selenium::WebDriver::Wait.new(:timeout => 20)
      .until { driver.find_element(:xpath => "//iframe[@title='VisualDNA Analytics']") }

  driver.find_element(:css => "article h1 a").click

  Selenium::WebDriver::Wait.new(:timeout => 10)
      .until { driver.title == "QA - Mediawiki 1.19 test Wiki" }

  Selenium::WebDriver::Wait.new(:timeout => 20)
      .until { driver.find_element(:xpath => "//iframe[@title='VisualDNA Analytics']") }

  text = "ASDASD#{i}"

  contribute_button = driver.find_element(:css => "nav.contribute")

  driver.action
      .click(contribute_button)
      .move_to(contribute_button.find_element(:css => "li a[data-id='edit']"))
      .click
      .perform

  Selenium::WebDriver::Wait.new(:timeout => 20)
      .until { driver.find_element(:css => "div.cke_contents iframe") }

  driver.switch_to.frame driver.find_element(:css => "div.cke_contents iframe")
  driver.find_element(:tag_name => "body").clear
  driver.find_element(:tag_name => "body").send_keys text
  driver.switch_to.default_content

  driver.find_element(:css => "input#wpSave").click

  Selenium::WebDriver::Wait.new(:timeout => 10)
      .until { driver.title == "QA - Mediawiki 1.19 test Wiki" }

  driver.find_element(:css => "#WikiaArticle p").text == text

  driver.quit
  puts Time.now.to_f - start
end