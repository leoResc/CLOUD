Feature: 	The Use Case 'login' is the first Use Case the user is confronted with. It is necessary to complete this Use Case to get access to the whole website.

As 			a casual user (not admin)
I want 		to log in
so that 	I can see the playlist and vote for songs


Scenario: 	Casual user types in all the information correctly

Given 		Casual user clicks on the button 'login' or hits 'ENTER' on the login-site

	When 	Casual user types in a valid nickname
	And 	The user types in a valid (event) password
	And 	The user accepts our terms of use
	And 	The user hits the submit button

	Then 	A new (valid for the current event only) account gets created
	And 	All the data is stored in our database


Scenario: 	Casual user types in invalid password

Given 		Casual user clicks on the button 'login' or hits 'ENTER' on the login-site

	When 	Casual user types in a valid nickname
	And 	The user types in an invalid (event) password
	And 	The user accepts our terms of use
	And 	The user hits the submit button

	Then 	An error message gets displayed to the user that the nickname or password is invalid
	And 	Nothing is stored in our database
	And 	No account gets created


Scenario: 	Casual user types in invalid nickname

Given 		Casual user clicks on the button 'login' or hits 'ENTER' on the login-site

	When 	Casual user types in an invalid nickname
	And 	The user types in a valid (event) password
	And 	The user accepts our terms of use
	And 	The user hits the submit button

	Then 	An error message gets displayed to the user that the nickname already exists
	And 	Nothing is stored in our database
	And 	No account gets created


Scenario: 	Casual user doesn't accept terms of use

Given 		Casual user clicks on the button 'login' or hits 'ENTER' on the login-site

	When 	Casual user types in a valid nickname
	And 	The user types in an invalid (event) password
	And 	The user doesn't accept our terms of use
	And 	The user hits the submit button

	Then 	An error message gets displayed to the user that he has to accept terms of use
	And 	Nothing is stored in our database
	And 	No account gets created