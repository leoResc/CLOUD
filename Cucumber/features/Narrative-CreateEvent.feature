Feature: 	The Use Case 'Create Event' is the Use Case for the admin to create a new event.

As 			admin
I want 		to create an event
so that 	I can set and save settings for a new event

Scenario: The admin is logged in

	Given 	The admin is logged in
	And 	The admin deletes all old events


Scenario Outline: 	Admin types in all the information correctly

	Given 	The user is on the event page of Cloud

	When 	The admin types in the name <eventName>
	And 	The admin types in the password <password>
	And 	The admin selects the begin
	And 	The admin selects the end
	And 	The admin selects a playlist
	And 	The admin writes a description
	And 	The admin clicks on create event

	Then 	The admin will see the new event on the event page
	
	Examples:
	| eventName | password 	|
	| Test_001 	| test1 	|
	| Test_002  | test2		|

	
Scenario Outline: 	Admin forgets to select a beginning and ending date

	Given 	The user is on the event page of Cloud

	When 	The admin types in the name <eventName>
	And 	The admin types in the password <password>
	And 	The admin selects no begin
	And 	The admin selects no end
	And 	The admin selects a playlist
	And 	The admin writes a description
	And 	The admin clicks on create event

	Then 	The error message is displayed: You didn't select any begin or end for the event ...
	
	Examples:
	| eventName | password 	|
	| Test_003 	| test3 	|