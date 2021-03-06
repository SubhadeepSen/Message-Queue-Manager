## Message-Queue-Manager

#### It manages multiple queues where one can create a queue, post and consume message from a queue, list down all the available queues, list down all the messages from a queue and delete a queue.
#### Check the attached images for avaliabe operations.


## Message-Queue-Manager-API

#### _Database:_ In memory Mongo DB which can easily be replaced with persistent database just by changing a simple configuration.
- Mongo DB
#### _API:_ Springboot v 2.1.4 
- Springboot-Web
- Springboot-data
- Springboot-Security
- Springboot-Devtools
- Swagger
- Lombok

## Instructions for API

- Clone the repository
- Open command prompt pointing to Message-Queue-Manager-API directory
- Run ___mvn clean install___ to build the project
- Change the directory to target
- Run ___java -jar Message-Queue-Manager-API-0.0.1-SNAPSHOT___ to deploy the application in embedded tomcat
- Open __http://localhost:8085/message-queue-manager/api/swagger-ui.html__ for API documentation
- Authorization: ___Basic cXVldWUtbWFuYWdlcjpxdWV1ZU1hbmFnZXJAMTIzNDU=___

<div style="text-align:center"><img src="https://github.com/SubhadeepSen/Message-Queue-Manager/blob/master/Queue-Manager-Swagger.jpg" /></div>


## Message-Queue-Manager-UI

#### Provides an interactive user interface to interact with the API. It has been developed in Angular v 7

## Instructions for UI

- Open command prompt pointing to Message-Queue-Manager-UI directory
- Run ___npm install___ to build the project
- Run ___npm start___ to deploy the UI application
- Open __http://localhost:4425/MessageQueueManagerUI/__ to launch the Application

<div style="text-align:center"><img src="https://github.com/SubhadeepSen/Message-Queue-Manager/blob/master/Queue-Manager-UI.jpg" /></div>
