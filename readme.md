# NISB Android App

## Libraries used
1. com.android.volley:volley:1.0.0
2. com.google.firebase:firebase-auth:10.2.0
3. com.google.firebase:firebase-messaging:10.2.0
4. com.google.firebase:firebase-database:10.2.0

### Firebase
The user login with email and password is done using the firebase auth library.
The user messaging requires the device token, so the user's email in hashed format is stored with the device token in a realtime database.

### Status
#### Done
1. About Page
2. User Login
3. Realtime Database connectivity
4. User / Guest Login and Registration
5. Blog
6. User notification system
7. Splash Screen

#### To Do
1. Team
2. Events
3. Contact
5. Event Reminders through notifications
