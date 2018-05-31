package ua.prog.java.lesson11a;

public class Main {

	public static void main(String[] args) {
		Networking net = new Networking();
		net.checkSitesLinks("C:/java/lesson11/sites.txt");
		net.isSiteAvailable("http://javalife.kiev.ua");
	}

}
