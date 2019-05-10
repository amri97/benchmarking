# Room vs greenDAO for Android: a comparative analysis of performance of Room and greenDAO on Android #
DT133G Final Project

### This repository contains source code for my Bachelor thesis in Software Engineering ###

### [Project proposal](project-proposal)
#### Project summary
Android is the most popular operating system used in smart phones. In order to save structured 
data in Android apps, developers have many frameworks to choose from (SQLite, Room persistence
Library(Room), ActiveAndroid, greenDAO, Realm, OrmLite, etc). This plethora of database libraries
implies Android developers must make a choice for storing structured data. 

In order to make this choice, developers have increasingly considered application performance 
as a major factor . This is because application performance, in terms of response time as 
well as memory consumption (CPU and RAM) plays an important role in user-perceived quality of 
apps. Using these third-party libraries can also increase the overall application size which 
in turn increases the memory usage of the application. 

There is limited empirical studies that compare the performance of the different Android 
database frameworks. In a study to understand the energy, performance and programming effort
trade-offs of SQLite, ActiveAndroid, greenDAO, Realm and OrmLite, Pu J. et al., (2016) found 
that SQLite performs best while ActiveAndroid performs worst. They also concluded that greenDAO 
performed best among the ORMs.  They however never compared the performance of Room which is 
the recommended framework by Google. This project adds to this knowledge of performance
analysis by analyzing the performance of Room and greenDAO. It also seeks to know how much
more of the application size is increased when using any of these frameworks.  

* Version 1.0


### How do I get set up? ###
## TODO
* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

This is an individual project work. However, you are free to contribute
any idea that might help improve on the work. 

### Who do I talk to? ###
For any enquiries, contact me at [lobe1602@student.miun.se](mailto:lobe1602@student.miun.se)