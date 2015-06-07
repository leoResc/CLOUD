require "rubygems"
require "selenium-webdriver"
require "rspec/expectations"
#require "headless"
require "rspec"
require "././lib/seleniumUtilities"
require "././lib/login"
require "././lib/event"
require "././lib/song"

# before all
#headless = Headless.new
#headless.start
selenium_driver = Selenium::WebDriver.for :firefox

$localTesting = true

$seleniumUtilities = SeleniumUtilities.new(selenium_driver)
$login = Login.new(selenium_driver)
$event = Event.new(selenium_driver)
$song = Song.new(selenium_driver)

# after all
at_exit do
	sleep(1)
	selenium_driver.quit
#	headless.destroy
end