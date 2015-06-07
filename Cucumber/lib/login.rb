require "seleniumUtilities"

class Login < SeleniumUtilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def createUser(user, password)
		navigate("login")
		click(".showLogin")
		sleep(1)
		typeIn("#inputUsername", user)
		typeIn("#inputPassword", password)
		click(".btn")
		@driver.manage.delete_all_cookies
	end
	
	def logInAsAdmin()
		navigate("login")
		click(".showLogin")
		sleep(1)
		typeIn("#inputUsername", "admin")
		typeIn("#inputPassword", "ozeanien")
		click(".btn")
	end
	
	def logout()
		if @driver.title != "CLOUD"
			navigate("main")
		end
		@driver.find_element(:xpath, "//button[@type='button']").click
		sleep(1)
		@driver.find_element(:link, "Logout").click
		sleep(1)
	end
	
end
