External Dependencies
==========
This project requires certain extarnal resources to work as intended, apart from what is described in the [build process](github.com/DigohD/SE-Android/blob/master/documentation/BuildProcess.md).

Global highscores
----------
The global highscore is saved on an independent Java server application running on a PC. If this software or PC fails, or the application can't connect to it due to its host device's own limitations or errors, no high scores will be saved.

Risk of failure on High score server: medium

Ads
----------
The ads showing in the menus of the application requires internet to communicate with Google's AdMob servers hosting the service. If the communication for any reason can't be established, no ads will be shown.

Risk of failure on AdMob servers: low

Other
----------
The application also uses some functions on the device it's runs on to work properly. Hardware used is
- touch screen for operating the application
- phone memory for saving local highscores and settings
- speaker for playing music and sound effects
