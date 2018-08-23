# DiscordBansAPI
Java API to interface with the Discord Bans API

An example program is shown in src/net/dbans/test

# How to use

1. Add the latest jarfile release to your buildpath

2. Import the API with `import net.dbans.*;`

3. Create an API object 
```java
DiscordBanAPI api = new DiscordBanAPI("your token here");
//If you want stack traces to be printed...
DiscordBanAPI api = new DiscordBanAPI("your token here", true);
```
4. Make calls
```java
//Single user checking
//Returns null for all exceptions except for NumberFormatException, throws IllegalArgumentException in that case
String id = "user id here";
UserInfo info = api.checkUser(id);
//Bulk checking
List<UserInfo> ids = /*List of user IDs*/;
List<UserInfo> list = api.checkUsers(ids);
```
Note: if an exception is thrown the methods will return `null`

You can see if a user is banned using `UserInfo.isBanned()`:
```java
if(info.isBanned()){ //returns true/false
  //do stuff
}
```
Getting user ID:
```java
System.out.println(info.getUserId() /* Replace with getUserIdLong() to return a long */
```
If you want to know why a user is banned:
```java
if(info.isBanned()){
  System.out.println(info.getReason()); //If the user isn't banned, this will throw an IllegalStateException
}
```
Geting proof URL:
```java
if(info.isBanned()){
  System.out.println(info.getProof()); //If the user isn't banned, this will throw an IllegalStateException
}
```
Getting case ID:
```java
if(info.isBanned()){
  System.out.println(info.getCaseId() /* Replace with getCaseIdLong() to return a long */); //If the user isn't banned, this will throw an IllegalStateException
}
```
