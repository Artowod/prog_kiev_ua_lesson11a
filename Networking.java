package ua.prog.java.lesson11a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Networking {

	public Networking() {

	}

	private List<String> getLinksFromFile(String filePath) {
		List<String> linksList = new ArrayList<>();
		String lineFromFile;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			for (; (lineFromFile = br.readLine()) != null;) {
				linksList.add(lineFromFile);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return linksList;
	}

	public void showHeader(URLConnection urlC) {
		Map<String, List<String>> headersFields = new HashMap<>();
		headersFields = urlC.getHeaderFields();
		Set<String> headersNames = new HashSet<>(headersFields.keySet());
		for (String key : headersNames) {
			System.out.println("Key: " + headersNames);
			List<String> valueSet = headersFields.get(key);
			System.out.println("Values:");
			for (String eachHeaderValueLine : valueSet) {
				System.out.println(eachHeaderValueLine);
			}
		}

	}

	private void getSiteIP(String siteLink) {
		/* definition of Network Protocol has to be missing in link */
		try {
			InetAddress inetAddress = InetAddress.getByName(siteLink);
			System.out.println("  Site found:\n  HostName/IP address: " + inetAddress);

		} catch (UnknownHostException e) {
			System.out.println("  Error: Current Site is unavailable or wrongly defined.");
		}
	}

	private void checkSiteAvailability(String siteLink) {
		try {
			URL url = new URL(siteLink);
			URLConnection urlC = url.openConnection();
			if (urlC.getContentLength() == -1) {
				System.out.println("  Error: Failed to verify connection with this Resource");
			}
			System.out.println("  ContentType: " + urlC.getContentType());
		} catch (IOException e) {
			System.out.println("  Error: Failed to open connection with this Resource");
		}
	}

	public void checkSitesLinks(String filePath) {
		for (String linkToSite : getLinksFromFile(filePath)) {
			System.out.println("\nSite " + linkToSite + " :");
			System.out.println("Check with InetAddress:");
			getSiteIP(linkToSite);
			System.out.println("Check with URL/URLConnection:");
			checkSiteAvailability(linkToSite);
		}
	}
}
