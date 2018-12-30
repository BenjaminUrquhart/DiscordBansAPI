###############################################################################
**DiscordList (and therefore DBans) no longer exists. This wrapper is defunct**
###############################################################################
# DiscordBansAPI
Java library to interface with the Discord Bans API

An example program is shown in src/net/dbans/test

# How to use

1. Add the latest jarfile release to your buildpath

2. Import the API with `import net.dbans.*;`

3. Create an API object 
```java
DiscordBanAPI api = new DiscordBanAPI("your token here");
//If you want to catch exceptions...
DiscordBanAPI api = new DiscordBanAPI("your token here", true);
```
4. Make calls
```java
//Single user checking
//Returns null for all exceptions except for NumberFormatException, throws IllegalArgumentException in that case
//Throws all exceptions if verbosity is enabled
String id = "user id here";
UserInfo info = api.checkUser(id);
//Bulk checking
List<String> ids = /*List of user IDs*/;
List<UserInfo> list = api.checkUsers(ids);
```
Note: if an exception is thrown the methods will return `null` unless verbosity is enabled, then an exception will be thrown instead

You can see if a user is banned using `UserInfo#isBanned()`:
```java
if(info.isBanned()){ //returns true/false
  //do stuff
}
```
Getting user ID:
```java
System.out.println(info.getUserId());
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
  System.out.println(info.getCaseId()); //If the user isn't banned, this will throw an IllegalStateException
}
```
Getting Direct URLs to Proofs:
```java
if(info.isBanned()){
  String[] urls = info.getDirectProofUrls();
  for(String url: urls){
    System.out.println(url);
  }
}
```
# Standalone Client
Executing the jarfile in a terminal window will give you a small interactive client.
```
java -jar path/to/jarfile.jar <token>
```
This is mainly for checking your token's validity. Just enter a user ID at the prompt to see the results.

Multiple users can be checked by inputting a comma-separated string of user ids. Example: `123456789123456789,98765432109876543210`
