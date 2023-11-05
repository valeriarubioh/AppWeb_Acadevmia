
# Acadevmia

This project aims to create a website to address inquiries related to the development field for professionals and enthusiasts. We have included an e-commerce component for the purchase of courses, offering benefits to community users who make the most contributions.

The API provides access to information on community question and answer publications, reactions to them, and user profile data related to their contributions.



## Tech Stack

**Client:** Built with HTML, pure CSS and JavaScript

**Server:** Spring boot 

**Database:** MongoDB
## Installation
Clone this repository:
```bash
git clone https://github.com/valeriarubioh/AppWeb_Acadevmia.git
```
Java JDK 17 should be installed

Run the Spring boot project (available at: localhost:8080)



    
## API
Open a browser and access http://localhost:8080/swagger-ui/index.html.

The available endpoints are:
### Authentication Endpoints
- POST `/api/auth/signup`: Send data for user registration.
- POST `/api/auth/signin`: Send user login.
### Questions Endpoints
- GET `/api/v1/preguntas`: Get question publications.
- POST`/api/v1/preguntas`: Post a question.
### Answers Endpoints
- POST `/api/v1/respuestas/{id}`: Post an answer to a question id.
- GET `/api/v1/respuestas/{idPregunta}`: Get the answers to a question id.
- PATCH `/api/v1/respuestas/{idRespuesta}`: The user who owns the question can mark a response as favorite.
### Reactions Endpoint
- POST `/api/v1/reacciones`: Post a like or dislike reaction to a question or an answer. 


## Endpoints running tests
In order to access all the endpoints, you need a valid JWT access token. To get a JWT access token:
- Provide the user credentials username and password and execute.
```bash
POST /api/auth/signin
```
- Copy the token value (without the double quotes) and paste it in the Headers section
 
|Key   |Value  |
| ------------ | ------------ |
|Authorization   |Bearer your_token   |



## Authors
Valeria Rubio [https://github.com/valeriarubioh](https://github.com/valeriarubioh)  
Emily Piña [https://github.com/emmspy](https://github.com/emmspy)  
Jhon L Cruz [https://github.com/JhonLCruz](https://github.com/JhonLCruz)  
Estefanía Huertas [https://github.com/EstefaniaHuertas](https://github.com/EstefaniaHuertas)  