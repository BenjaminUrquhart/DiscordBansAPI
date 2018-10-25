package net.dbans.test;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import net.dbans.DiscordBanAPI;
import net.dbans.UserInfo;

public class DiscordBanAPITest {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		DiscordBanAPI api = new DiscordBanAPI(args[0], true);
		Scanner sc = new Scanner(System.in);
		UserInfo info;
		String input;
		while(true) {
			try {
				System.out.print("ID: ");
				input = sc.nextLine();
				if(input.contains(",")) {
					ArrayList<String> ids = new ArrayList<>();
					String[] split = input.replace(" ", "").split("\\,");
					for(String s : split) {
						ids.add(s);
					}
					System.out.println("Checking " + ids.size() + " users...");
					List<UserInfo> results = api.checkUsers(ids);
					System.out.println(results.size() + " results");
					for(UserInfo u : results) {
						System.out.println("---------------------------------------------------");
						System.out.println(u);
					}
					System.out.println("---------------------------------------------------");
				}
				else {
					info = api.checkUser(input);
					System.out.println(info);
				}
				Thread.sleep(500);
			}
			catch(Exception e) {
				e.printStackTrace();
				if(e instanceof java.util.NoSuchElementException) {
					System.exit(0);
				}
			}
		}
	}
}
