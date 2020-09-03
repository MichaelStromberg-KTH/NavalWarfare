# NavalWarfare

Date: 1996-08-12

This was a fun project Rasmus Kaj and I did in 1996 for the KTH MUMS course. The goal was to create a web version of the old game "battleship" using Java applets (which were all the rage at the time). These days applet support has been removed from most web browsers, but theoretically someone could still use appletviewer from an older JDK to play the game.

I have converted some of our online documentation in GFM. Enjoy!

## Preparations

We took a look at one or two implementations of the 'battlefield' game already implemented on the net. Seeing them we decided that a) they were too slow and b) they were too ugly. So we decided we needed to implement the game in java.

## Goals

After the initial design decisions were made, we got the base game working. The plan was to introduce entire fleets of modern naval ships from different countries. However, those goals were tempered by the fact that Rasmus and I were already working around the clock on fulltime jobs.

## Main Java Program

We have split the java code into two packages, sea and naval. The partition between the two was defined as 'everything that might be used in for example a minesweeper' at sea and everything that is really specific to Naval Warfare in naval. The reason for this is it should make the code cleaner and easier to understand, but also that Rasmus plans to write that game eventually (Editor's note: my guess is 14 years later, that never happened). We also considered creating a package (misc or something) for really generic classes, but we haven't found the need for those classes yet.

The applet used on the 'one player game' page is naval.OnePlayer . It is a simple panel with a Label for status messages and the actual game, naval.Naval as Components.

## The Game

When the user clicks the mouse in the game area, Sea.mouseUp gets called by awt. It calculates the Coord that corresponds to the mouse click's position and calls Naval.touch (since Naval extends sea)... Do I really have to get this detailed? You can just look at the source code, you know...

## The Future of NW

By no means are we finished just because the MUMS project deadline has arrived (Editor's note: serious development never really happened afterwards). During the whole process, Rasmus and I became so fanatical about NW, that we plan on continuing with the project to offer a cool, extremely high-bandwidth game onto the Internet.

Here are the ideas that we came up with:
* More kinds of ships
* Get multiplayer going
* Help for the users
* Animate explosions etc.

