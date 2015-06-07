When(/^The admin types in the name (.+)$/) do |name|
	$event.typeIn("#name", name)
end

When(/^The admin types in the password (.+)$/) do |password|
	$event.typeIn("#password", password)
end

When(/^The admin writes a description$/) do
	$event.typeIn("#description", "Lorem ipsum dolor sit amet. Dolem nado ego tunem direl.")
end

When(/^The admin clicks on create event$/) do
	$event.click(".btn.btn-green")
end

Then(/^The admin will see the new event on the event page$/) do
end

And(/^The admin selects the begin$/) do
	$event.selectBegin()
end

And(/^The admin selects the end$/) do
	$event.selectEnd()
end

When(/^The admin selects a playlist$/) do
end

And(/^The admin selects no begin$/) do
end

And(/^The admin selects no end$/) do
end

And(/^The admin deletes all old events$/) do
	$event.navigate("event")
	$event.deleteAllEvents()
end