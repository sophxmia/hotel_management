# Hotel Management System

## Project Overview

This repository contains a Hotel Management System designed for hotel administrators to efficiently manage room
allocation, guest registration, check-in/check-out processes, and guest search functionalities.

## Features

* Room Management: Keep track of different room types and their capacities.
* Guest Management: Store guest information including passport details, arrival, and departure dates.
* Check-in: Assign available rooms to guests upon arrival, register them, and generate invoices.
* Check-out: Select departing guests, free up rooms, or process delayed departures with additional invoicing.
* Early Departure: Allow guests to check out earlier than planned with reevaluation of charges.
* Guest Search: Easily find guests based on various criteria.

## Installation and Setup

1. Clone the repository from GitHub using the command: git clone https://github.com/sophxmia/hotel_management.git .
2. Download and install JDK and Maven on your computer.
3. Start your MySQL database and create a database named hotel_management.
4. In the application.yml file located in the src/main/resources directory, modify the parameters to connect to your database.
5. Run the application using the command mvn spring-boot:run.
6. Open the following URL in your browser: http://localhost:8080/hotel.

## Technologies Used

* Java 
* Spring Framework
* Spring Boot
* Spring MVC
* Thymeleaf
* Spring Data JPA
* Maven
* MySQL
* HTML5 
* CSS3
* Bootstrap


## Important Information about the project

This project has the following criteria:

* Ensured the functionalities meet the requirements specified for a hotel administrator.
* Properly set up the project with necessary libraries and frameworks.
* Established a clear and meaningful hierarchy of Java classes with proper variable and class naming conventions. Utilize
  design patterns where applicable.
* Designed a normalized database schema with appropriate relationships and foreign keys.
* Includeed comments and explanations regarding the deployment and usage of the application.
* Developed an intuitive and user-friendly interface for easy interaction with the system.

## Developer Documentation
1. Clone the repository to your local machine using the command git clone https://github.com/sophxmia/hotel_management.git.
2. Create a new branch for your changes using the command git checkout -b feature/new-feature.
3. Develop and make necessary changes to the source code.
4. Save your changes using the commands git add . to stage the changes and git commit -m "Description of changes" to commit the changes.
5. Upload your changes to GitHub using the command git push origin feature/new-feature.
6. Create a new pull request on GitHub with your branch of changes into the main branch of the project.
7. Review your changes, resolve any conflicts, and merge any incoming changes that occurred during the review of your pull request.
8. After your pull request is approved and all conflicts are resolved, your code will be merged into the main branch of the project.

## Author

Sofiia Maliarenko
