Feature: 	The Use Case 'Song' allows the admin to upload new songs on the server, to view all existing songs and to delete them.

As 		admin
I want 	to upload and delete songs
so that	I can listen to songs.

Scenario: 	The admin uploads a mp3 file.

	Given 	The user is on the song page of Cloud

	When 	The admin selects the song Jingle Punks - Stale Mate for upload
	Then 	The button upload will enable
	And 	The admin clicks on the upload button
	And 	The admin waits the upload to finish
	Then 	The song will show up in the list of all uploaded songs

Scenario: 	The admin uploads a file which is no .mp3

	Given 	The user is on the song page of Cloud

	When 	The admin selects a song for upload which is not of type mp3
	Then 	The the button upload will remain disabled
	And 	The song won't show up in the list of all uploaded songs

Scenario: 	The admin deletes a song from the list of all songs

	Given 	The user is on the song page of Cloud

	And 	The admin already uploaded one song
	And 	The admin clicks on the bin icon of the song
	Then 	The song should disappear from the list containing all songs