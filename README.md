# Codingchallenge
Coding challenge for Archtouch no special build instrucctions.

This all shows you a list of upcoming videos that were taken from the API: TheMovieDB usgin Retrofit 2 as HTTP client

The list comes from a web service, as is showed to the user in a recycler view. At the bottom of the UI two added were added as controls for navigating in the list. Two more web services were needed to get ciertain properpties needed to translate the movie genre and get the base url for getting poster. This images where loaded into a Image View using asyncronous calls with Piccaso. 

This app was created using a Model View Presenter (MVP) pattern as architectural design. 

Third party libraries:

-Picasso A powerful image downloading and caching library for Android This library was used to add the posters to the upcoming movies in an asynchronous way.

-Retrofit 2 -GSON -Retrofit 2 gson converter A type-safe HTTP client for Android and Java To avoid use asynctasks instead I used Retrofit 2 to make the web service calls
