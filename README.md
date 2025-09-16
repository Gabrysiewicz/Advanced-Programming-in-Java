# Advanced Programming in Java

## Goal

This repository contains a Java course project, developed in a group of 2–3 students.  

The project allowed freedom in choosing the subject, but the application had to fulfill the following requirements:

- JWT authentication and authorization  
- Database integration  
- Usage of Lombok for boilerplate code reduction  
- Built with the Spring Framework  
- Support for **Admin** and **User** accounts  

## Project Description

The application is a **Todo List** with extended functionality.  

In addition to standard features such as creating and managing tasks, the app allows users to **share lists** so that multiple users can view and edit the same list simultaneously. This enables collaborative task management and keeps all shared users up to date with the list content.  


## Database

The database contains 4 tables:  

- **Users**  
  - User data required for login: `email`, `password` (hash)  
  - User identification data: `Name`, `Surname`, `ProfilePicture` (hash)  
  - Administrator data: `createdAt`, `updatedAt`  

- **List**  
  - `Hash`: a 16-character code; to share a list, the user provides a link and hash to another user they want to share it with  
  - `idUsers`: foreign key referencing the user who owns the list  
  - `Mode`: defines how the list will be shared  
    1. Copy  
    2. Shared  
  - `isFavourite`: field specifying the priority of lists  
  - Administrator data: `createdAt`, `updatedAt`  

- **ListItem**  
  - Foreign key referencing the list to which the item belongs  
  - Content data: `Name`, `isDone`, `Description`  
  - Administrator data: `createdAt`, `updatedAt`  

- **Users_has_List**  
  - Intermediate table  

All fields except `Description` in `ListItem` are required. Each table has a primary key with the `Auto Increment` attribute.  


<p align='center'>
<img src="https://github.com/Gabrysiewicz/Zaawansowane-programowanie-w-Javie/blob/main/Database/Database.png">
</p>
