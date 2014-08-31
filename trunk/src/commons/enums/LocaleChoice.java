/**
 * 
 */
package commons.enums;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import components.GamesLibrarian;

/**
 * @author Naeregwen
 *
 */
public enum LocaleChoice {

	en_US ("en_USLabel", "en_USMnemonic", "en_USAccelerator", "/images/locales/en_US.png", "English (United States)"),
	fr_FR ("fr_FRLabel", "fr_FRMnemonic", "fr_FRAccelerator", "/images/locales/fr_FR.png", "Français (France)");
	
	public static Set<String> usablesLanguages = new HashSet<String>();
	
	String labelKey;
	String mnemonicKey;
	String acceleratorKey;
	String iconPath;
	String translation;
	
	public static final Pattern localePattern = Pattern.compile("^[a-z]{2}_[A-Z]{2}$");
	public static final Pattern countryPattern = Pattern.compile("^([a-z]{2})_[A-Z]{2}$");
	public static final Pattern languagePattern = Pattern.compile("^[a-z]{2}_([A-Z]{2})$");
	
	LocaleChoice(String labelKey, String mnemonicKey, String acceleratorKey, String iconPath,  String translation) {
		this.labelKey = labelKey;
		this.mnemonicKey = mnemonicKey;
		this.acceleratorKey = acceleratorKey;
		this.iconPath = iconPath;
		this.translation = translation;
	}
	
	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation == null ? this.name() : translation;
	}
	
	/**
	 * @return the labelKey
	 */
	public String getLabelKey() {
		return labelKey;
	}

	/**
	 * @return the mnemonicKey
	 */
	public String getMnemonicKey() {
		return mnemonicKey;
	}

	/**
	 * @return the acceleratorKey
	 */
	public String getAcceleratorKey() {
		return acceleratorKey;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @return a list of acceptable string values for parsing
	 */
	public static String getAcceptableValues() {
		StringBuilder acceptableValues = new StringBuilder("");
		for (LocaleChoice localeChoice : LocaleChoice.values()) 
			acceptableValues.append((acceptableValues.length() == 0 ? localeChoice : ", " + localeChoice) + ", " + localeChoice.ordinal());
		return acceptableValues.toString();	
	}
	
	/**
	 * Return the LocaleChoice the most corresponding to passed value
	 *  
	 * @param value the value to make pass the test
	 * @return LocaleChoice if found, null otherwise
	 */
	public static LocaleChoice getAcceptableValue (String value) {
		for (LocaleChoice localeChoice : LocaleChoice.values())
			if (localeChoice.toString().equalsIgnoreCase(value))
				return localeChoice;
		return null;
	}
	
	//
	// Helpers to manage Locales <-> ResourcesBundles
	//
	
	private static final String i18nBundlePackage = "i18n"; 
	private static final String defaultLanguageName = "default";
	private static boolean resourcesChecked = false;
	private static boolean resourcesValidated = false;
	
	private static String bundlePattern(String bundleName) {
		return "^" + bundleName + "(_\\w{2}(_\\w{2})?)?\\.properties$";
	}
	
	private static String toDirectoryPath(String bundlePackageName) {
		return bundlePackageName.replace('.', '/');
	}
	
	/**
	 * List directory contents for a resource folder. Not recursive.
	 * This is basically a brute-force implementation.
	 * Works for regular files and also JARs.
	 * 
	 * @author Greg Briggs
	 * @param clazz Any java class that lives in the same place as the resources you want.
	 * @param path Should end with "/", but not start with one.
	 * @return Just the name of each member item, not the full paths.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	static String[] getResourceListing(Class<?> clazz, String path) throws URISyntaxException, IOException {
		
		URL dirURL = clazz.getClassLoader().getResource(path);
		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			/* A file path: easy enough */
			return new File(dirURL.toURI()).list();
		}

		if (dirURL == null) {
			/*
			 * In case of a jar file, we can't actually find a directory. Have
			 * to assume the same jar as clazz.
			 */
			String me = clazz.getName().replace(".", "/") + ".class";
			dirURL = clazz.getClassLoader().getResource(me);
		}

		if (dirURL.getProtocol().equals("jar")) {
			/* A JAR path */
			String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); // strip out only the JAR file
			JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries in jar
			Set<String> result = new HashSet<String>(); // avoid duplicates in case it is a subdirectory
			while (entries.hasMoreElements()) {
				String name = entries.nextElement().getName();
				if (name.startsWith(path)) { // filter according to the path
					String entry = name.substring(path.length());
					int checkSubdir = entry.indexOf("/");
					if (checkSubdir >= 0) {
						// if it is a subdirectory, we just return the directory name
						entry = entry.substring(0, checkSubdir);
					}
					result.add(entry);
				}
			}
			jar.close();
			return result.toArray(new String[result.size()]);
		}
		throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
	}

	/**
	 * Get all the language codes available for the passed bundle
	 * 
	 * @param bundle
	 *            the bundle name
	 * @return a set of all the language codes available for this bundle
	 */
	private static Set<String> getAvailableResourceLanguages(String bundle) {

		final String bundleName = bundle;
		Set<String> languages = new TreeSet<String>();
		try {
			String[] fileListing = getResourceListing(GamesLibrarian.class, i18nBundlePackage + "/");
			for (String filename : fileListing) {
				if (filename.matches(bundlePattern(bundleName))) {
					File file = new File(filename);
					String language = file.getName().replaceAll("^" + bundleName + "(_)?|\\.properties$", "");
					if (language.equals(""))
						language = defaultLanguageName;
					languages.add(language);
				}
			} 
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
		return languages;
	}

	/**
	 * Check differences between keys set of each available language for this bundle
	 * <p>Warn: ResourceBundle.getBundle return a <code>Set</code> of all keys contained in this <code>ResourceBundle</code> and its parent bundles.</p>
	 * @param bundleName
	 * @param languages
	 * @return
	 */
	private static boolean checkAvailablesKeys(String bundleName, Set<String> languages) {
		
		class LanguageKeys {
			
			String language;
			Set<String> keys;
			
			LanguageKeys(String language, Set<String> keys) {
				this.language = language;
				this.keys = keys;
			}
		}
		
		Set<String> locales = new HashSet<String>(); 
		for (Locale locale : Locale.getAvailableLocales()) locales.add(locale.toString());
		Set<String> availablesLocales = new HashSet<String>(locales);
		Set<String> availablesLanguages = new HashSet<String>(languages);
		availablesLanguages.retainAll(availablesLocales);
		
		ArrayList<LanguageKeys> bundlesKeys = new ArrayList<LanguageKeys>();
		for (String language : availablesLanguages)
			bundlesKeys.add(new LanguageKeys(language, ResourceBundle.getBundle(toDirectoryPath(i18nBundlePackage) + "/" + bundleName, new Locale(language)).keySet()));
		
		boolean differences = false;
		for (LanguageKeys languageKeys : bundlesKeys) {
			for (LanguageKeys otherLanguageKeys : bundlesKeys) {
				if (!otherLanguageKeys.language.equals(languageKeys.language)) {
					
					Set<String> keysDifferences = new HashSet<String>(languageKeys.keys);
					if (keysDifferences.removeAll(otherLanguageKeys.keys)) {
						differences = true;
						for (String key : keysDifferences) 
							System.err.println("defaultLocale = " + Locale.getDefault().toString() + " - Found key '" + key + "' in " + bundleName + 
									(languageKeys.language.equals(defaultLanguageName) ? "" : ("_" + languageKeys.language)) + 
											" but missing same key in " + bundleName 
											+ (otherLanguageKeys.language.equals(defaultLanguageName) ? "" : ("_" + otherLanguageKeys.language)));
					}
					keysDifferences = new HashSet<String>(otherLanguageKeys.keys);
					if (keysDifferences.removeAll(languageKeys.keys)) {
						differences = true;
						for (String key : keysDifferences)
							System.err.println("defaultLocale = " + Locale.getDefault().toString() + " - Found key '" + key + "' in " + bundleName + 
									(otherLanguageKeys.language.equals(defaultLanguageName) ? "" : ("_" + otherLanguageKeys.language)) + 
											" but missing same key in " + bundleName 
											+ (languageKeys.language.equals(defaultLanguageName) ? "" : ("_" + languageKeys.language)));
					}
				}
			}
		}
		
		return !differences;
	}
	
	public static synchronized boolean checkAvailablesResources() {
		
		if (resourcesChecked) return resourcesValidated;
		resourcesValidated = false;
		
		Set<String> messagesLanguages = getAvailableResourceLanguages("messages");
		Set<String> UITextsLanguages = getAvailableResourceLanguages("UITexts");
		Set<String> resourcesLanguages = getAvailableResourceLanguages("resources");
		
		Set<String> messagesUiTextsDifferences = new HashSet<String>(messagesLanguages);
		messagesUiTextsDifferences.removeAll(UITextsLanguages);
		Set<String> UiTextsmessagesDifferences = new HashSet<String>(UITextsLanguages);
		UiTextsmessagesDifferences.removeAll(messagesLanguages);
		
		Set<String> messagesResourcesDifferences = new HashSet<String>(messagesLanguages);
		messagesResourcesDifferences.removeAll(resourcesLanguages);
		Set<String> resourcesMessagesDifferences = new HashSet<String>(resourcesLanguages);
		resourcesMessagesDifferences.removeAll(messagesLanguages);
		
		Set<String> UITextsResourcesDifferences = new HashSet<String>(UITextsLanguages);
		UITextsResourcesDifferences.removeAll(resourcesLanguages);
		Set<String> resourcesUITextsDifferences = new HashSet<String>(resourcesLanguages);
		resourcesUITextsDifferences.removeAll(UITextsLanguages);
		
		boolean differences = true;
		if (messagesUiTextsDifferences.size() == 0 && UiTextsmessagesDifferences.size() == 0 &&
				messagesResourcesDifferences.size() == 0 && resourcesMessagesDifferences.size() == 0 &&
				UITextsResourcesDifferences.size() == 0 && resourcesUITextsDifferences.size() == 0) {
			differences = false;
		} else {
			System.err.println("Missing resources : ");
			for (String language : messagesUiTextsDifferences)
				System.err.println("Found messages_" + language + " but missing locales files : UITexts_" + language + ".");
			for (String language : UiTextsmessagesDifferences)
				System.err.println("Found UITexts_" + language + " but missing locales files : messages_" + language + ".");
			for (String language : messagesResourcesDifferences)
				System.err.println("Found messages_" + language + " but missing locales files : resources_" + language + ".");
			for (String language : resourcesMessagesDifferences)
				System.err.println("Found resources_" + language + " but missing locales files : messages_" + language + ".");
			for (String language : UITextsResourcesDifferences)
				System.err.println("Found UITexts_" + language + " but missing locales files : resources_" + language + ".");
			for (String language : resourcesUITextsDifferences)
				System.err.println("Found resources_" + language + " but missing locales files : UITexts_" + language + ".");
		}
		
		boolean messagesKeysValidated = checkAvailablesKeys("messages", messagesLanguages);
		boolean UITextsKeysValidated = checkAvailablesKeys("UITexts", UITextsLanguages);
		boolean resourcesKeysValidated = checkAvailablesKeys("resources", resourcesLanguages);
		
		if (messagesKeysValidated && UITextsKeysValidated && resourcesKeysValidated && !differences)
			resourcesValidated = true;
		
		Set<String> languagesDifferences = new HashSet<String>(messagesLanguages);
		languagesDifferences.retainAll(UITextsLanguages);
		languagesDifferences.retainAll(resourcesLanguages);
		if (languagesDifferences.isEmpty()) {
			resourcesValidated = false;
		} else
			usablesLanguages = languagesDifferences;
			
		resourcesChecked = true;
		return resourcesValidated;
	}
	
}
