# rtCommunicator

## Table of contents
- [General info](#general-info)
- [Technologies](#technologies)
- [About the project](#about-the-project)
   - [Main application interface](#main-application-interface)
   - [Login](#login)
   - [Registration](#registration)
   - [Forgot password option](#forgot-password-option)
- [Functional and non-functional requirements](#functional-and-non-functional-requirements)
- [Project status](#project-status)
- [Contact](#contact)

## General info
The application is a messenger that enables the exchange of messages between users in real time, where the protocol is WebSocket.

In order to be able to log in, the user must first set up his account and then confirm its authenticity by clicking on a special link sent to his e-mail address. After logging in, the user can change the data associated with his account.

The application gives you the ability to create and manage new rooms. Each user we want to add to the room will receive an invitation from us that they can accept or reject. All users in our room can receive and send messages.

## Technologies
* Java 11
* JavaScript
* WebSocket
* MongoDB
* MySQL
* Spring Boot
* Spring Security
* Spring WEB
* Spring MVC
* Spring DATA
* JSON Web Token
* Oauth2
* HTML, SCSS, Thymeleaf, Swiper.js

## About the project

### Main application interface

![app_panel](https://user-images.githubusercontent.com/67873349/218342257-174ba860-a279-415f-a6fc-703b91de6913.JPG)

### Login

<p align="center">
  <img src="https://user-images.githubusercontent.com/67873349/218344485-03caaae6-8f55-4621-98b2-67d762c52b4f.JPG" width="370">
</p>


### Registration

<p align="center">
  <img src="https://user-images.githubusercontent.com/67873349/218344570-0f956688-0a4b-49c8-9a6f-fc2d85ed8541.JPG" width="370">
</p>

### Forgot password option

Initialize set new password process for choose email adress.
<p align="center">
  <img src="https://user-images.githubusercontent.com/67873349/231785029-f9310450-10b9-4609-899e-8856acca0f53.JPG" width="520">
</p>

On your email box you will receive special link with auth token as param, which will redirect you to the password setting form.
<p align="center">
  <img src="https://user-images.githubusercontent.com/67873349/231785457-048b782a-2857-42cc-9551-6d742fa37bde.JPG" width="520">
</p>


## Functional and non-functional requirements

The non-functional requirements that have been set for the application include:
* Responsive and intuitive application graphical interface.
* Ensuring highly scalable applications.
* The application should be cross-platform (i.e. not strictly related to one operating system)
* Provide real-time communication for users.
* Provide user authorization solutions.
* The application should be built on the basis of currently used technologies and solutions.



The functional requirements that have been set for the application include:
* The user should be able to authorize using his/her e-mail address and
password associated with the account.
* The user should be able to authenticate using Google and Facebook accounts.
* A logged in user should be able to log out of their account.
* A non-logged-out user should not be reauthorized after
closing and then reopening the application.
* The user should be able to create an account with its confirmation via
sending an activation link to the e-mail address provided.
10
* The user should have access to the forgot password function in case he forgot it
password and will want to change it.
User access to manage your account, including changes to basic data,
profile picture and account deletion.
* The user should be able to create new rooms.
* The user should be able to invite friends.
* The user should be able to manage the rooms to which he belongs, including
adding new users, deleting them, leaving the room, changing the name of the room.
* After introducing changes in his room, the user should receive feedback about
action success and updates presented in its graphical interface based on
response from the server.
* The user should be able to send his chat messages to anyone
another user assigned to the selected room.
* Users belonging to a given room should be notified when they receive a new message.
* The user should have access to information on how many messages in a given room have not been through
read to him.
* The user after reading the message in a given room should be notified that there is none
new messages.
* Users who receive invitations to join a room should be able to do so
accepting or rejecting.

## Project status

The project has been completed, all functional and non-functional requirements have been implemented. It was a great opportunity to learn about new technologies and solutions as well as gain experience in creating applications.

## Setup

## Contact

Author: Marcin Mikołajczyk \
Email: marcin.mikolajczyk22@gmail.com \
Project Link: https://github.com/MarcinMikolaj/rtCommunicator


