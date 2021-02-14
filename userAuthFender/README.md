# Fender User Auth App

This is my version of a token based user authentication system. I built all Five REST endpoints as required.
I made certain assumptions based on the information given in the instructions. This application was written using Java with SpringBoot and mySql.

### Building the Models

#### User Model
I built out a user model with the initial required properties of <b>name</b>, <b>email</b>, and <b>password</b>. I also added an <b>id</b> property, as well as a <b>created</b> and <b>last_updated</b> date.
These additional properties a for database purposes, <b>id</b> for primary key and indexing and <b>created</b> and <b>last_updated</b> dates for auditing and tracking.

![id, name, email, password, created, last_updated]( https://github.com/Days0fThnder/platform-exercise/blob/master/userAuthFender/src/main/resources/static/userModelDb.png "Description goes here")


#### Token Model
I also build an additional model to represent the token with four properties, 
<b>id</b>, <b>token</b>(value of the token), <b>user_id</b>(foreign key to the user table) and <b>created</b> date.

![id, token, user_Id, created]( https://github.com/Days0fThnder/platform-exercise/blob/master/userAuthFender/src/main/resources/static/tokenModelDb.png "Description goes here")


### App Functionality

The functionality of the application is based on requirements given in the instructions of the challenge, as well as assumptions made by me.

1. User Registration
   
    * This Rest endpoint uses a POST request call with a JSON payload including a name, email, and password.
    * The response is a newly registered user object.
    
2. Login (token based) - should return a token, given valid credentials
   
    * This Rest endpoint uses a POST request call with a JSON payload including an email and the user's password.
    * The response is a newly generated token, given the user's credentials are correct.
    * The response token is linked to that logged-in user.
    
3. Logout - logs a user out
   
    * This Rest endpoint user a POST request with a JSON payload including the email of the user you wish to log out.
    * The response is a 200 status, if log out is successful, and a deletion of the token associated with the user.
    
4. Update a User's Information
   
    * This Rest endpoint uses a PUT request along with the user id (the user you wish to update) in the uri path. 
      The request also includes a bearer token in the header which is the user associated token created at user log in. 
      There is also a JSON payload with the user properties you wish to update.
    * The Response should return the newly updated user object.
    
5. Delete a User
   
    * This Rest endpoint uses a DELETE request along with the user id (the user you wish to delete) in the uri path.
      The request also includes a bearer token in the header which is the user associated token created at user log in.  
    * The Response should return a 204 no content and the user should be deleted for the database along with their associated token.
    
### Installation and Running

#### Requirement
1. [Java 14](https://jdk.java.net/14)
2. Your Favorite Java IDE, ([Intellij](https://www.jetbrains.com/idea/download) recommend)
3. [Gradle](https://gradle.org)
4. [Docker](https://www.docker.com/) and Docker cli if you would like
5. [mysql](https://www.mysql.com/)
6. [Git](https://git-scm.com/downloads)

### Setup

#### Database
Once tools are downloaded and installed, we first need to set up the database.
 1. From the Docker hub download the latest image of mysql. <i>(cli: ```$ docker pull mysql```)</i>
 2. From your docker dashboard you will be able to start the image. <i>(cli: ``` $ docker run --name mysql ```)</i>

#### mySql dbms client

1. I recommend using DBMS client GUI to connect to the mysql docker image, I used [mySql Workbench](https://www.mysql.com/products/workbench/).
Once your DBMS client is downloaded and installed the connection details of the image can be found in the docker dashboard under "Containers / Apps" and clicking on the running mysql container and clicking on the "Inspect" tab.

2. Once connected to the docker image. You can take a dump of the schema by using the file stored in the resources directory of the project ```<yourPath>/resources/static/userFender.sql```.


#### Running application code 

3. You can clone the repo via git ```git clone https://github.com/Days0fThnder/platform-exercise.git```.
4. After cloning configure the ``application.properties`` file to make sure spring can connect to the running mysql database.
      ```properties
   spring.datasource.url=jdbc:mysql://{localhost}:{port}/{schemaName}?useSSL=false&serverTimezone=UTC
   spring.datasource.username=<yourUserName>
   spring.datasource.password=<YourPassWord>
   ```
5. To build the application ``./gradlew build``
6. To run the application ``./gradlew bootRun``

### Further Possible Enhancements
1. I was able to create one Unit test to test the ``UserServices`` class, with more time I could create more tests classes to cover more cases.
   * <i>Test can run through the IDE, but I with more time I should be able to get them to run for command line</i>
2. Given more time I could also enhance the functionality of the token, like set an expiration time/date for more security.
3. I could also give the token the ability to be refreshed automatically maybe
4. I could also create a feature to recover a forgotten password.



      



