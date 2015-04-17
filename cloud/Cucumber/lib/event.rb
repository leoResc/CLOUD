require "utilities"

class Event < Utilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def selectPlaylist(number)
		@driver.find_element(:xpath, "(//input[@type='checkbox'])[2]").click
	end
end