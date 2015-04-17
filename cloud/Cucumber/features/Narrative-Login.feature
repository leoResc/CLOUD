Feature: 	The Use Case 'login' is the first Use Case the user is confronted with. It is necessary to complete this Use Case to get access to the whole website.

As 			a user
I want 		to log in
so that 	I can see the playlist and vote for songs


Scenario Outline: 	Casual user types in all the information correctly

Given 		The user is on the login page of Cloud

	When 	The user clicks on the sign in button
	And 	The user types in the username <userName>
	And 	The user types in the password <password>
	And 	The user clicks the sign in button

	Then 	The user will see the main page of Cloud

Examples:
    | userName	| password	|
    | gordon	| test		|
    | lea		| test		|

	
Scenario Outline: 	Casual user types in invalid password

Given 		The user is on the login page of Cloud

	When 	The user clicks on the sign in button
	And 	The user types in the username <userName>
	And 	The user types in the password <invalidPassword>
	And 	The user clicks the sign in button

	Then 	The error message is displayed: Warning! Wrong password for current event.

Examples:
    | userName	| invalidPassword	|
    | jan		| 1234				|
    | gary		| ljasd				|
	
	
Scenario Outline: 	Casual user types in invalid nickname

Given 		The user is on the login page of Cloud
Given 		The user <invalidUserName> exists already

	When 	The user clicks on the sign in button
	And 	The user types in the username <invalidUserName>
	And 	The user types in the password <password>
	And 	The user clicks the sign in button

	Then 	The error message is displayed: Warning! User exists already.
	And 	The user will see the login page of Cloud
	
Examples:
    | invalidUserName	| password	|
    | leo				| test		|
	
Scenario Outline: 	The admin logs in

Given 		The user is on the login page of Cloud

	When 	The user clicks on the sign in button
	And 	The user types in the username <userName>
	And 	The user types in the password <password>
	And 	The user clicks the sign in button

	Then 	The user will see the main page of Cloud
	
Examples:
	| userName	| password	|
	| admin		| ozeanien	|