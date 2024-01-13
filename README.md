# Proiect GlobalWaves  - Etapa 3
 ### Mărgheanu Cristina-Andreea 323CA

* I used the official implementation for the first 2 stages of the project to be able to continue
the development of Spotify functionalities with : new statistics generation functions for all types
of users, monetization management, but also to create a summary for the artists that have been
listened to, using the end program method.

### Description of the workflow and new added important classes:
* In the command runner class all the methods used are called so that all the commands can then be 
entered in Main. The statistics for each type of user are made in the user class, where the number of listens
for each category is updated: album, song, artist, episode, using what is currently in the player. 

* **Wrapped:** 
The method where the updates are made is called 'listening'. The strategy approached for its creation was the
following: when the load command is called , the time at which this operation performed is stored in the time variable.
We will use it later to find out how many songs played from the current album , or how many episodes from the podcast.
At the same time, we also make a copy of the player , which copies the type of the current source as well as the source,
in order to have access to the last source in the player and to be able to update its number of listens. 
When a new search is performed, I call the listening method because then I will no longer take into account
what it was in player and we can move on to the next search and create statistics for what was played previously.
The number of listens for an album and its songs is done as follows: it is checked if the current source si an album
and the time(noted remain) is calculated as the difference between the new search call and the last load call.
This time si compared with the total variable , which sums up the duration of the songs from the player. When this
exceeds the initially given time, it means that a new search has been started and that we are no longer progressing in
the analysis of the songs from the respective album. I do the same for the podcast.

* **Notifications:**
Also within the user package , for notifications, I created a new class that contains its name and description. Every
user, regardless of their type can receive notifications , so I initialized a list of notifications for the abstract
user, that is updated every time an album, merch or event is added. 

* **Recommendations:**
To get the song type recommendations, I use a list of songs that have the same genre as the current song in player that
respects the condition that is has played for more than 30 seconds of the song. Then I use the random util to get the 
desired recommendations.

* **EndProgramOutput:**
For the display at the end of the program, I summarized the necessary information in the EndProgramOutput class. 
This class uses fields from the artist class.

### In my implementation  I used the following design patterns:
1. Singleton Pattern: was used in order to create a global variable to be used in several classes and methods.
It creates a common access point from various places of the application and restricts the number of instantiations
of a class to a single object. The instance declaration in made in the Admin class.
2. Command Pattern: for the page navigation system : this design was used in order to make the navigation through the 
pages easier. The extension to add new commands is easier by calling the command interface, executing the switch
to the next page or to the previous one.
3. Factory Pattern : this creational design pattern uses the PageFactory class with which help the pages are created, 
depending on their type. I chose to use it to offer a solution more related to the creation of pages. It was used
in the changePage method in order to move and set the next current page. 
4. Strategy Pattern: was used for the shuffle strategy. To create this , I implemented the Shuffle Strategy interface
and the Random Strategy class that implements this interface. Initially, in the method of the PlayerSource class 
that generates the shuffle order, I initialize the list of indices to be in a normal order, the initial one, 
and then shuffle it using the shuffle method. Thus, the strategy is implemented more easily and can be called
upon in the future in other areas of the program. 