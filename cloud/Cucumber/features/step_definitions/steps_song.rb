When(/^The admin selects the song (.+) for upload$/) do |song|
	$song.uploadSong(song)
end

Then(/^The button upload will enable$/) do
	$song.buttonEnabled()
end

Then(/^The admin clicks on the upload button$/) do
	$utilities.click("#upload")
end

Then(/^The admin waits the upload to finish$/) do
	$song.waitForUpload()
end

Then(/^The song will show up in the list of all uploaded songs$/) do
	$song.assertSongIsUploaded()
end

When(/^The admin selects a song for upload which is not of type mp(\d+)$/) do |arg1|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^The the button upload will remain disabled$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^The song won't show up in the list of all uploaded songs$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

Given(/^The admin already uploaded one song$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

Given(/^The admin clicks on the bin icon of the song$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^The song should disappear from the list containing all songs$/) do
  pending # Write code here that turns the phrase above into concrete actions
end