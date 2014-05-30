#Post Mortem Report
**Group 2**
*Software Engineering Project*

**By:**
Simon Eliasson
Jonas Enlund
Anders Eriksson
Josef Haddad

##1. Summary
This report will describe the working process during the development of the Android application Space Shooter. Space shooter is a side-scrolling arcade game in a futuristic setting where the player’s goal is to score as high as possible by defeating enemies and other dangers. The different working methods that was used to develop the product will be processed, evaluated and discussed. There will also be a section with reflections and a discussion about the project as a whole.

##2. The working process
The working progress during the development of Space Shooter has been somewhat bumpy and inconsistent at times. The main reason for this have been the issue of actually meeting all group members at a time. Most group members have had large projects aside from Space Shooter, which have made it hard to find time slots which all members have the possibility to attend. Furthermore, only one of the members live in Gothenburg, which have complicated this issue even more. Despite this problem, the development progress have been kept in a certain pace, thanks to the ambitious group members and the methods that were used instead. This section will describe these methods.

###2.1 Scrum
Scrum has been utilized to a certain extent although meetings have been rather few. The goal was to have at least one meeting every week. This goal was mostly fulfilled, and the weeks that didn’t contain a meeting at chalmers often had a complementary skype meeting. The main goals of these meetings were to establish a sense of what state the product was currently in, how the team was doing in relation to the time plan and what would be implemented next. This was done mainly by keeping a backlog of prioritized features and implementations, coupled with user and/or developer stories. Since we did not had any Scrum master, the backlog was created and maintained in a democratic sense, and members had the permission to add new features to the backlog at will, at any time. These new items would then be discussed at the next meeting. The amount of freedom that individuals had during the project was relatively high.

###2.2 Pair Programming
All members of the group have utilized pair programming during the development process. Although meetings with all members have been somewhat rare, working with each other have actually been the norm. Members have been encouraged to meet when possible and cooperate, especially when merges have taken place between two of the different responsibility areas.

###2.3 Software Testing
It was extremely hard at times to identify certain issues within the game, as some bugs appeared seemingly at random and therefore were hard to recreate and fix. Because of this, the testing of the application have been done mainly by group members, playing the game a lot. Group sessions where the game was intensely played with the sole purpose of crashing it led to the discovery of a lot of bugs and other issues that was taken care of at the same time. There was also established rule that no git branches could be re-merged with the main branch without the new features being thoroughly tested.

Apart from this, some JUnit testing was also done in order to make sure that some vital functions within the game worked as intended. When some of the code was pretty much guaranteed to work, many uncertainties disappeared and debugging went faster.

###2.4 User/Acceptance Testing
Many items in the backlog have been added thanks to people outside of the development process that have tried the application. User testing has allowed the group to identify problems that was previously hard to recognize. These issues have mainly been about the steering of the ship, what different graphics look like or just general ideas of how to make the game better. To let people outside of the team play the game while being observed and listened to, have improved the quality of our game significantly.

###2.5. Areas of Responsibility
As stated earlier, all members of the team have had their own areas of responsibility. The reason behind this was to get a more organized group, minimize the amount of git conflicts and to improve the overall quality of the application. The idea was for the members to focus their work on the areas for which they were responsible. Of course this was not a hard-drawn line that could never be passed, but it generally served its purposes well.

##3. Working Method Evaluation/Discussion
Section 2. described the working process that the group utilized. In this section, different methods will be evaluated and discussed in order to illustrate their advantages and disadvantages.

###3.1 Scrum Discussion
Although Scrum has been an important part of the development, many things were changed to better fit the group. With less meetings than what the group wanted, it was hard to follow the method as strictly as first intended. More freedom was the solution to a slowly expanding backlog and members with no remaining tasks to handle. This worked very well for the group, which quite often produced more every week than what was actually defined at the meetings. Since every team member had his own area of responsibility, code conflicts were still very rare, despite the somewhat chaotic nature of the new working process. This system would however make sprints almost irrelevant, as it implied free-for-all implementation, rather than fixed weekly plans. Organization was maintained through constant communication over skype and facebook, which also meant that the lack of weekly plans did not result in any form of mess. The parts of Scrum that we did utilize were responsible for keeping the group on track and especially the backlog have been important to establish a consensus among members of what to do at all times.

We understand that this system probably would not work very well in larger groups and that Scrum should ideally be followed as much as possible. However, given the circumstances, we think that our changes have been mostly well thought out and executed. Our general thoughts about the method is that although it is well specified, it leaves room for modification, which should make it viable in almost any working process.

###3.2 Pair Programming Discussion
Pair programming have perhaps been the most utilized working method during the project. The benefits of working side by side in small groups might not be that apparent for someone who have never tested it before, but obvious to those who have. Not only did it make working a lot more fun, but it actually made working easier and more efficient. Team members have worked in pairs or triplets during most of the development time. Since it has never been forced in any way, there have been seemingly no apparent backdraws to this system, but a lot of winnings. Some of them was that:

- There was always a possibility to get direct feedback from another person. This often meant that the quality of newly implemented features could be improved directly after their initial implementation.
- You always got more than one perspective of every aspect of the working process. Problems and issues that arose got fixed a lot quicker when two minds collaborated. Design and functionality of the application could be continuously discussed during the development, which meant that a consensus regarding the product was easier to establish and maintain.
- When code was merged together, it was done during pair programming sessions where the authors could work together to make the merging smooth and elegant.
- The increased sense of better teamwork and collaboration made the members more interested and engaged in the project, which improved productivity.

Pair programming might not suit all projects as well as it suited this one. Especially if there are problems within the group that makes working together more difficult. In our case, the working atmosphere has been friendly and ambitious which have made the method highly enjoyable.

###3.3 Software Testing Discussion
Testing software is always highly prioritized in any software development project. A main issue was the distinction between what could be tested with JUnit testing and what would be better to test in other ways. Some underlying vital structures of the game, such as the vector class, are easily testable with JUnit testing. While enemy formation generation is much easier to verify by actually playing the game. We decided to have a member of the group responsible for the JUnit testing, while all members of the group continuously kept playing the game, reporting any bugs they encountered. Bugs were then mainly fixed by the member who was responsible for the part of the game where the issues were discovered. This system was completely made up by us and followed no established pattern or method. It worked well though. In general, we feel that there can be no software development without proper testing.

###3.4 User/Acceptance Testing Discussion
User testing has, as stated in section 2.4, been of much value to the project. Outside user criticism have been of a very high priority. Although it is often hard for a developer to see the faults of his own creation, there might be others who identify them with ease. In the development of a game, it could easily be argued that user testing reaches it’s peak value. Not only must user interaction be intuitive and easily understandable. Music, sound effects, graphics, enemy scripting and game balance issues are all subject to the player’s opinions and thoughts. These opinions and thoughts define what makes a good or bad game and are therefore central in any game development process. Although we have not conducted any user testing in large-scale or organized manners, we tried to expose the game to people outside of the project as often as possible in order to get a better understanding of our own game. 

This kind of testing might not be vital in all software. But user testing can never hurt a product, especially if it is developed for entertainment or service purposes.

###3.5 Areas of Responsibility Discussion
This topic has already been discussed to some extent during section 3. To have different areas of responsibility have helped in other parts of the project and made it more organized and efficient. In our case, members have been comfortable with their respective areas. We feel that most working processes should benefit from a similar system.

##4. Individual Report
This section will give a report of our individual areas of responsibility and estimations for how much time each individual have spent on the different parts of the project. In general, we have had a fairly equal share of working with software, organization and documentation. 

###4.1 Simon - Lead Game Design
Simon has mainly been responsible for the game design of the actual game stage of the application. He has mainly been working with graphics, sound effects, music and game features in general, as well as with the code that handle these features. Also designed the TCP system for global high-scores.

###4.2 Jonas - Lead Android Development
Jonas has been responsible for the Android modules parts of the program and XML. He has implemented the menus and their features, as well as implementing google related services like ads etc. He has also been working with git in order to make these external dependencies work in a correct manner.

###4.3 Anders - Lead Coding
Anders have been responsible for the architecture of the game and the back-end parts of the software. He has designed and implemented the game engine as well as a number of game features, while debugging and optimizing the software in general. also spent a lot of time researching possible design choices for games on Android.

###4.4 Josef - Lead Database Manager
Josef has been responsible for the database part of the program. More specifically, the local high-score SQL database that is stored in the phone memory.

###4.5 Common Grounds
Since we have been working with pair and triplet programming a lot there have been a substantial amount of co-operation time spent across different areas of the application. Testing and debugging, for example, have been an ongoing process where everyone in the team have helped each other out. Working in Android is a new experience to us all, and this fact have of course resulted in extra consumed time. Documentation have been handled by all members of the group in an equal fashion. We also decided to present the game all together.

During the first week of the project, it was said that 20 hours would be asked out of every group member each week. In most cases, this time goal have been met and exceeded well beyond the initial goal, and have done so from start to finish. Of course, individual variation have existed, which will be apparent in the individual group evaluations.

##5. What Worked Well
We have a very positive view of our work during this project. Most of the time it has been interesting and fun, and the members of the group have been enthusiastic from the start. The work pace has been consistent and well organized. Scrum slowed us down at first, but after modifying the system to better fit our needs (See 3.1), this issue disappeared. To always have a continuously expanding backlog meant that all members always had something to do. We had constant communications open over Skype, Facebook and SMS-Grupp so that everyone could ask questions, discuss issues and help each other out at any time. Although git and gitHub was a new way of organizing code to the team, synchronizing the software went fairly smoothly with few conflicts or other errors.

##6. What Worked Worse
As mentioned before, the meeting issue have been the only real major setback during the development. We’d like to follow Scrum as detailed as possible, but this has unfortunately not been a possibility. also, at first the backlog organization was a bit messy. We added items that we wanted to add but didn’t properly sort them or classify them. This didn’t lead to any major issues, but turned focus from what was most important to implement, to what was fun to implement. Throughout the project, we increasingly understood the value of a well organized backlog, which helped us develop the finished product that we have today. 

Apart from these problems, the main obstacles that we faced had to do with Android related issues, such as design decisions or a lack of understanding of the Android OS. For example, there is a small stuttering that appear now and then in the game. After a lot of research it was concluded that this issue is a problem with the Android OS and not our code specifically. This could be solved by converting the graphics into OpenGL, a decision that we did not take due to uncertainty about whether it would be a viable option or not.

Also, when the project first started, we were five members of the team. Unfortunately, after a few weeks, Isabel decided to leave the project.

##7. Non-Process-Related Discussion
The following is how we described the app that we would deliver at the end of the course:

*We will have a finished level where you steer a player ship with a joystick and shoot with a button. There will be enemies coming in waves at random who you can kill and you will get points for killing them. When the level is finished your score will be updated on a working leaderboard to compare with friends and other players. We will also have a working menu system, sound effects and music.*

The only item that is not implemented today, that is featured in this description, is the button for shooting. After a while, we realized that pressing a button to shoot was mostly annoying and added no value to the game experience. The button was instead exchanged for the activatable powerup slots that we have today. 

We are very satisfied with the resulting product, as it has a lot more features than what we imagined at the start of the course. When we specified our vision (See documentation), we aimed high and really didn’t expect to fulfill it, while the MVP was what we thought was the realistic goal. Today we are somewhere in between and the game is also fairly balanced.

We also made some non-process related decisions. These can be seen in the design decisions document in our documentation.

##8. Group Discussion
As stated earlier, work within the group have been smooth and enjoyable. There have been practically no conflicts within the group during this assignment other than conflicting ideas, which resulted in discussion, that in the end have been beneficial for the project anyway. We have worked well together and already have possible plans for working again in the future, continuing on the app. Apart from previously discussed meeting issues, there have been no real problems within the group. In general, group members have been helpful and ambitious, and there has been a common sense of constantly looking forward.

##9. Learnings for Future Projects
First of all, given that a future project group consists of people working at the same place, meeting schedules should be more regular and consistent than what we have had during this course. Morning meetings almost every day would probably help to organize the project.
