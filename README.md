## SUMMARY
- [**TOM API**](#tom-api) ………………………………………………………………………………..
- [**Starting**](#-starting) ………………………………………………………………………………..
- [**Prerequisites**](#-prerequisites)…………………………………………………………………………..
- [**SOME INFORMATIONS ABOUT THE PROJECT**](#%EF%B8%8F%EF%B8%8F-some-informations-about-the-project)………………………………..
- [**My Approach**](#-my-approach)……………………………………………………………………………
- [**Fetch Members Job**](#fetch-members-job)……………………………………………………………………
- [**Fetch Teams**](#fetch-teams)……………………………………………………………………………
- [**Database and API flow**](#%EF%B8%8F-database-and-api-flow)………………………………………………………………..
- [**SUGGESTION FOR IMPROVEMENT IN THE TEAM OR USER SERVICES**](#-suggestion-for-improvement-in-the-team-or-user-services)..
- [**SUGGESTION FOR IMPROVEMENT OF TOM APPLICATION**](#-suggestion-for-improvement-of-tom-aplication)………………..
- [**WHAT HAPPENS IF THE DATA YOU ARE USING GETS DELETED?**](#-what-happens-if-the-data-you-are-using-gets-deleted).............
- [**Observations**](#some-observations)…………………………………………………………………………
- [**Acknowledgments**](Acknowledgments…………………………………………………………………..)…………………………………………………………………..


# TOM API

#### T -> means team
#### O -> means organizer
#### M -> means Manager

... Yes, I know

## 🚀 Starting

### 📋 Prerequisites

```
Docker
```
The project contains a dockerfile and a docker-compose file, then you must have the Docker installed on your machine.

Considering you have the docker and docker-compose installed on your machine, what you need to do is:

Go to the root of the project, and run:

```
docker-compose up -d
```

It will build and up two docker containers, the first is for the database and the second for the TOM API.
maybe the tomapi container fail in the first attempt, but don't worry it will retry and run perfectly

You can access the TOM API by using the URL: http://localhost:8080/tomapi/

You can access Swagger documentation available on: http://localhost:8080/tomapi/swagger-ui.html#/


Consulte **Implantação** para saber como implantar o projeto.

## ⚙️🛠️ Some informations about the project

The project Was built using:

- Java 8
- Springboot
- Mysql 
- JUnit
- Swagger
- LOG4J - without vulnerability


### 🧠 My Approach

To build this project I had to consider an existing application which provides resource data about users and team, at the first moment I considered using a microservice application however I had just few information about the project and its purpose,  then I chose following with a monolithic application strategy with some aspects of the clean architecture, so If I need change the project to a microservice architecture in the future it will be easy.


After deciding between microservice and monolithic architecture I got concerned about how to get, handle and keep the data provided for the other application because performing integrations between APIs is not a trivial task,  and thinking about it I got some points:

 - How much is getting data in the other application?
 - If the other application stop working?
 - Do we have some control over the other application?
 - Will there be a future sync between both applications?
 - Is the other application in constant improvement or is it a final implementation?
 - How ensure real-time access to the data in the other application.


As proof of concept, I built a solution based on scheduled jobs and real-time searches to retrieve and keep the data for TOM’s database.

Talking about the scheduled jobs I assume the risk of thinking that there will be good times to run the jobs, in the other words there will be moments when the application will be not used or almost not used. Since the application will be running inside a company to manage teams and members, it probably is a low risk. For the same reason, I also assume that the application is not a really critical system inside the company.

Considering my observations I built four scheduled jobs:

 - Fetch Members Job
 - Fetch Teams Job
 - Delete Members Job - (actually, I haven't built it but, it was my plan)
 - Delete Teams Job - (actually, I haven't built it but, it was my plan)

##### **Fetch Members Job**

This job is responsible for doing a requisition to the other application and retrieving all necessary data to instantiate a Member in TOM application, it is: first name, last name, display name, location, avatar, and uuid and afterward save the data to TOM’s database.

The algorithm built for this task has the following Mathematical Equation when the worst case:
```_
 (4 + n + 3n)
```

Having these asymptotic analyses above and considering a server executing 8.000 operations per second + the worst case we have today which is 400 users the first time the job is run the algorithm would take:

```
  4 + 400  + 3 * 400 = 1604 operations -> 1604 operations / 8000 = 0.2 seconds to execute the algorithm.
```

That is a such good algorithm because it is a linear one.

OBS: For sure, these asymptotic analyses don’t take into consideration the request and response time for the other application, time to open, retrieve, record, and commit to the database, and also don’t consider micro-operations such as reading and writing in the machine memory.

To sum up, the job also contains a good algorithm and we have to consider that there will not be hundreds of new teams and members every day, then this job will be really efficient.

##### **Fetch Teams**

This job is responsible for doing a requisition to the other application and retrieving all necessary data to instantiate a Team in TOM application, it is: name, uuid, and team lead, besides it, we have to fetch the members of each team and construct the memberships and afterward save all data to TOM’s database.


The algorithm built for this task has the following Mathematical Equation when the worst case:

 5 + ( n + act) + 6n + 6 x 7

act = amount of current data on TOM’s database

Having these asymptotic analyses above and considering a server executing 8.000 operations per second + the worst case we have today which is 100 teams the first time the job is ran and considering the amount of teams already saved in TOM’s database being 500 the algorithm would take:

5 + (100 +  500) + (6 x 100) + (6 x 7) = 1247 operations -> 1247 / 8000 = 0.15 seconds to execute the algorithm.

OBS: For sure, these asymptotic analyses don’t take into consideration the request and response time for the other application, time to open, retrieve, record, and commit to the database, and also don’t consider micro-operations such as reading and writing in the machine memory.


##### **Other way to fetch data*

Always a request by id is sent to TOM API, it first looks in TOM’s database if the entity is not present, a request is sent to the other application in order to retrieve and save it in the TOM DB, if so, the application return the result which is now saved in the bank 


### ⌨️ Database and API flow

 Using UUID as a unique have some advantages:
 
 - security: opaque IDs for exporting in REST APIs;
 - decentralized ID generation: so browsers, clients and services, mobile apps and other banks can generate unique IDs;
 - ideal on distributed DBs or with multiple write nodes;
 - are great for planned banks or when merging;
 - super useful among DBs
 - its use in batch processing can improve and optimize throughput;
 - in common data replication scenarios

Some disadvantages:

 - requires more disco space;
 - Affect memory buffer to index table;
 - UUIDs are not orderable (although such specifications and implementations as the ULID);
 - Most 64-bit servers: UUID are 128-bit keys, so they need at least 2 CPU cycles to process;
 - Could be worse? of course, just explain in your bank the UUID;
 - PostgreSQL and impact on writing and reading;
 - PostgreSQL and Write Amplification;
 - MySQL and clustered indexes;
 - MS SQL Server and clustered indexes (suffers from the same malady as MySQL);
 - Oracle and performance impact;

Besides that, we have cases as examples in Big techs such as Meta / Facebook which needed to destruct Instagram Database in other to improve performance, and instead of using UUID they built their own 64bits ID.

I chose use Int as primary key in order to keep the database helthy but I use uuid as a column in database, and I use this field such as an external ID,
So that, I can keep the relation between both applications.


## 📦 Suggestion for improvement in the Team or User services

In my point of view, the service to list all users and teams must return the completed data as in the searching by ID. It would allow a simpler implementation of my algorithms cited in previous section

Adicione notas adicionais sobre como implantar isso em um sistema ativo

## 📦 Suggestion for improvement of TOM aplication

- It was in my plan to use Apache Camel to make the integration because it is scalable and  provides quickly and easily integrate of various systems consuming or producing data.
- Mabe implement jobs to delete data based on the other application.
- Maybe turn the project into microservice architecture, but I need to be sure about the purpose of the application and the other application.
- The cron definitions are hardcoded, move them to environment variables.

## 🤯 What happens if the data you are using gets deleted?

If the other application data is deleted, the system will keep working because we retrieve data and save it in the Tom's database

## Some observations

 - I chose don't extract the two common attributes to a base entity because I think these two attributes don’t add behavior that is worth extracting, Instead of it, I preferred to write an Interface to make use of the concept of programming thinking on interfafaces.

 - I provide security but I let the endpoints open.

## 🎁 ❤️ Thank you

I would like to thank for the opportunity.



---
com ❤️ por [Lúcio Leandro](https://linkedin.com/in/lucioleandro) 😊

