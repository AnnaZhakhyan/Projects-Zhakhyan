### SOFTWARE REQUIREMENT SPECIFICATION
## 1. 	Introduction
 
The Software Requirement Specification provides a guide outlining the functions and requirements for the ticket selling application. The "Purpose" section identifies the purpose and the target audience for the SRS, along with the "Scope" section which identifies the software products produced and what they do. Briefly said, this introduction offers an overview of the SRS, including its purpose, scope, key definitions, references, and an overview of the application's functionalities.
 
1.1 Purpose
 
This subsection describes the purpose and the intended audience for the SRS.
 
This document provides a precise description of the requirements for the successful development of the booking  application. Clearly outlining these topics facilitates clear communication and understanding within the project, therefore leading to a successful delivery of the application to meet the customer's requirements.
The intended audience for the SRS is broad. It can include developers, stakeholders, designers, project managers and so on.
 
1.2 Scope
 
The software product to be produced is the booking application. This application will facilitate the purchase and management of airline tickets for customers. There are various functionalities that the application will have. Those include: allowing users to search for flights and make bookings, to view seat and ticket availability and so on.
The ticket selling application aims to streamline the process of purchasing airline tickets for users, providing them with a convenient and efficient platform to search, book, and manage their travel arrangements.
 
1.3 Definitions, acronyms, and abbreviations
 
SRS-Software Requirement Specification

PC-Personal Computer

NF-Normal Form

KPI-Key Performance Indicator

CEO-Chief Executive Officer
 
1.4 References
 
[Data dictionary](https://github.com/FontysVenlo/prj2-2024-18/blob/main/analysis/dataDictionary.md)
 
[User Stories](https://github.com/FontysVenlo/prj2-2024-18/blob/main/analysis/userStories.md)

[Domain Model](https://github.com/FontysVenlo/prj2-2024-18/blob/main/analysis/domainModel.svg)

[Database Design](https://github.com/FontysVenlo/prj2-2024-18/blob/main/design/DatabaseDesignAFS.svg)

[Use case diagram](https://github.com/FontysVenlo/prj2-2024-18/blob/main/analysis/usecaseDiagram.svg)
 
 
1.5 Overview
 
This subsection provides an overview of the contents and organization of the Software Requirements Specification (SRS).
The remainder of the SRS provides a detailed description of the requirements and steps taken for a successful creation of a ticket selling application. The SRS is organized into several sections, each focusing on specific aspects of the software requirements.
 
## 2. Overall description
 
This section provides a broad description of the SRS without going into precise detail, therefore providing a broad overview of the software requirements.
  
2.1 Product perspective
 
This subsection shows the ticket selling application within its larger system environment and outlines any dependencies, interfaces, and constraints that may impact its design and functioning in the future.
 
The ticket selling application is an independent and self-contained product designed to facilitate the purchase and management of airline tickets.
 
By understanding the product's perspective within its larger system context and acknowledging the various constraints and interfaces it operates within, stakeholders can effectively plan for integration, deployment, and maintenance of the ticket selling application.
 
2.2 Product function

The application is meant to be used as a way of booking the required flights on behalf of the clients of the company. 
It also is intended that the users of the application have the control over certain features which are only available for certain user groups. This includes being able to apply and change certain settings and features so that the end price of the bookings may vary.
 
2.3 User characteristics

The intended users of the application consist of the employees of the travel agency. The characteristics of these users would be that they specifically work for one travel agency and they are either part of the sales team or of the management team. 
The users shall have various levels of expertise and experience in the sphere of the project, and, therefore, different levels of education.
Most of the users don't have a deep understanding of the system behind the application, they have basic knowledge of the use of the application and the system which it runs on (PC).
Users of the application vary from a sales officer to an administrator. Their roles are specified in the second reference “User Stories”.

2.3.1 Sales officer

Talks to customers to sell them bookings
Creates trips by putting in the customer information and passenger information, selecting all the wanted options, selecting the wanted seat
Can apply special discounts
Decides on the minimum connection time

2.3.2 Sales manager

Sets price per kilometer  and general discounts and the timeframe when they are valid

2.3.3  Account manager

Can look into KPI’s 
Reports to the CEO

2.3.4  Administrator

Can create accounts for employees, the employees can’t register their account themselves, they only have the ability to log into their already existing account.

 2.4 Constraints
 
a) Important regulatory policies which should be considered while creating the application are: the right and safe use of customer information. Furthermore it is important to ensure safe use of the flight information.

b) There are not many hardware limitations, the only thing that is important is that the application has to work with a mouse and without a mouse, only with the keyboard. Also the application has to run on windows and also MacOS.

c) Interfaces to other applications which should be considered is that the flight data is received from an external API.

d) The system handles parallel operation automatically. The principle on which it operates is that if two users select the same process of the application, the first one who selected will have the priority.

 
2.5 Assumptions and dependencies

Assumptions and dependencies represent the factors that could potentially affect the requirements set out for the project in the SRS.

2.5.1 Operating environment

The SRS assumes that the application will be functioning in a normal, stable environment.
Any changes to it, such as updates, may affect the SRS.

2.5.2 Technological dependencies 

The application and the success of the functionality depends on the technology used, such as frameworks or development tools.

 
## 3. Specific requirements


3.1 Functions


3.1.1 User Authentication and Authorization

The system shall authenticate users upon login and grant appropriate access privileges based on their role within the travel agency.

Users shall be classified into distinct user groups, each with specific access permissions.

The system shall enforce role-based access control to ensure that users can only access features and data relevant to their roles.

3.1.2 Flight Search and Booking

The system shall provide users with the ability to search for available flights based on specified criteria, including date, destination, and preferred airline.

Users shall be able to view detailed flight information, including schedules, seat availability, and fare options.

The system shall allow users to book flights by selecting desired itineraries and providing necessary passenger information from the client.

Users shall have the option to modify or cancel existing bookings within specified time frames, adhering to airline policies and regulations.

3.1.3 Payment Processing

The system shall support secure a way for the Sales Officer to provide payment processing for flight bookings, integrating with payment gateways to facilitate transactions.

The system shall generate electronic confirmations upon successful payment, providing users with it via email or other communication channels.

3.1.4 User Management and Administration

The system shall maintain user profiles with relevant information, such as contact details and access permissions.

Administrators shall be able to monitor user activities and access logs for auditing and security purposes.

3.1.5 Error Handling and Recovery

The system shall detect and respond to abnormal situations, such as invalid inputs or system errors, by providing informative error messages.

Users shall be guided through error resolution processes, which may include corrective actions or assistance from support personnel.

The system shall implement mechanisms for error recovery, allowing users to resume interrupted tasks without data loss or inconsistency.

3.2 Performance requirements 

The system should respond within  a comfortable time range, providing a smooth and responsive user experience. The response should be enough to minimize critical situations. That applies to the amount of transactions too.
The numerical data is not available to the project group yet.

3.3 Logical database requirements 

Logical database requirements specify the structure and organization of data within the flight booking application.

3.3.1 Integrity constraints

The system shall enforce the data to be consistent and accurate, preventing errors or data inconsistencies.
Constraints such as primary key constraints, foreign key constraints, and domain constraints shall be defined to enforce data integrity rules.

3.3.2 Normalization

The database schema shall be normalized to reduce data redundancy and improve data integrity, adhering to standard normalization forms (e.g., 1NF, 2NF, 3NF).
Normalization techniques shall be applied to eliminate data anomalies and ensure efficient data storage.

3.4 Design constraints 

The design constraints may include various regulations and industry standards. These constraints shall be considered later in the design phase.

3.4.1 Security requirements

The system design must incorporate security measures to protect sensitive data, prevent unauthorized access, and mitigate security risks.
Design constraints may include encryption requirements, access control policies, and secure coding practices to address potential security vulnerabilities and threats.

3.4.2 Performance constraints 

Design decisions must consider performance constraints imposed by hardware limitations, and system resources, ensuring that the application meets specified performance requirements.
Constraints may include limitations on processing power, memory, disk space, and network bandwidth that must be accommodated during system design and optimization.

 
3.5 Software system attributes 

These attributes serve as requirements themselves, ensuring that the software meets certain standards of quality, performance, and maintainability.

3.5.1 Reliability

The system shall demonstrate high reliability, ensuring consistent and accurate performance under normal and abnormal conditions.
Reliability measures, such as mean time between failures and mean time to repair, shall meet specified requirements to minimize downtime and service interruptions.

3.5.2 Availability

The system shall maintain high availability, with minimal downtime and service disruptions during normal functioning.

3.5.3 Usability

The system shall provide a user-friendly and easy-to-navigate environment to ensure a pleasant and convenient experience for the users of the application.
