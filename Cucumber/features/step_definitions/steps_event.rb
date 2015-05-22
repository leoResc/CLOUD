When(/^The admin types in the name (.+)$/) do |name|
	$event.typeIn("#name", name)
end

When(/^The admin types in the password (.+)$/) do |password|
	$event.typeIn("#password", password)
end

When(/^The admin selects the playlist (\d+)$/) do |playlist|
	$event.selectPlaylist(playlist)
end

When(/^The admin writes a description$/) do
	$event.typeIn("#description", "Lorem ipsum dolor sit amet.")
end

When(/^The admin clicks on create event$/) do
	$event.click(".btn.btn-green")
end

Then(/^The admin will see the new event on the event page$/) do
  pending # Write code here that turns the phrase above into concrete actions
end