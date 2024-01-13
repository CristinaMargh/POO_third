# Proiect GlobalWaves  - Etapa 3
 Margheanu Cristina-Andreea 323CA

I used the official implementation for the first 2 stages of the project to be able to continue
the development of Spotify functionalities with : new statistics generation functions for all types
of users, monetization management, but also to create a summary for the artists that have been
listened to, using the end program method.

Description of the workflow:
In the command runner class all the methods used are called so that all the commands can then be 
entered in Main. The statistics for each type of user are made in the user class, where the number of listens
for each category is updated: album, song, artist, episode, using what is currently in the player. 

In my implementation  I used the following design patterns:
1. Singleton Pattern was used in order to create a global variable to be used in several classes and methods.
It creates a common access point from various places of the application and restricts the number of instantiations
of a class to a single object. The instance declaration in made in the admin class.
2. Command Pattern for the page navigation system.
3. Factory Pattern 
4. Strategy Pattern was used for the shuffle strategy. 