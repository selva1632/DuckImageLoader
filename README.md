# DuckImageLoader

The implementation described above focuses on building a simple Android app using best practices such as Retrofit for network calls, Hilt for dependency injection, MVI (Model-View-Intent) architecture, and the Repository pattern

## Overall Flow:
When the app starts, the DuckViewModel triggers the network call (via UiEvent.GetDuck).
Data is fetched from the API using Retrofit through the DuckRepository.
State is updated in the ViewModel to reflect loading, success, or error.
UI reacts based on the UiState, showing either a loading indicator, the fetched image and fact, or an error message.
