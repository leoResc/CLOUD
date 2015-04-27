require "rubygems"
require "selenium-webdriver"
require "rspec/expectations"
require "././lib/login"
require "././lib/event"
require "././lib/song"
require "rspec"

# before all
selenium_driver = Selenium::WebDriver.for :firefox
$utilities = Utilities.new(selenium_driver)
$login = Login.new(selenium_driver)
$event = Event.new(selenium_driver)
$song = Song.new(selenium_driver)

# after all
at_exit do
	sleep(1)
	selenium_driver.quit
end