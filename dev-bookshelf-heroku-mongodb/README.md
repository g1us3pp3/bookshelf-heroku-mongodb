##Spring Boot + MongoDB + Heroku Demo##
This demo application has been created as an example of deploying Spring Boot + MongoDB on Heroku.

There is a detailed discussion of the migration of the demo application from JPA to MongoDB at the following [web page](http://chrisbaileydeveloper.com/projects/spring-boot-mongodb-heroku-demo/) on my website.

###Technology Stack###
- Spring Boot, no-xml Spring MVC 4 web application for Servlet 3.0 environment
- Spring Data MongoDB
- Database (MongoDB, embedded MongoDB, MongoLab)  
- Thymeleaf templates with added Joda Time & Spring Security Dialects  
- Heroku fully cloud deployable
- Testing (JUnit/Mockito/MockMVC/AssertJ/Hamcrest)  
- Java 8, Spring Security 3.2, Maven 3, SLF4J, Logback, Bootstrap 3.3.4, jQuery 1.11.2, i18n, etc

###Compatibility###
This application functions properly with versions of MongoDB previous to 3.0.  As of October 2015, Spring Data MongoDB does not offer a compatible Java driver that will work properly with MongoDB 3.0.  The current Spring Data MongoDB project is using a Java driver with version 2.12.5 whereas version 2.13 is required.

###Live Demo###
Be aware that this application is currently running on a free Heroku account.  If it hasn't been accessed in 30 minutes, then ***the first request will take up to 120 seconds***.  Note that the demo application might fail to load altogether if the Heroku servers are busy. 

Here is the [MongoDB Developer's Bookshelf](https://dev-bookshelf-mongodb.herokuapp.com/) running on Heroku.


###Local Deployment###
```
$ mvn clean install  
$ mvn spring-boot:run
```

Navigate to [http://localhost:8080](http://localhost:8080).  
 
The application can also be deployed by running the `Application.java` class.

###Deploying to Heroku###
<i>The following steps require that the [Heroku Toolbelt](https://toolbelt.heroku.com/) has been installed locally and that a Heroku account has been created.</i>

Navigate to the project directory on the command line.

Before creating your Heroku application, make sure that there is a Git repository associated with the project.   
```
$ git status
```  

If a Git repository is not associated with the project, then create one before continuing. 

Create a new application on Heroku  
```
$ heroku create
```

Rename your Heroku application if interested  
``` 
$ heroku apps:rename new-name
```

Add a MongoDB database to your Heroku application with MongoLab.
Note that your Heroku account must have a credit card attached in order to use free add-ons other than the PostgreSQL and MySQL add-ons.
```
$ heroku addons:create mongolab:sandbox
```

Retrieve your MongoDB database name by clicking on the MongoLab addon.  Place the database name into the `src/main/resources/config/application-prod.yml` configuration file in the database field.

Create a new collection by clicking on the MongoLab addon.  
Click on the `Add collection` button.  
Create a collection named `T_BOOK`.  

Deploy project to Heroku.  
```
$ git push heroku master
```

Look at your application logs to see what is happening behind the scenes.  
```
$ heroku logs
```

If your application deploys without timing out then open it as follows. 
```
$ heroku open
```


### Author ###
[Chris Bailey](http://www.chrisbaileydeveloper.com)