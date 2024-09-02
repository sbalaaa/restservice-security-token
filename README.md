http://localhost:8080/api/auth/signup  --> POST --> To create new user 
http://localhost:8080/restservice/api/auth/signup
{
  "username": "admin",
  "email": "admin@gmail.com",
  "password": "123456",
  "role":["admin","user","create","update","delete"]
}

{
  "username": "user",
  "email": "user@gmail.com",
  "password": "123456",
  "role":["user"]
}

{
  "username": "tina",
  "email": "tina@gmail.com",
  "password": "123456",
  "role":["create","update"]
}

==============================================================


http://localhost:8080/api/auth/signin  --> POST --> To signin a existing user to get token
http://localhost:8080/restservice/api/auth/signin

Request:

{
  "username": "admin",
  "password": "123456"
}

Response:

{
    "uuid": "#UIID#",
    "response": {
        "responseMessage": "SUCCESS",
        "responseCode": 0.0,
        "response": {
            "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNzExOTE1NSwiZXhwIjoxNjI3MTE5MTg1fQ.ZUsU1YbfGtHktOd6kvBDZQ9nhr77-2IJK9WdHy1vjcX9yhxTx6cVdFkMfG-44jvVwqvPbcNxUnZ2L4W9vfIzBA",
            "refreshToken": "3e883961-6f04-4982-87d2-d843d5bbd7b2"
        }
    }
}

==============================================================


http://localhost:8080/api/auth/refreshtoken   --> To get token with refreshtoken --> POST
{
	"refreshToken": "861d9ec9-7a2d-4169-837c-3db164d468c2"
}


Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2QiLCJpYXQiOjE2MjA3MzA2NDksImV4cCI6MTYyMDczMDcwOX0.Jve8FWCWpqtbgUotE7H4f0_1pow3PZDPtnMjuVSVf_hnjBXP_3szIjQeNjPflgKXzTOJ4Ete2xtLWliZ1XpDPg

==============================================================

{
"refreshToken": "861d9ec9-7a2d-4169-837c-3db164d468c2",
"id": 1,
"username": "mod",
"email": "mod@gmail.com",
"roles": [
  "ROLE_USER",
  "ROLE_MODERATOR"
],
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2QiLCJpYXQiOjE2MjA3MzA0NTgsImV4cCI6MTYyMDczMDUxOH0.M2GnmPFydr-QmxItdCkE2iyzA50XkPUt0-MOvEJyG_etfRUx9AlrTdswjRvHeHhiO_IZgXzVSHZWdaKsdI_Tew",
"tokenType": "Bearer"
}

