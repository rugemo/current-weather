# Current Weather

Current Weather is a single activity app built for a coding challenge.

It requests the latitude and longitude values from the device, then uses RxJava and Retrofit to make a call to get the forecast for that location from the Dark Sky api.

Other third party libraries used are:

- Dagger for Dependency Injection
- Butterknife for view and method binding

## Application Architecture

The app follows Clean Architecture guidelines with 3 modules.

- App
- Domain
- Data

### App Layer

Contains all Android specific implementation details.

Split into 2 packages, UI and Framework.

#### UI
MVP Pattern - Presenter has minimal Android dependencies and has been unit tested.

#### Framework
Contains a Handler class for accessing the users Location.

### Data Layer
A pure Java layer that contains the implementation for accessing the Dark Sky api.

Unit tests for the mapper class that transforms data entities into domain models.

### Domain Layer
A pure Java layer that contains the models used by the UI.

Also contains the use cases (only one in this case) that describe the purpose of the app.

Unit tests for the use case concrete implementation.
