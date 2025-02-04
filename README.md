# Apartment Manager

This is a web application to manage property.


*Tech stack:*
## RESTfull backend API.
## Back: Spring Boot, Maven, JPA, Spring security, PostgreSQL.
## Cache: Redis.
## Front: React.



![](/pics/3.JPG)


*Main features:*
 - Spring security(token-based authentication. Basic auth is used to get the token. Stateless).
 - Users can be locked out.
 - API is Role-based protected. (ADMIN, USER - default roles).
 - Add\view properties.
 - search
 - house planner (plan your house and save as a picture) . ( https://unstablecookie.github.io/basichouseplanner/ )

*modifications:*
Availdable in **addldap** branch. Open ldap integration with bind authentication.
The docker config comes with pre-configured open ldap server :
3 users , 2 role groups.
user1:password1 ,- no permissions across application.
user2:password2 ,- permissions to add own property.
user3:password3 ,- permissions to add property and view all properties.



Docker-ready compiled archieve is available in *releases*.

## How to install?

copy /target
	Dockerfile
	docker-compose.yml from the archieve to your server.
For example, to the /home/usr/aptmgr

![](/pics/inst1.jpg)

and run 
```
sudo docker compose up
```
![](/pics/inst2.jpg)

App will be awailable at http://DOCKER_SERVER_IP:8083 .
Default admin creds: admin\admin

ERD:

![](/pics/ERD.JPG)

Some other pics:
![](/pics/1.JPG)
![](/pics/2.JPG)
![](/pics/4.JPG)
![](/pics/5.JPG)
