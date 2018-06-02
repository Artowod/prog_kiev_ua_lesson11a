package ua.prog.java.lesson11a;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		Networking net = new Networking();
		net.checkSitesAvailability("C:/java/lesson11/sites.txt");

		String site = "http://programming.in.ua/programming/basisprogramming/144-programming-java-book.html";
		List<String> linksList = net.getAllLinksFromSite(site);
		net.putListToFile(linksList, "C:/java/lesson11/listOfLinks.txt");
		System.out.println("\nList of available links in the Site:");
		for (String link : linksList) {
			System.out.println(link);
		}
	}

}
