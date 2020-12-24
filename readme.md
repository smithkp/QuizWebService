# **Quiz Web Service**
## - Description
  A simple restful web service that enables users, who have registered, to create and solve quizzes. It uses H2 as an in memory database and spring security for authentication / authorization.

## - How to run
  Build and run the application

```shell
  $ gradle build
```
```shell
  $ java -jar .\build\libs\Web_Quiz_Engine-task.jar
```
***
## - User Registration
In order to access any enpoints, the client must send JSON containg an email address and password. This is done via **POST** request to the endpoint: 

> /api/register

the JSON in the request body should look like :
```json
{
  "email": "test123@gmail.com",
  "password": "password"
}
```
---
## - Creating a quiz
to create a quiz, the client must make a **post** request to the endpont : 
> /api/quizzes

The request body must contain JSON containg the quiz information.

All quizzes must have :
1. A title
2. Text describing the quiz
3. An array of options to select from
4. An answer

```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```
---
## - Solving a quiz
To solve a quiz, the client must send a **POST** request to the endpoint : 
> /api/quizzes/{id}/solve

JSON body must contain an array of indexes of all chosen answers. It is possible for the array to be empty since a quiz may have no correct answer. The JSON object should look as follows : 
```json
{
  "answer": [0,2]
}
```
The service will respond in one of three ways depending on the answer submitted:

Correct Answer
```json
{
  "success":true,"feedback":"Congratulations, you're right!"
}
```
Incorrect Answer
```json
{
  "success":false,"feedback":"Wrong answer! Please, try again."
}
```
Or 404(Not found)

---
## Getting a quiz by ID
To get a quiz by its id, the client must send a **GET** request to the endpoint : 
> /api/quizzes/{id}
---

## Getting all quizzes

A client may get all existing quizzes of the service by sending a **GET** request to the endpoint : 
> /api/quizzes

The service returns the paged result : 

```json
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":102,"title":"Test 1","text":"Text 1","options":["a","b","c"]},
    {"id":103,"title":"Test 2","text":"Text 2","options":["a", "b", "c", "d"]},
    {"id":202,"title":"The Java Logo","text":"What is depicted on the Java logo?",
     "options":["Robot","Tea leaf","Cup of coffee","Bug"]}
  ]
```
By default, the service returns 10 quizzes per page, but this can be changed with the **page** parameter.

---

## Get all quiz completions
A user may get all of the quizzes they have completed by sending a **GET** request to the endpoint : 
> /api/quizzes/completed

The response is separated by pages containing :
1. The id of the quiz completed(same quiz may be completed multiple times)
2. Time of completion
```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```
---

## Deleting a quiz
A user may delete a quiz by sending a **DELETE** request to :
> /api/quizzes/{id}

If the delete is successful, the service responds with a 204 status code.

If the quiz does not exist, the service responds with a 404 status code.

If the client is not the author of the quiz they are trying to delete, the service responds with a 403 status code.