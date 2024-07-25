# SPE summer resit project template

## Project Overview

Our project is a Simple Text Adventure Game (STAG) to be run on a server. This server will run on a UoB machine and will restart every hour to recieve any updates. In the game, the player will be given text prompts and must enter specific text inputs in order to progress through the game. Valid inputs will cause the player to be given new prompts whereas invalid inputs will be met with an error message.

## Group Details

Group Name: Functional

Deployment lab machine: it075753.wks.bris.ac.uk  

Group Team Channel: [Functional](https://teams.microsoft.com/l/channel/19%3A15b613f759a540f5a8e6c0999f825d5c%40thread.tacv2/Team%20Functional?groupId=929cf74e-332b-4f32-9b05-35403b3bb092&tenantId=b2e47f30-cd7d-4a4e-a5da-b18cf1a4151b&ngc=true)

## Architecture Diagram
![1721902721854](https://github.com/user-attachments/assets/75eb0a69-d2eb-4467-acc3-2e117fe79726)


## Class Diagram

![image](https://github.com/user-attachments/assets/e96fa26e-06c7-4835-917f-8e6dcd443545)

## Sequence Diagram

![1721901936448](https://github.com/user-attachments/assets/b27b96e6-1624-4d5f-8cf6-15ff877c606c)






## Overview

This template is the skeleton project that acts as the starting point for the SPE summer resit assignment.  
**Note:** that on MS Windows, you might need to replace `./mvnw` with `.\mvnw` in the following commands.

## Building the project

You can compile all of the source code in the project (which is located in `src/main/java`) using the following command:
```
./mvnw clean compile
```

## Running test cases

Run all of the test scripts (which are located in `src/test/java`) using the following command:
```
./mvnw test
```

## Manually running the Server and Client

You can start the server using the following command:
```
./mvnw exec:java@server
```

In a separate terminal, run the client using the following command:
```
./mvnw exec:java@client -Dexec.args="username"
```
Where `username` is the the name of the player



