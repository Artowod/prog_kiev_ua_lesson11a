package ua.prog.java.lesson11a;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Networking {
	private String siteProtocol;
	private String siteHost;

	public Networking() {

	}

	public String getSiteProtocol() {
		return siteProtocol;
	}

	public String getSiteHost() {
		return siteHost;
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

	public void checkSitesAvailability(String filePath) {
		for (String linkToSite : getLinksFromFile(filePath)) {
			isSiteAvailable(linkToSite);
		}
	}

	public void getHeaders(URLConnection urlC) {
		Map<String, List<String>> headersFields = new HashMap<>();
		headersFields = urlC.getHeaderFields();
		Set<String> headersNames = new HashSet<>(headersFields.keySet());
		for (String key : headersNames) {
			System.out.println("\nKey: " + key);
			List<String> valueSet = headersFields.get(key);
			System.out.print("Values:");
			for (String eachHeaderValueLine : valueSet) {
				System.out.println(eachHeaderValueLine);
			}
		}
	}

	public String getSiteContent(String siteLink) {
		StringBuilder siteContent = new StringBuilder();
		try {
			URL url = new URL(siteLink);
			HttpURLConnection urlC = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
			String eachSiteLine = "";
			for (; (eachSiteLine = br.readLine()) != null;) {
				siteContent.append(eachSiteLine).append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return siteContent.toString();
	}

	public Boolean isSiteAvailable(String siteLink) {
		try {
			System.out.println("\nChecked Site: " + siteLink);
			URL url = new URL(siteLink);
			siteProtocol = url.getProtocol();
			siteHost = url.getHost();
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			System.out.println("  Site is available;\n  ResponceCode: " + urlConnection.getResponseCode());
			System.out.println("  ResponceMessage: " + urlConnection.getResponseMessage());
		} catch (IOException e) {
			System.out.println("  Site is unavailable.");
			return false;
		}
		return true;
	}

	private List<String> parseContentByDefinedTags(String startTag, String endTag, String content) {
		List<String> dataBetweenTags = new ArrayList<>();

		String[] splittedContentByTag = content.split(startTag);
		for (String eachSplittedPart : splittedContentByTag) {
			dataBetweenTags.add(eachSplittedPart.split(endTag)[0]);
		}
		dataBetweenTags.remove(0);
		return dataBetweenTags;
	}

	public List<String> getAllLinksFromSite(String site) {
		List<String> allLinksOnSite = new ArrayList<>();
		if (!isSiteAvailable(site)) {
			return null;
		}

		String siteContent = getSiteContent(site);

		for (String link : parseContentByDefinedTags("href=\"", "\"", siteContent)) {
			if (link.startsWith("/")) {
				allLinksOnSite.add(siteProtocol + "://" + siteHost + link);
			} else {
				allLinksOnSite.add(link);
			}
		}

		return allLinksOnSite;
	}

	public void putListToFile(List<String> list, String filePath) {
		try (PrintWriter pw = new PrintWriter(filePath)) {
			for (String eachLineOfFile : list) {
				pw.write(eachLineOfFile);
				pw.write(System.lineSeparator());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
