# Okta
Go to [okta developer](https://developer.okta.com/signup/) and create an developer account.
![](img/oktadevelope.png)
Fill, if you want, about yourself
![](img/oktayourself.png)
Verify your email address and activate your okta developer account.
## Backend login
Create new java application
![](img/oktanew.png)
Change login redirect uri http://localhost:8080/authorization-code/callback to http://localhost:8080/login/oauth2/code/okta

Copy the cliend id and client secret to use on helm or the microservice.
## Frontend login
Create new angular application and change base, login redirect and logout redirect uris.
![](img/oktafrontend.png)
Now copy the client to use on helm or frontend application.