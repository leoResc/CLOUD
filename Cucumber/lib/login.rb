require "/home/phil/CLOUD/Cucumber/lib/seleniumUtilities"

class Login < SeleniumUtilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def createUser(user, password)
		navigate("login")
		click(".showLogin")
		typeIn("#inputUsername", user)
		typeIn("#inputPassword", password)
		click(".btn")
		logout()
	end
	
	def logInAsAdmin()
		if !$adminIsLoggedIn
			navigate("login")
			click(".showLogin")
			typeIn("#inputUsername", "admin")
			typeIn("#inputPassword", "ozeanien")
			click(".btn")
			$adminIsLoggedIn = true
		end
	end
	
end
