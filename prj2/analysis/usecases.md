### Log in
| **Name:**          | Log In                                                                                            |
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Sales Officer, Sales Manager, Account Manager, Administrator                                                                                               |
| **Description:**   | Actor logs into their account                                                                     |
| **Pre-Condition:** | Actor has an account                                          |
| **Scenario:**      | 1. Actor fills in account details.<br>2. System confirms the details are correct and lets actor into their account |
| **Result:**        | Actor is logged in                                                                                 |
| **Extentions:**    | 2a System displays that details are incorrect. Return to step 1    


### Log out
| **Name:**          | Log out                                                                                            |
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Sales Officer, Sales Manager, Account Manager, Administrator                                                                                               |
| **Description:**   | Actor logs out of their account                                                                     |
| **Pre-Condition:** | Actor has an account                                          |
| **Scenario:**      | 1. Actor chooses the option "Log out".<br>2. System brings actor back to log in. |
| **Result:**        | Actor is logged out                                                                                 |
| **Extentions:**    | none 


## Sales Officer

### Discard Booking
| **Name:**          | Discard Booking                                                                                            |
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Sales Officer                                                                                              |
| **Description:**   | Actor discards current booking                                                                     |
| **Pre-Condition:** | Actor is creating a booking & customer wants to cancel it                                          |
| **Scenario:**      | 1. Actor cancels the booking.<br>2. The System takes the user back to the select flight menu. |
| **Result:**        | The booking is cancelled                                                                                   |
| **Extentions:**    | none                                                                    

### Select seat
| **Name:** | Select Seat 
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Actor:** | Sales Officer 
| **Description:** | Actor selects a seating option on behalf of the customer.
| **Pre-Condition:** | Actor is creating a booking.
| **Scenario:** | 1. System asks for the customer's preferred seat option. <br> 2. Actor chooses to select the seat themselves. <br> 3. System acknowledges Actors choice  <br> 4. System asks Actor to accept the that was selected. <br> 5. Actor accepts. <br> 6. System reserves seats.
| **Result:** | Selected Seat is added to the reservation.
| **Extentions:** | 2a. Actor chooses a free seat at random. <br>  1. Return to step 3. <br> 5a. Actor declines the accept. 

### Select extra options
| **Name:** | Select extra options
| ------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Actor:** | Sales Officer
| **Description:** |Actor wants to select paid options
| **Pre-Condition:** | Actor is creating a booking.
| **Scenario:** | 1. Actor navigates to the paid options menu <br> 2. The system displays the menu <br> 3. Actor selects the desirable options <br> 4. The system displays the different options <br> 5. The Actor chooses a specific option/options in according with the customer’s needs <br> 6. The system validates the paid options and modifies the final price accordingly <br> 7. Actor moves on to next step
| **Result:** | Paid options were selected
| **Extentions:** | 3a. Actor selects no desirable options <br> 1. Customer wants to continue with no extra options <br> 2. return to step 7


### Create a booking
| **Name:**          | Create a booking       |  
| ---- | --------|
| **Actor:**         | Sale Officer         |                                                  
| **Description:**   | Actor creates a booking.|                                    
| **Pre-Condition:** | Customer wants to book a trip.  |
| **Scenario:**      | 1. System asks for departure and arrival airport, and departure time. <br> 2. Actor fills in the asked data. <br> 3. System displays trips for given data. <br> 4. Actor chooses a trip. <br> 5. System asks for details of passengers <br> 6. Actor fills in details of passengers <br> 7. System asks to select customer <br> 8. Actor selects customer<br>  9. System for transaction ID of the payment <br> 10. Actor fills in transaction ID and confirms booking <br> 11. System creates a booking.   |
| **Result:**        | A booking is created.
|**Extentions:**    |  |

### Passenger details
| **Name:**          | Create new Passenger       |  
| ---- | --------|
| **Actor:**         | Sale Officer         |                                                  
| **Description:**   | Actor selects passenger details.|                                    
| **Pre-Condition:** | A flight as been selected  |
| **Scenario:**      | 1. Actor selects option to create a new passenger. <br> 2. System offers to fill in passenger details. <br> 3. Actor fills in passenger details. <br> 4. System saves passenger information. <br> 5. System displays list of passengers. <br> 6. Actor chosses one of the passengers. <br> 7. System aks to fill in the passport number and select a seat. <br> 8. Actor fills in the passport number and chooses seat <br> 9. System offers to confirm booking.    |
| **Result:**        | A new passenger is created.
|**Extentions:**    | 1a. Actor selects option to add an existing passenger <br> 1. System displays list of existing passengers <br> 2. Actor chooses one of the passengers. <br> 3. Return to step 7. |


## Sales Manager

### Create & apply general discounts

| **Name:**          | Create & apply general discounts                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Actor:**         | Sales Manager                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **Description:**   | Actor creates and applies a general discount                                                                                                                                                                                                                                                                                                                                                                                                               |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                                                                                                                                                                                                                                                                                |
| **Scenario:**      | 1. Actor navigates to the discount management menu.<br>2. The system displays the menu.<br>3. Actor creates a new discount.<br>4. Actor a pop-up menu with the discount specification.<br>5. Actor sets the specifications.<br>6. The system validates the discount specifications to ensure they are within allowed limits.<br>7. The Actor applies the discount.<br>8. System closes menu and discount is set. |
| **Result:**        | A discount is set.                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Extensions:**    | none                                                                                                                                                                                                                                                                                                                                                                                                                                                               |



## Account Manager

### Look at KPIs


| **Name:**          | View KPIs                                                                                                                                                                                           |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Account Manager                                                                                                                                                                                     |
| **Description:**   | Actor wants to see the KPIs                                                                                                                                                               |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                    |
| **Scenario:**      | 1. Actor clicks on the KPI page.<br>2. The system redirects the user to the KPI page.<br>3. Actor chooses which KPI they want to check.<br>4. System displays the relevant KPI. |
| **Result:**        | Actor can view the KPIs                                                                                                                                                                   |
| **Extensions:**    | none                                                                                                                                                                                                |


## Administrator

### Create Accounts for Employees


| **Name:**          | Create Account                                                                                                                                                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Administrator                                                                                                                                                                                                                       |
| **Description:**   | Actor creates a new account for a new employee                                                                                                                                                                              |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                                                         |
| **Scenario:**      | 1. Actor clicks on the create account menu.<br>2. System redirects the user to the menu.<br>3. Actor inputs the first name, last name, username and password of the employee for the account creation.<br>4. The system saves the information to the database. |
| **Result:**        | A new account is created                                                                                                                                                                                                            |
| **Exceptions:**    | 4a.System doesn't save the account due to an exception occurring, and writes out a warning message.       

### Search Account for employee

| **Name:**          | Search Account                                                                                                  |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Administrator                                                                                                                                                                                                                       |
| **Description:**   | Actor searches for an account                                                                                                                                                                             |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                                                         |
| **Scenario:**      | 1. Actor chooses the "Search account" option. <br>2. System redirects the user to a page with a searchbar.<br>3. Actor inputs the name of the employee, whose account he/she is looking for.<br>4. The system shows the results of the search. |
| **Result:**        | A search for an account has been done.                                                                                                                                                                                                            |
| **Extensions:**    | none        


### Delete Account for employee

| **Name:**          | Delete Account                                                                                                         |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Administrator                                                                                                                                                                                                                       |
| **Description:**   | Actor deletes an account                                                                                                                                                                             |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                                                         |
| **Scenario:**      | 1. Actor chooses the "Search account" option. <br>2. System redirects the user to a page with a searchbar.<br>3. Actor inputs the name of the employee, whose account he/she is looking for.<br> 4. The system shows the results of the search. <br> 5.Actor chooses the account and clicks on edit.<br> 6.System shows the options for the specific account.<br> 8.Actor chooses "Delete".<br> 9.System deletes the account from the database.|
| **Result:**        | An account has been deleted.                                                                                                                                                                                                            |
| **Extensions:**    | 4a.System doesn't find the account, return to step 3.    

### Edit Account for employee

| **Name:**          | Edit Account                                                                                                         |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Actor:**         | Administrator                                                                                                                                                                                                                       |
| **Description:**   | Actor edits an account                                                                                                                                                                             |
| **Pre-Condition:** | Actor is logged in.                                                                                                                                                                                         |
| **Scenario:**      | 1. Actor chooses the "Search account" option. <br>2. System redirects the user to a page with a searchbar.<br>3. Actor inputs the name of the employee, whose account he/she is looking for.<br>4. The system shows the results of the search. <br>5.Actor chooses the account and right clicks on the options.<br>6.System shows the options for the specific account.<br>7.Actor chooses "Edit"<br>8.System provides actor with a page with all of the details of the account.<br>9.Actor edits the necessary details<br>10.System changes the chosen data in the database.|
| **Result:**        | An account has been edited.                                                                                                                                                                                                            |
| **Extensions:**    | 4a.System doesn't find the account, return to step 3.   


