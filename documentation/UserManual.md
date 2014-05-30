#User Manual
This document describes how to use the application, what it's goal is and more that might be good to know.

##Installation
###Option 1
Run the project as an Android application from your build environment as described in the [build process](github.com/DigohD/SE-Android/blob/master/documentation/BuildProcess.md).

###Option 2
1. Download the .apk file of the application to the phone
2. Navigate to the .apk file using a file system browsing software
3. Install the application (usually by tapping the .apk file and select install)

##First start
On the very first start of the application, or when the settings have been reseted and the application is started again, you will see a short welcoming text. Please read this carefully. Followed the welcoming, you will see a dialog asking for your player name. This is used to store both the local and global highscore. 
##Menus
###Start menu
Here, the text on the buttons does what it says. For more information about the *Global Highscore*, see the corresponding manual section furter down.
###Scores
A posted highscore cannot be edited. The local highscore can be deleted though.
####Local
The highest score recored is saved on your phone using a SQL database and is found under the menu tab *Scores*.
####Global
This score is stored on a server and is visible to anyone. You can select to post here after every finished game. Saved global highscores is found when clicking the *Global Highscore*-button on the start menu. This score cannot be edited or removed.
###Settings
Under this tab you can find useful settings that can help you get an enjoyable experience. Your settings, included selected player name from the *Start Menu* is saved on your phone unless the app is uninstalled or reseted.
####Music
This button toggles the in-game music on and off. Useful if you want to listen to music from another app when playing the game.
####SFX
This toggle the in-game sound effects on and off.
####Reset Highscores
This deletes the recored local highscore.
####Reset settings
This restores the selected playername, music and sound effect to what it was upon first start.
####Restart app
Use this to fast see the app as a it was on first start if you want to after reseting the settings.
##Game
Start the game by pressing the *Play*-button on the *Start Menu*. If this is your first start and you haven't checked the help section of the app yet, it will show now. 
###Weapons
When starting the game, select one of the weapons showing (you might see more by scrolling the list using your finger to swipe up and down). Different weapons suit different players and ways of playing good or bad. Find your favourite by trying them all and by altenating your aggressivity in the game.
####Damage
How much damage every projectile is dealing to an enemy. A higher number is better.
####Reload
The time between each fired projectile. A lower number is better.
####Spread
Defines how much the projectile deviates from the path of the ship. A higher number indicates a bigger spread.
####Speed
Shows how fast the projectiles travels. A higher number indicates a faster projectile.
###Steering
Use the green joystick on in the lower left corner of the game to steer the ship. The direction the joystick is dragged, the ship will start travelling to.
###Health
The health bar is shown in the upper left corner of the game. You lose health by taking hits from enemies projectiles and by hitting smaller asteroids. Collisions with enemy ships or bigger asteroids leads to instant death.
###Power-ups
In the lower right corner of the game you'll see your inventory with saved power ups. Tap one of these to activate it's function.
####Health pack
The read dots with a whit plus sign in them are health packs. Use these if you are low on health to be able to take more hits. If your health is full when picking up a health pack, it will be added to an empty intentory slot if you have one.
####Slow time
The blue dot with a hour glass on it slows down the time of everything else but your ship and your projectiles for a moment so that you can spend some time on killing more enemies.
###Score
The score is shown right under the health bar in the upper left corner of the game. Shooting enemies and asteroids gives you a cernain number of points. If you manage to shoot many things in a short period of time, you will get a combo multiplier which can increase your score greatly.
