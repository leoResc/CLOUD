class SeleniumUtilities

	def initialize(driver)
		@driver = driver
		@admin = false
		@url = ""
	end

	def navigate(page)
		if $localTesting
			@url = "localhost:9000"
		else
			@url = "cloud.licua.de"
		end
		
		case page
		when "login"
			@url << "/login"
			@driver.navigate.to @url
		when "main"
			@driver.navigate.to @url
		when "dashboard"
			if (@driver.title != "Dashboard - Overview")
				navigate("main")
				@driver.find_element(:css, "button.dropdown").click
				sleep(1)
				@driver.find_element(:link, "Dashboard").click
			end
		when "event"
			if (@driver.title != "Dashboard - Events")
				navigate("dashboard")
			end
			@driver.find_element(:link, "Events").click
		when "song"
			if (@driver.title != "Dashboard - Songs")
				navigate("dashboard")
			end
			@driver.find_element(:link, "Songs").click
		when "playlist"
			if (@driver.title != "Dashboard - Playlists")
				navigate("dashboard")
			end
			@driver.find_element(:link, "Playlists").click
		when "user"
			if (@driver.title != "Dashboard - User")
				navigate("dashboard")
			end
			@driver.find_element(:link, "User").click
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
	
	def assertNotification(message)
		(@driver.find_element(:css, "div.toast-message").text).should == message
	end
	
	def isElementPresent(how, what)
		@driver.find_element(how, what)
		true
		rescue Selenium::WebDriver::Error::NoSuchElementError
		false
	end

end