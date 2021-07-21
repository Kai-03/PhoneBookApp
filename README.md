# PhoneBook App

This is a web app which allows users to create, modify or delete contacts. Users are created in the registration page. Contacts are associated with different User Accounts and retrieved using a unique ID from the User. This web app simulates the CRUD operations in a MVC framework.

## Database

Two tables are created:

* Accounts which holds the login credentials
* Contacts which stores:
    * User contact info
    * User's contacts

## Authors

Contributor names and contact info

[Kui-Dong Riman](https://github.com/Kai-03/)

## Version History

* 0.1
    * Initial Release
    * Working User registration and log in
    * Create and Delete Contacts
    * User Account and Contacts persist in the database
    * Currently no input character validation at the moment
    * Web interface is under development
    * To add the Update Contact functionality
    * To develop Explore functionality which allows a User to list other Users and Add them to their Contacts list

### Reference Documentation

This web app was created using Eclipse IDE and Spring Boot Initializer. Dependencies associated with this web app include:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
