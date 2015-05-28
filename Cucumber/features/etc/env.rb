require "rubygems"
require "selenium-webdriver"
require "rspec/expectations"
#require "headless"
require "rspec"
require "/home/phil/CLOUD/Cucumber/lib/seleniumUtilities"
require "/home/phil/CLOUD/Cucumber/lib/login"
require "/home/phil/CLOUD/Cucumber/lib/event"
require "/home/phil/CLOUD/Cucumber/lib/song"

# before all
#headless = Headless.new
#headless.start
selenium_driver = Selenium::WebDriver.for :firefox

$localTesting = false

$seleniumUtilities = SeleniumUtilities.new(selenium_driver)
$login = Login.new(selenium_driver)
$event = Event.new(selenium_driver)
$song = Song.new(selenium_driver)
$adminIsLoggedIn = false

# after all
at_exit do
	sleep(1)
	selenium_driver.quit
#	headless.destroy
end