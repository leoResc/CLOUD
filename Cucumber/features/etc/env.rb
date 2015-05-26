require "rubygems"
require "selenium-webdriver"
require "rspec/expectations"
require "././lib/login"
require "././lib/event"
require "././lib/song"
require "rspec"

# before all
selenium_driver = Selenium::WebDriver.for :remote, url: 'http://localhost:8001'
$localTesting = true
$utilities = Utilities.new(selenium_driver)
$login = Login.new(selenium_driver)
$event = Event.new(selenium_driver)
$song = Song.new(selenium_driver)
$adminIsLoggedIn = false

# after all
at_exit do
	sleep(1)
	selenium_driver.quit
end