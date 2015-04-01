class Utilities

	def initialize(driver)
		@driver = driver
	end

	def navigate(page)
		case page
		when 'login'
			@driver.navigate.to 'http://cloud.licua.de/login'
		when 'event'
			@driver.navigate.to 'http://philenius.de/dashboard/event.html'
		when 'main'
			@driver.navigate.to 'http://philenius.de/dashboard/index.html'
	end		
	end
	
	def typeIn(item, value)
		@driver.find_element(:css, item).clear
		@driver.find_element(:css, item).send_keys value
	end
	
	def click(item)
		@driver.find_element(:css, item).click
	end
	
	def checkbox(item)
		@driver.find_element(:xpath, "(//input[@type='checkbox'])[2]").click
	end

	def assertTitle(title)
		case title
		when 'login'
			(@driver.title).should == "Welcome to Cloud"
		when 'main'
			(@driver.title).should == "Cloud"
		when 'event'
			(@driver.title).should == "Dashboard - Events"
		end
	end
	
	def assertNotification(item, message)
		(@driver.find_element(:css, item).text).should == message
	end
	
	def logout()
		@driver.navigate.to 'http://cloud.licua.de'
		@driver.find_element(:link, "Logout").click
	end
	
end