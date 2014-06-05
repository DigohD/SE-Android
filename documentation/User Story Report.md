#User Story Report
User stories have been used to define different items in our backlog. early on in the project, we defined the critical user stories that had to be implemented in order for the game to be considered finished. Along with these, we also specified general user stories for aspects of the game that were not considered critical in a working product, but important for a high quality game. The stories arose from two different perspectives, the user- and the developer perspectives. We will list some of the major user stories during the development process.

##Critical
**“As a developer, I want the game engine to be well designed in order to more easily add new features in the future. The engine must meet the needs of the vision of the game, but preferably also be flexible enough to allow future changes” - Critical**

This story is very general and ill-defined at a first look. However, given the vision, there is a clear description of what the engine had to be able to do. 

Most of the items in our backlog are of this nature. We had a policy to individually divide these big items into smaller parts and execute them accordingly. The problem was that some areas of the application was hard to predefine and that some areas were described as “non-finishable”. In other words, some items were viewed as processes rather than specific implementations. The intention was to be better prepared for any ideas of new features that would possibly arise during, or after the development.

**“As a developer, I want the application to be ‘scalable’, in order for the game to be playable with any screen resolution. This is important in order to reach a broader audience.” - critical**

This item is more specific. Although it could be put under the previous story, it was considered vital enough to be it’s own item. Android is run on a huge variety of screen resolutions and sizes.

**“As an user, I want to be able to pause the game so that I can resume at the exact same point later” - critical**

**“As an user, I want the game to pause as soon as focus is released from the app so that I can answer phone calls” critical**

These stories were considered critical, since all group members are hardened gamers, that know the feeling of having progress thrown into the trash bin due to game crashes and other unexpected events.


**“As a developer, I want to improve performance in order to make the game playable on low-end devices” - critical**

This item is related to the “scalable-screen” item earlier described. To improve already existing code is sometimes as important as creating new features, and it has been a priority to minimize lag and other performance issue artifacts.

Now these are all stories that have little to do with the game vision, or even the MVP. We established from the start that the most important part of any game is the underlying structure and how well it is designed and implemented. If at any point one of us found something in the game engine that was hard to understand or work with, we prioritized to refactor and redesign parts that were redundant or inefficient.

With a working and efficient game engine, one can start dealing with the game itself:

**“As an user, I want to be able to steer the ship in an easy and intuitive way so that steep learning curves can be eliminated” - critical**

This story is also somewhat general as it does not specify anything about the actual steering mechanism. The member responsible for this item was expected to test different solutions and come up with a working prototype. 

In the case of steering, the ship first just followed the finger of the player. After some acceptance testing this was considered not ideal, since the finger made it hard to see some parts of the screen. A joystick was added instead, so that only the bottom-left corner would be used. Even then, there was complaints about not seeing the ship as it traveled beneath the joystick. To solve this issue, a mechanism for scrolling the whole game area upwards as the player ship travelled far down, was invented. This solution was accepted as the final solution, and also added a somewhat interesting graphical effect to the game.

**“As an user, I want to be able to shoot enemies so that a new score is set” - critical**

This item is very specific and also one of the most important items in the backlog, since it is the foundation of the gameplay. Of course, due to testing, this item was as well expanded into a number of sub-parts later, including:

**“As an user, I want different ways of shooting enemies, so that the game feels more dynamic and diverse.” - optional**

**“As an user, I want to be rewarded when I dare to take risks, rather than staying completely passive.” - optional**

These sub-items were considered optional as they were not required in the MVP. The respective solutions for both was to design the weapons inventory and the combo system. The weapons inventory was also part of the vision.

“As an user, I want To have the ship move smoothly so that The game expresses a higher quality and hence feels more enjoyable” - critical

This story arose from a fear that steering would turn out stiff and hard to use. Acceleration and top speeds was added, as well as support for accelerating at different speeds by moving the thumb less or more. The system was tested a lot to set the different values that affect the steering, in order to improve control as much as possible.

##Optional
Although some of the features that were labeled as optional was defined in the MVP, they were not set as high priority due to their peripheral nature. Things like sound effects, music and fancier graphics were not seen to be required in the finished product, in a development sense. The stories for these things were also very general, for example:

**“As an user, I want to receive visual and auditory feedback that help me identify what's happening in the game.” - optional (apart from enemies disappearing when shot, which was critical)**

**“As an user, I want the game to feel dynamic and ‘alive’. There should be graphics and audio that fit well into the game setting while also staying consistent” - optional**

These user stories would as well branch out into a number of smaller issues that arose from testing the game. some being:

**“As an user, I want music and sounds to be normalized, in other words, I want the sounds to be balanced so that no particular sounds feel annoying or to loud.” - optional**

**“As an user, I’d like to see enemies exploding, not just a disappearing image” - optional**

**“As an user, I want the graphics to be clear-cut and easily spotted so that I have more control of what’s happening.” - optional**

These are all functions that purely enhance the entertainment value of the game while leaving the actual game pretty much the same. Most of the testing with external users resulted in items of this nature.
