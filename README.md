## Message-Queue-Manager

#### It manages multiple queues where one can create a queue, post and consume message from a queue, list down all the available queues, list down all the messages from a queue and delete a queue.
#### Check the Swagger image for avaliabe operations.

#### Swagger URL: http://localhost:8085/message-queue-manager/api/swagger-ui.html

## Technical Descriptions

#### _Database:_ In memmory Mongo DB which can easily be replaced with persistent database just by changing a simple configuration.
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
- Run __mvn clean install__ to build the project
- Change the directory to target
- Run __java -jar Message-Queue-Manager-API-0.0.1-SNAPSHOT__ to deploy the application in embedded tomcat
- Open __http://localhost:8085/message-queue-manager/api/swagger-ui.html__ for API documentation
- Authorization: Basic cXVldWUtbWFuYWdlcjpxdWV1ZU1hbmFnZXJAMTIzNDU=

<div style="text-align:center"><img src="https://github.com/SubhadeepSen/Message-Queue-Manager/blob/master/Queue-Manager-Swagger.jpg" /></div>


## Message-Queue-Manager-UI

#### Provides a UI interface to interact with the API. It has been developed in Angular v 7

#### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.
