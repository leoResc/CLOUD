require 'utilities'

class Login < Utilities
	
	def initialize(driver)
		@driver = driver
	end
	
	def createUser(user)
		navigate()
		click('.showLogin')
		typeIn('#inputUsername', user)
		typeIn('#inputPassword', 'test')
		click('.btn')
	end
	
end
