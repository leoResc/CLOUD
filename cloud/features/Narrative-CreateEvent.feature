Feature: 	The Use Case 'Create Event' is the Use Case for an admin to create a new event.

As 			an admin
I want 		to create an event
so that 	I can set and save settings for a new event


Scenario: 	Admin types in all the information correctly

Given 		Admin clicks on the button 'New Event' on the admin-site

	When 	admin types in a valid event name
	And 	admin types in a valid event password
	And 	admin sets a valid beginning date
	And 	admin sets a valid ending date
	And 	admin chooses valid number of playlists
	And 	admin hits the submit button 'ON'

	Then 	A new event gets created
	And 	All the data is stored in our database


Scenario: 	Admin types in an invalid event name

Given 		Admin clicks on the button 'New Event' on the admin-site

	When 	admin types in an invalid event name
	And 	admin types in a valid event password
	And 	admin sets a valid beginning date
	And 	admin sets a valid ending date
	And 	admin chooses valid number of playlists
	And 	admin hits the submit button 'ON'

	Then 	An error message gets displayed to the admin that the event name already exists or empty field
	And 	Nothing is stored in our database And No event gets created


Scenario: 	Admin sets an invalid beginning date

Given 		Admin clicks on the button 'New Event' on the admin-site

	When 	admin types in a valid event name
	And 	admin types in a valid event password
	And 	admin sets an invalid beginning date
	And 	admin sets a valid ending date
	And 	admin chooses valid number of playlists
	And 	admin hits the submit button 'ON'

	Then 	An error message gets displayed to the admin that the beginning date is incorrect
	And 	Nothing is stored in our database And No event gets created


Scenario: 	Admin sets an invalid ending date

Given 		Admin clicks on the button 'New Event' on the admin-site

	When 	admin types in a valid event name
	And 	admin types in a valid event password
	And 	admin sets a valid beginning date
	And 	admin sets an invalid ending date
	And 	admin chooses valid number of playlists
	And 	admin hits the submit button 'ON'

	Then 	An error message gets displayed to the admin that the ending date is incorrect
	And 	Nothing is stored in our database And No event gets created


Scenario: 	Admin chooses an invalid number of playlists

Given 		Admin clicks on the button 'New Event' on the admin-site

	When 	admin types in a valid event name
	And 	admin types in a valid event password
	And 	admin sets a valid beginning date
	And 	admin sets a valid ending date
	And 	admin chooses invalid number of playlists
	And 	admin hits the submit button 'ON'

	Then 	An error message gets displayed to the admin that he has to choose at least one playlist
	And 	Nothing is stored in our database And No event gets created