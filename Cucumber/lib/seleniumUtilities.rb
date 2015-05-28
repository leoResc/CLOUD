class SeleniumUtilities

	def initialize(driver)
		@driver = driver
		@admin = false
	end

	def navigate(page)
		if $localTesting
			case page
			when "login"
				@driver.navigate.to "localhost:9000/login"
			when "main"
				@driver.navigate.to "localhost:9000"
			when "event"
				$login.logInAsAdmin()
				@driver.navigate.to "localhost:9000/events"
			when "song"
				$login.logInAsAdmin()
				@driver.navigate.to "localhost:9000/songs"
			end		
		else
			case page
			when "login"
				@driver.navigate.to "http://cloud.licua.de/login"
			when "main"
				@driver.navigate.to "http://cloud.licua.de"
			when "event"
				$login.logInAsAdmin()
				@driver.navigate.to "http://cloud.licua.de/events"
			when "song"
				$login.logInAsAdmin()
				@driver.navigate.to "http://cloud.licua.de/songs"
			end		
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
		checkbox = @driver.find_element(:css, "input.checkbox:nth-of-type(#{item})")
		unless checkbox.attribute("checked") == ""
			checkbox.click
		end
	end

	def assertTitle(title)
		case title
		when "login"
			(@driver.title).should == "Welcome to Cloud"
		when "main"
			(@driver.title).should == "CLOUD"
		when "event"
			(@driver.title).should == "Dashboard - Events"
		end
	end
	
	def assertNotification(item, message)
		(@driver.find_element(:css, item).text).should == message
	end
	
	def logout()
		navigate("main")
		@driver.find_element(:link, "Logout").click
	end

end