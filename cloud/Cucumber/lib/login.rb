require "utilities"

class Login < Utilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def createUser(user)
		navigate("login")
		click(".showLogin")
		typeIn("#inputUsername", user)
		typeIn("#inputPassword", "test")
		click(".btn")
		logout()
	end
	
end
