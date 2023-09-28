# FilmToGo
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

![login](https://github.com/Kris-glitch/FilmToGo/assets/78586563/10e41918-be38-4f6c-b6aa-2aec9712be0c)
![signup](https://github.com/Kris-glitch/FilmToGo/assets/78586563/4340a68a-e532-40c5-bd31-33377411b555)
![splashscreen](https://github.com/Kris-glitch/FilmToGo/assets/78586563/fccc2377-d4e7-4a49-bd67-68acd2195223)
![subscription](https://github.com/Kris-glitch/FilmToGo/assets/78586563/9fedac77-50f3-4e93-bd50-348d68072685)
![recom](https://github.com/Kris-glitch/FilmToGo/assets/78586563/19dcca61-4d1c-4050-950b-efcdd98b53b1)
![cardInfo](https://github.com/Kris-glitch/FilmToGo/assets/78586563/ce33f4b4-dfe1-45ab-b3ff-268ea4bea9aa)
![homepage](https://github.com/Kris-glitch/FilmToGo/assets/78586563/28bb13c9-f508-49e8-a823-c8c9084935b2)
![moviedetails](https://github.com/Kris-glitch/FilmToGo/assets/78586563/8a0f646e-5111-4a72-a188-52899da6a58b)
![search](https://github.com/Kris-glitch/FilmToGo/assets/78586563/b5fa8d25-9697-485c-9527-029a51747ad4)
![profile](https://github.com/Kris-glitch/FilmToGo/assets/78586563/d69d3562-ab29-45f5-a8f9-c16897c21fdf)
![downloads](https://github.com/Kris-glitch/FilmToGo/assets/78586563/8ab4d7a3-c4c7-4012-8c71-6f172cd1ea34)
![swipe functionallity](https://github.com/Kris-glitch/FilmToGo/assets/78586563/ac108b7a-b0ab-48dd-b845-545345ce2442)
![send list of movies](https://github.com/Kris-glitch/FilmToGo/assets/78586563/caceaae3-e76f-4860-a72d-296d1d94c398)

