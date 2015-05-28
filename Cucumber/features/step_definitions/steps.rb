Given(/^The user is on the (.+) page of Cloud$/) do |page|
	$seleniumUtilities.navigate(page)
end

Then(/^The error message is displayed: (.+)$/) do |message|
	$seleniumUtilities.assertNotification(".alert-danger", message)
end

Then(/^The user will see the (.+) page of Cloud$/) do |title|
	$seleniumUtilities.assertTitle(title)
end