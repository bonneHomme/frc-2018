# FRC 2018
Programming repository of team Arctos 6135 in ![FIRST POWER UP 2018](https://upload.wikimedia.org/wikipedia/commons/2/2d/2018_FIRST_Power_Up_game_logo.png)
Visit us at <a href="http://arctos6135.ca">arctos6135.ca</a>!
## Branch `demo`
The code in this branch is for demonstration purposes only. This branch includes a "demo mode" that can be toggled on or off, the ability to disable the operator's controller, and reduced info output. This branch should never be merged with master or vice versa. Use `cherry-pick` to merge individual commits instead if necessary.

## Building This Project
In order to keep the code more organized, some parts of our code were packaged into jars and put under the `/lib/` directory. 
However, this introduces some minor inconveniences when building.<br><br>
For those looking to build the robot code, first include all the jars under the `/lib/` directory into the build path,
then make symlinks from `user_home/wpilib/user/java/lib` for all the jars.<br><br>
For Windows, this command is `mklink path\to\symlink path\to\target`, and for UNIX it is `ln -s path/to/target path/to/symlink`.<br><br>
Note that making a shortcut will not work, as during the building process build.xml looks for actual files.
