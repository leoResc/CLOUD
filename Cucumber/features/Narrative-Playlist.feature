Feature: 	The Use Case 'Playlist' allows the admin to create a playlist on the server containing one or more songs.

As 		admin
I want 	to create a playlist
so that	I can listen to those songs and casual user can vote for them.

Scenario: The admin is logged in

	Given 	The admin is logged in

	
Scenario: 	The admin creates a playlist.

	Given 	The user is on the playlist page of Cloud

	When 	The admin selects the song Jingle Punks - Stale Mate for upload
	Then 	The button upload will enable
	And 	The admin clicks on the upload button
	And 	The admin waits the upload to finish
	Then 	The song will show up in the list of all uploaded songs