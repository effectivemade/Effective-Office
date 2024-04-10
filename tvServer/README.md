# Server for tv
It is server for effective tv application
## Feature
- Get list of events from leader id
- Get list of score from duolingo
- Get special events list (Birthday, first day new employee, ...)
- Processing post from mattermost webhook
- Send message in tv (firebase topic)
- Get saved messages
- Authentication for get access for information about employee
## Routing
- (get) /duolingo - get duolingo score
- (get) /event - get events list
- (get) /leader - get leader id events
- (post) /message - save message
- (put) /message - update message
- (get) /message - get saved messages
- (delete) /message/:id - delete message by id
- (post) /message/send - show message on tv in story
- (post) /message/important - show message on tv immediately
## Authentication
For get full access you need get api key and put it in header like this:

```X-EO-ApiKey: <your apikey> ```
## Secrets (.env)
- OURKEY - apikey for authentication on server
- APIKEY - apikey from mattermost
- MATTERMOST_DIRECT_ID - chat for save messages (bot direct)
- NOTION_TOKEN - token for get access for notion
- NOTION_DATABASE_ID - work together data base id
- FCM_CRED - firebase service account json
## Library
- Serialization: [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)
- HTTP requests: [Ktor client](https://ktor.io/docs/getting-started-ktor-client.html)
- Setting HTTP server: [Ktor server](https://ktor.io/docs/intellij-idea.html)
- DI: [Koin](https://insert-koin.io/)
- Send messages: [firebase](https://firebase.google.com/docs/cloud-messaging/send-message?hl=ru)
## Api Reference
- [Mattermost](https://api.mattermost.com/)
- [Leader Id](https://apps.leader-id.ru/swagger/)
## Issues
- [I](https://github.com/UserNameMax) haven't accessed to sinology photo, because server not work with sinology
- Bearer authentication not work on yandex cloud