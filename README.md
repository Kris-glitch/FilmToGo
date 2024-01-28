# FilmToGo - Android movie streaming app / Kotlin / Fragments / Coroutines / Live Data / xml

Film ToGo is a movie streaming app for Android. The app uses Kotlin, XML, Room, and Firebase technologies.
Allows users to sign up or log in with password validation such as min of 8 chars, min one uppercase letter, and min one number in the password. Firebase is used here for user authentication.

Next, it has a user setup section, starting with an animated splash screen. This section allows users to pick movie genres for future recommendations, add billing info, and choose subscriptions. Firebase real-time database is used to store the information.

After the setup, it displays the homepage, done with multiple recycler views. It has a recycler view of recommended movies and the option to filter movies by choosing a genre. The app uses API from https://www.themoviedb.org/, and also Retrofit.
By clicking a movie, movie details are displayed and you have the option to download or play the movie or add it to favorites. Room is used to store information about downloaded movies.

The homepage has a bottom navigation menu that includes a search view, and a profile item. The search view displays a search dialog and uses a Retrofit query to search. The profile item allows editing profile information.
In the profile segment users can edit billing info, choose a different subscription, or go to their downloads and favorites. The downloads and favorites segments have Recycler View with ItemTouchHelper and sipe functionality, where swiping left deletes a movie and swiping right takes you to the play screen.
Finally, the user has the option to share their favorite movies as a list in a message by using intent.

---- Tech used ----
Uses Firebase Authentication for registering users

Uses Firebase Realtime Database for storing billing info, selected preferences, subscription type, etc.

Uses Room to store downloaded movie information

Uses Glide Library to display movie posters

Uses xabaras swipe decorator

Uses Activities, Fragments, Navigation, Kotlin coroutines, Live Data

---- Illustrations for UI used ----

<a href="https://www.freepik.com/free-vector/naive-cinema-stickers-set_34678601.htm">Image by pikisuperstar</a> on Freepik

![login](https://github.com/Kris-glitch/FilmToGo/assets/78586563/690d35a7-6151-47ea-92b9-f0f1b26fb14a)
![signup](https://github.com/Kris-glitch/FilmToGo/assets/78586563/f1f9c5d3-efa6-4799-96b5-553163cbb730)
![splashscreen](https://github.com/Kris-glitch/FilmToGo/assets/78586563/92c17551-7f2f-452c-b575-9c788db81d6f)
![subscription](https://github.com/Kris-glitch/FilmToGo/assets/78586563/04250b56-b935-4359-8b52-ed5d540b9865)
![cardInfo](https://github.com/Kris-glitch/FilmToGo/assets/78586563/fa3e9ea9-4b23-42b5-94e4-e046cd0800b3)
![recom](https://github.com/Kris-glitch/FilmToGo/assets/78586563/d25eca27-e487-4795-acf1-1d20e3a2689c)
![homepage](https://github.com/Kris-glitch/FilmToGo/assets/78586563/5d4e39a1-bc49-4fd5-9bc7-ec5845b1bad9)
![moviedetails](https://github.com/Kris-glitch/FilmToGo/assets/78586563/ef5e8ab7-1ef5-4307-8fc6-2be434b2a379)
![search](https://github.com/Kris-glitch/FilmToGo/assets/78586563/31523da8-b1b6-48f4-afda-773f01530bf1)
![profile](https://github.com/Kris-glitch/FilmToGo/assets/78586563/84264d23-465f-4156-b53f-b00b498cb8bc)
![downloads](https://github.com/Kris-glitch/FilmToGo/assets/78586563/8e716c22-5e01-473e-9cfc-7e9550f62259)
![send list of movies](https://github.com/Kris-glitch/FilmToGo/assets/78586563/a699b46e-ed13-443f-b8f9-6a4121d1e360)




