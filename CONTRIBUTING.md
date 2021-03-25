# When creating a pull request be sure your code is working and compiling

### Avoid creating random pull request for no reason
Your pull request should be related to one issue: https://github.com/Blackoutburst/Wally/issues \
If what you want to change is not defined as an issue, just create a new issue first.

## Be sure your code is clean
When you made a new pull request, take a look at Codacy report: https://app.codacy.com/gh/Blackoutburst/Wally/pullRequests \
Try to minimise at the maximum amount of code issues your pull request can add if possible 0 issues should be added.

## Respect overall project coding style
You a free to do basically what you want in term of coding style but respect some basic rules such as:
- Really long line should be avoided at the maximum.
- Really long function should be avoided, use multiple small function instead on just one big chunk.
- Do not split lines unless it's a long if statement.
- Put space between operators and keyword to make the code easier to read e.g `if (...)` `"..." + "..."`.
- Put opening bracket on the same line e.g `if (...) {` `void foo {` `for (...) {`.
- Do not use static import as they can lead to confusion.
- Always import classes you use do not use them directly like this e.g `utils.Stats.getUUID(...)`.
- Inside loops if a condition is supposed to skip the current loop iteration use `if (condition) continue;`

Just look at the rest of the code you are editing to be sure everything fit correctly and does not look off compared to the rest.

## Code modification
**DO NOT** touch the [Main.java](https://github.com/Blackoutburst/Wally/blob/master/src/main/Main.java). \
[event files](https://github.com/Blackoutburst/Wally/tree/master/src/event) should not be edited unless necessary. \
[comparators files](https://github.com/Blackoutburst/Wally/tree/master/src/comparators) should not be edited, you can add new one if needed but do not touch existing ones. \
[core files](https://github.com/Blackoutburst/Wally/tree/master/src/core) can be edited but be really careful with them as they can break a big part of the project. \

## Important
Every function inside the [Request](https://github.com/Blackoutburst/Wally/blob/master/src/core/Request.java) class must be used as less as possible. \
These function send request to api and lead to two issue.
- Api request are limited in usage per minute.
- Api request are slow and impacts the project speed a lot.

## Adding a new command
#### 1) Create a new file inside the [commands package](https://github.com/Blackoutburst/Wally/tree/master/src/commands)
Name your file CommandNameOfYourCommand with a class named the same inside. \
Your new command class should always extends CommandExecutable (Look inside other commands file to get exemple).

#### 2) Define your commands inside the [manager](https://github.com/Blackoutburst/Wally/blob/master/src/commands/CommandManager.java)
For that just add a new case inside the switch with the name of your command, put multiple case in the same line for aliases. \
In your new case create a new instance of your command class and directly call the `run()` function. \

Arguments are:
- command variable (it contains every vitals information such as commands name, sender, args, ...).
- ADMIN || EVERYONE (Define who can use the command).
- a String automatically displayed in case of a bad usage.

#### 3) Program your command
Do that inside the auto generated execute function inside your new command class, command permission is automatically made so you don't have to do it. \
Commands error case must be handled by returning specific function defined in [CommandError class](https://github.com/Blackoutburst/Wally/blob/master/src/core/CommandError.java). \
If your case does not exist just add it inside the [CommandError class](https://github.com/Blackoutburst/Wally/blob/master/src/core/CommandError.java). \

#### 4) Adding new error case
Every error case added inside [CommandError class](https://github.com/Blackoutburst/Wally/blob/master/src/core/CommandError.java) must have their error message defined inside the [configuration file](https://github.com/Blackoutburst/Wally/blob/master/config.json).
