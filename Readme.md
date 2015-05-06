# Recognize 

### Tagline: ***Beat the Blur***

### Team:
Gabi Ghali (gghali@ncsu.edu), Xavier Primus (xjprimus@ncsu.edu), Justin Toniazzo (jjtoniaz@ncsu.edu), Sakthi Narayanan Thirukonda Viswanath (sthiruk@ncsu.edu), Chetan Pawar (chpawar@ncsu.edu)

### What is Recognize?
Recognize is a simple image-based quiz game. There are plenty of apps out there that let you participate in quiz games, but most of these are primarily text based. Recognize is different in that it uses images. Specifically, players are asked to identify the origin of a specific image as it slowly depixilates on the screen. For example, one of our categories is "Famous Moustaches". Players might be shown a heavily distorted image of Burt Reynolds and challenged to select his image from a group of similar looking people as the main image slowly becomes more clear.

Recognize is a continuing project. That is, the current version of the application is the summation of work from a series of past semesters. The last iteration (Spring 2015) was primarily focused on user experience and interface design. While making these UX improvements, however, we noticed several backend changes that would make the application much better from a development perspective. The primary area of improvement we see is in the pixelation of images. Currently, all images are stored statically, but this means the application is rather large. Applying these pixelations programmatically would significantly improve both application size and performance. It would also make it considerably easier for new albums to be created, which is another area in which we think the app could use work. Currently, new albums need to be created manually by people with access to the project’s source code, but if it were possible to create these albums from within the application itself it would make for a much more interesting social platform.

### Key features of the App:
Once user opens this game application, he can play this enjoying game with different types of albums. 
- Friendly detective greeter: For fun, we have added a friendly detective greeter character on top of home screen which shows different random texts each time you come to home screen. Purpose of this function was to make our app more interactive.
- Help oevrlay screen: User can click on Help(?) button and there is help overlay screen opens up on top of main activity which shows all the actions that you can take on the main page. This is good for first time users to tell them how to start the game, how to scroll through albums, how to open settings option, click on particular album from the scrollable album menu and play.
- Settings button: User can click on Settings (gear icon) at the bottom right to open settings. It allows user to select the difficulty level as Easy, Medium and Hard. User can choose to turn on/off the sound in the game.
- Once user clicks on album from the scrollable album options, a modal window opens up showing the highest score for that particular album for that user. Then user can cancel or go ahead with playing for that album.
- Game play screen: On the game play screen, one main image in pixelated form will be shown to user and timer will be set to 10. Main image will depixelate itself as the timer reduces to zero. There will be 6 answer choices given below from which user have to select the correct answer, based on which points will be added to score.
- Fun Facts: Fun facts are displayed for each question about the correct answer for some of the albums. Also at the end of game, when scores are shown, fun fact about an album is shown in 'Did you know?' window along with the score.
- Once all the questions are over, score will be displayed with option to replay with the same album or go to home screen to choose different album or he can share the score result with any other application. For example, user can post on facebook or twitter or share with friend on watsapp that "Hey, I just scored x on xxx album on Recognize application. Download this app from (github link is provided)"

### Future Work:
- The apk is very large now. It has all the images, for all the intermediate depixelation levels stored as separate image in the source code, which makes it very bulky. If we can incorporate the pixelation and depixlation algorithm, then we would need to store only the original image and not all the intermediate images.
- It would be cool if user can create an album from the app with his/her own choice of photos. This again would be possible if we can have pixelation algorithm.
- Difficulty settings to play at easy and medium level is not currently working now. We can have this functionality working by allowing user to choose from less number of options during the game based on difficulty level selected.

### Link to github repo:
https://github.com/chetanpawar0989/Recognize-App/tree/score_screen

### Link to screencast video:
http://youtu.be/kM9TsPP2Gfc
