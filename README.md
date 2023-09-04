# Course Review
A full-stack course review system using Java while utilizing JavaFX for front-end development and SQLite for back-end development. Below, I have included a breakdown of the project in terms of an MVC software design pattern as well as a walkthrough video of the project. 

# Model
The model, what I often refer to as the integration, allows the back-end to communicate with the front-end. This code was primarily used to detect valid/invalid input types and address them as needed. Below, there are several issues that will throw an error which we display in the view portion of the MVC.
 - Incorrect password (log in)
 - Username already exists (create new account)
 - Class does not exist (view reviews)
 - Already submitted review (submit reviews)

# View
The front-end, using JavaFX, was designed through the usage of four different scenes (log in, create new account, view reviews, submit reviews). Using my knowledge of front-end development, I was able to do this easily as JavaFX mimics HTML formatting and style.

# Controller
The back-end, using SQLite, will store information on your device while the project is still open, however due to the fact that it is SQLite, it will dissapear after you leave the project. In SQL, we store usernames, passwords, reviews, ratings, classes, and student review history to allow the software to work. 
