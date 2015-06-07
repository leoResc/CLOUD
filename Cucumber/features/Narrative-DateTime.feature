Feature: 	The Use Case 'Date Time' allows the admin to update the system time of the Raspberry Pi.

As 		admin
I want 	to update the system time
so that	events are activated at the right day.

Scenario: The admin is logged in

	Given 	The admin is logged in

	
Scenario: 	The admin updates the time.

	Given 	The user is on the dashboard page of Cloud

	When 	The admin clicks on the refresh icon
	Then 	The time will be updated
	And 	A check mark will appear 