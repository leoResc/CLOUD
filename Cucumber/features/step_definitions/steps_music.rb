When(/^The admin clicks the play button$/) do
  $seleniumUtilities.click("#play2")
end

Then(/^The Raspberry Pi will start the playback of the music$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

When(/^The admin clicks the pause button$/) do
  $seleniumUtilities.click("#pause2")	
end

Then(/^The Raspberry Pi will pause the playback of the music$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

When(/^The admin clicks the forward button$/) do
  $seleniumUtilities.click("#forward2")
end

Then(/^The Raspberry Pi will play the next song$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

When(/^The admin enters a new value (\d+) for the volume$/) do |volume|
  puts volume
  $seleniumUtilities.typeIn("input.knobdial", volume)
end

Then(/^The Raspberry Pi will adjust it's volume accordingly to the volume (\d+)$/) do |volume|
  pending # Write code here that turns the phrase above into concrete actions
end