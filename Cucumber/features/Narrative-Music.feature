Feature:	The use case 'Music' is in charge of the playback of music.

As			admin
I want		to play, pause, stop and forward music
so that		the music played changes accordingly.

Scenario: The admin is logged in

	Given 	The admin is logged in


Scenario: The admin starts the music

	Given 	The user is on the dashboard page of Cloud

	When 	The admin clicks the play button
	Then 	The Raspberry Pi will start the playback of the music
	
Scenario: The admin pauses the music

	Given 	The user is on the dashboard page of Cloud

	When 	The admin clicks the pause button
	Then 	The Raspberry Pi will pause the playback of the music

Scenario: The admin forwards the music

	Given 	The user is on the dashboard page of Cloud

	When 	The admin clicks the forward button
	Then 	The Raspberry Pi will play the next song
	
Scenario Outline: The admin selects another volume

	Given 	The user is on the dashboard page of Cloud

	When 	The admin enters a new value <volume> for the volume
	Then 	The Raspberry Pi will adjust it's volume accordingly to the volume <volume>
	
	Examples:
	| volume	|
	| 56		|
	| 21		|
	| 89		|