**General**
- Application for a travel agency to sell flight tickets
- The application won’t be used by customers of the travel agency, but by sales officers who make the bookings for their customers 

**Users of the application**
Employees can take on multiple roles
- Sales officer
  - Talk to customers to sell them bookings
  - Create trips by putting in the customer information and passenger information, selecting all the wanted options, selecting the wanted seat
  - Can apply special discounts
  - Decides on the minimum connection time
- Sales manager
  - Sets price per kilometer and general discounts and the timeframe when they are valid
- Account manager
  - Can look into KPI’s 
  - Reports to the CEO
- Administrator
  - Can create accounts for employees, they can’t register their account themselves, only log into it

**Trips/flights/bookings and tickets**
- Trips
  - Either one way or round
  - Can exist of multiple connecting flights
  - There is no such thing in place as restrictions on which airplane companies have trips with only certain other airplane companies
  - A round trip can only be booked if all the data is available. The return trip gets fully booked at the same time as the outbound trip.
  - A trip has a single ticket per passenger per flight
  - The minimum connection time is determined by the agency
- Flight
  - There are 2 available classes, business and economy (Make this preferably flexible for future)
- Booking and tickets
  - A booking is done by one customer, through the sales officer
  - A booking can have multiple tickets, as it can be done for multiple passengers at once (for example 1 person booking for their family)
  - A ticket is for a single seat on every single flight of the trip
  - Bookings can't be modified, they can only be cancelled
  - A round trip is 2 tickets but are bought in the same booking

**Seats**
- One passenger can only have 1 seat per flight
- Seats get reserved while the booking is being done, the first customer to reserve the seat gets the opportunity to buy the seat
- If the seat stays reserved but not bought until 4am, the reservation gets reset and the seat becomes available again. If another seat or flight is selected the seat also becomes available again.
- If possible, implement the seat choosing in a graph, but not necessarily

**Searching and displaying information**
- Searching
  - Departure airport
  - Arrival airport
  - One way or round trip
  - Date of departure
  - Date of departure for return trips
- Displaying after search
  - All flights from departure airport to arrival airport on departure date
  - Information displayed per option:
    - Price
    - Travel time
    - Amount of stops
    - Time of departure in local time of the airport
    - Time of arrival in local time of the airport
  - Display the cheapest and fastest trips
  - After picking departure flight, show a similar screen but for the return trip

**Pricing and discounts**
- Pricing
  - Base price is calculated through price per kilometres x kilometres , then added to that, the price for additional options 
  - The price per kilometer can be set by the sales manager
- Discounts
  - The sales officer can apply special discounts (i.e discount when booking x amount of tickets)
  - The sales manager can set general discounts (i.e early bird and last minute discounts or general discounts, and set a timeframe for the discounts)
  - Discounts cant be more than a 100%, and not less than 0%
  - When a discount is more than 50%, the system should give a warning






**Additional options**
- Not different per class and included in the final price of the booking
- Luggage
- Food
  - Option for normal, vegetarian or vegan
  - (If possible, add ability to add other options in the system by for example the sales manager)

**Client information**
- Name
- Birthdate
- ClientID

**Customer information stored in booking**
  - Address
  - Email address
  - If payment has been made

**Passenger information stored in ticket**
- Passport document number
- BookingID from the booking that their ticket belongs to

**KPIs (key performance indicators)**
- Can be viewed by account managers and administrators on the management dashboard
- Useful metrics that the agency would want to be able to see:
  - Money earned
  - Amount of tickets sold 
  - Kilometres travelled
  - The use of a plane in percentage
  - Amount of tickets sold in each class
  - Statistics on all options sold
  (Additional metrics can be added if found useful)

**Additional information**
- The gui should have a professional and intuitive design, and should be able to be used both with use of mouse and without
- The gui has no special branding requirements
- Information about planes, airports and flights don’t have to be put in by hand




**Interview 1 questions**
- What are sun hours mentioned in the case description?
- What do you expect of our application?
- How do you define a “trip” and how do you define a “booking”?
- How is the price for the tickets calculated?
- What are the available classes? (Economy, premium, business...)
- What paid options are available?
- The option for choosing your seats, how should that be implemented? (a graph or…?)
- Discount cap-is there a maximum for it?
- How and by whom are the discounts controlled and made?



**Interview 2 questions**
- How exactly do discounts work?
- How is the picking of an arrival/destination airport/city/country envisioned? 
- How are classes incorporated in searching, before searching, or displayed as available option after searching?
- Is there still only one ticket if its a round trip, or would this be 2
- Should there be a filtering option, or should it only display whats fastest and cheapest and only that?
- How is the minimum connection time envisioned? You said it was decided by the agency, who in the agency? The sales manager or someone else? Should this be able to be set through an option in the application? 
- How do the classes come into play regarding the price?
- Should we implement a way for the sales manager to reset all the seat reservations by hand? 
- Is it completely optional to offer different types of food, or are normal, vegetarian and vegan mandatory?
- Is legroom included in the class or should there be an additional option?
- Do we have to send confirmation of the booking to the customer?
- Do we have to save payment info?
- How do we use the dataset provided? Do we have to implement a way to register flights/set the time for this? Would a separate user group be tasked with this?
