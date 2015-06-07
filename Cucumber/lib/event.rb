require "seleniumUtilities"

class Event < SeleniumUtilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def selectPlaylist(number)
		@driver.find_element(:xpath, "(//input[@type='checkbox'])[2]").click
	end
	
	def createTestEvent()
		# delete all user
		navigate("user")
		click("button.btn.btn-red")
		# upload song
		# not possible because of dropzonejs
		
		# create playlist
		# not working because of drag and drop
	
		navigate("event")
		deleteAllEvents()
		# create event
		typeIn("#name", "Test Event")
		typeIn("#password", "test")
		#begin
		@driver.find_element(:id, "begin").click
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_submit").click
		#end
		@driver.find_element(:id, "end").click
		elements = @driver.find_elements(:css, "a.nextDay")
		elements[1].click
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_submit").click
			
		@driver.find_element(:id, "description").send_keys "This is a test event for cucumber"
		@driver.find_element(:xpath, "//button[@type='submit']").click
		$login.logout()
	end
	
	def selectBegin()
		@driver.find_element(:id, "begin").click
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_submit").click	
	end
	
	def selectEnd()
		@driver.find_element(:id, "end").click
		elements = @driver.find_elements(:css, "a.nextDay")
		elements[1].click
		@driver.find_element(:css, "div.dd_.dd_fadein > div.dd_submit").click
	end
	
	def deleteAllEvents()
		loop do
			elements = @driver.find_elements(:css, "span.glyphicon.glyphicon-trash")
			break if elements[0].nil?
			elements[0].click
		end
	end
	
end  