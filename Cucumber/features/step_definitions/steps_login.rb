When(/^The user clicks on the sign in button$/) do
	$login.click(".showLogin")
	sleep(1)
end

When(/^The user types in the username (.+)$/) do |userName|
	$login.typeIn("#inputUsername", userName)
end

When(/^The user types in the password (.+)$/) do |password|
	$login.typeIn("#inputPassword", password)
end

When(/^The user clicks the sign in button$/) do
	$login.click(".btn")
end

And(/^The user (.+) exists already$/) do |user|
	$login.createUser(user, "test")
end

And(/^The user logs out$/) do
	$login.logout()
end

And(/^The test event exists$/) do
	$login.logInAsAdmin()
	$event.createTestEvent()
end