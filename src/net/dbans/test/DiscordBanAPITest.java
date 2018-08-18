package net.dbans.test;

import java.util.Scanner;
import net.dbans.DiscordBanAPI;
import net.dbans.UserInfo;

public class DiscordBanAPITest {
	
	public static void main(String[] args) {
		DiscordBanAPI api = new DiscordBanAPI("token", true);
		Scanner sc = new Scanner(System.in);
		UserInfo info;
		while(true) {
			try {
				System.out.print("ID: ");
				info = api.checkUser(sc.nextLine());
				System.out.println(info);
				Thread.sleep(500);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
