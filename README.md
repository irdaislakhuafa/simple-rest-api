Documentations
	* you can use swagger documentation with this url
	- http://localhost:8080/swagger-ui/#/
	before you access url above, please register your account in /restapi/users/register

How to run it?

	* First
	- you have to install maven (https://maven.apache.org/install.html)
	- install MySQL for DBMS (use xampp for easier)
	- install java/jdk 1.8 and above

	* Second
	- make sure you have set the path for java/jdk
	- run mysql (I think you already know how to run mysql)
		- create database with name simple_rest_api (you can modified database url in application.properties)
	- make sure you have installed maven and set the path so that it can be used in the terminal
	
if you have done all the requirements above correctly, type the command below to run it.

	* open your terminal
	- cd /path/to/simple-rest-api
	- mvn spring-boot:run

don't forget to git clone first haha :D
