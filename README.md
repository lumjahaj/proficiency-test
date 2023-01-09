# proficiency-test

This project is designed to read data from an Excel file containing employee and department information, store the data in a database, and provide a set of API endpoints to search, display, and manipulate the data.

### API endpoints

* Read data from an Excel and store it in the database
* Search for employees based on various criteria
* Display active and inactive employees
* Display all employees in ascending order
* Display the total number of employees by department and their last names

### Tests

* Check number of active users
* Check number of inactive users
* Search employee by username

## Getting Started

* Clone or download the project code from the repository to your local machine
* Create a new PostgreSQL database and update the database configuration in the application.yml file
* Import the project into your IDE as a Maven project
* Open a terminal and navigate to the root directory of the project
* Run the application
* The application should now be running at http://localhost:8080
* You can use this URL to test the API endpoints with Postman. You can find the **proficiency-test.postman_collection.json** file located in the **src/main/resources** directory

### Prerequisites

* Java 8 or higher
* Maven
* An IDE such as IntelliJ
* A PostgreSQL database
