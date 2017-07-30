This is my solution to the technical test. I have had about 4 hours this weekend to chip away at it, it's been a very busy week!

To run the program pull the code and enter the bin folder. Run the command 'java Controller <command file path>'.

========== Approach ========== 

My approach was to create a minimal viable product and then chip away by adding a feature, testing and releasing a working version before moving on to another feature. The test stages show the feature implementation progression.

Test 1 : Can the controller pass strings to the drone and the drone send them back to be spat out as the output.
Test 2 : Can the controller pass commands to the drone and can the drone then properly decode them and handle command errors.
Test 3 : Drone start stop logic from controller commands.
Test 4 : Drone movement logic from controller commands.
Test 5 : Boundary collision logic from controller commands.

- At this point the logic was changed so the drone polled the controller for the next command as opposed to the controller sending commands, this gives greater control to the drone eg. commands being executed in serial for different durations.

Test 6 : Toggling lights from commands.
Test 7 : Navigating home.


=========== Testing ============

The testing suite is basic. A file containing commands is given as a CLI parameter when running the controller (main class).
A simulated drone is created and runs the commands, passing its new state after each back as a string which is printed on the CLI. The output can then be compared to a pre-calculated output to check the behaviour is as expected. I am satisfied that I have tested a good coverage of boundary and erroneous cases to say that the solution is correct.

========== Evaluation ========== 

I believe the solution is good:

+ Most features have been implemented.
+ Code is reasonably self-commenting.
+ The solution is not overly complicated.
+ The solution is not implemented in an unreasonably inefficient manner.

I would improve with more time by:

+ Integrating automated testing eg. JUNIT.
+ Complete navigate home feature. (Need to avoid continious precision looping).
+ Implement the time duration controlled features - light flash, horn sound, alert (horn x3).
