This submission was built using the most recent version of android studio.
I have used Hilt for dependency injection, Compose to build the UI. It is structured to have an MVVM architecture.
I have omitted unit tests but those typically would be here if this was to go into production.

I used retrofit to make a call to the provided api and then used a use case to get the data from the repository for the viewmodel.
A use case just helps in reusability for situations where you might have a feature reused in multiple parts of the app.

The viewmodel then changes the data into what needs to be displayed to the user.