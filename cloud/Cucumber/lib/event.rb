require "utilities"

class Event < Utilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def selectPlaylist(number)
		@driver.find_element(:xpath, "(//input[@type='checkbox'])[2]").click
	end
	
	def createTestEvent()
		$login.logInAsAdmin()
		$utilities.navigate("event")
		$utilities.typeIn("#name", "Test Event")
		$utilities.typeIn("#password", "test")
		element = @driver.find_element(:id, "begin").click
		sleep(1)
		@driver.find_element(:css, "div.dd_submit").click
		@driver.find_element(:id, "end").click
		sleep(1)
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_sw_.dd_d_ > a.dd_nav_.dd_next_").click
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_submit").click
		@driver.find_element(:xpath, "//button[@type='submit']").click
	end
	
end  