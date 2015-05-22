Feature: 	The Use Case 'Create Event' is the Use Case for the admin to create a new event.

As 			admin
I want 		to create an event
so that 	I can set and save settings for a new event

Scenario Outline: 	Admin types in all the information correctly

	Given 	The user is on the event page of Cloud

	When 	The admin types in the name <eventName>
	And 	The admin types in the password <password>
	And 	The admin selects in the begin <dateStart>
	And 	The admin selects in the end <dateEnd>
	And 	The admin selects the playlist <playlist>
	And 	The admin writes a description
	And 	The admin clicks on create event

	Then 	The admin will see the new event on the event page
	
	Examples:
	| eventName | password 	| dateStart  | dateEnd	  | playlist 	|
	| Test_001 	| test 		| 9.4.2015 | 11.4.2015 | 3			|
	| Test_002  | test		| 10.4.2015 | 12.4.2015 | 8			|

Scenario Outline: 	Admin sets an invalid ending date

	Given 	The user is on the event page of Cloud

	When 	The admin types in the name <eventName>
	And 	The admin types in the password <password>
	And 	The admin selects in the begin <dateStart>
	And 	The admin selects in the end <dateEnd>
	And 	The admin selects the playlist <playlist>
	And 	The admin writes a description
	And 	The admin clicks on create event

	Then 	The error message is displayed: Warning! The ending date is before the beginning date.
	
	Examples:
	| eventName | password 	| dateStart  | dateEnd	  | playlist	|
	| Test_003 	| test 		| 9.6.2015 | 8.6.2015 | 3			|