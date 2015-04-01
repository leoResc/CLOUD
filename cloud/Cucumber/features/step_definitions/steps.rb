Given(/^The user is on the (.+) page of Cloud$/) do |page|
	$utilities.navigate(page)
end

Then(/^The error message is displayed: (.+)$/) do |message|
	$utilities.assertNotification('.alert-danger', message)
end

Then(/^The user will see the (.+) page of Cloud$/) do |title|
	$utilities.assertTitle(title)
end