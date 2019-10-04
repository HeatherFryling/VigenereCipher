import edu.duke.*;
import java.util.*;
import java.io.File;
public class VigenereBreaker {
	private HashMap<String, HashSet<String>> languageDicts;
	
	public VigenereBreaker() {
		languageDicts = new HashMap<String, HashSet<String>>();
	}
	
	private void buildLanguageDicts() {
		System.out.println("Select dictionary files");
		DirectoryResource dr = new DirectoryResource();
		for(File file : dr.selectedFiles()) {
			String language = file.getName();
			FileResource fr = new FileResource(file);
			HashSet<String> dictionary = readDictionary(fr);
			languageDicts.put(language, dictionary);
		}
	}
	
	public String sliceString(String message, int whichSlice, int totalSlices) {
		StringBuilder slice = new StringBuilder();
		for(int k = whichSlice; k < message.length(); k += totalSlices) {
			char c = message.charAt(k);
			slice.append(c);
		}
		return slice.toString();
	}
	
	private String[] buildSliceArray(String string, int klength) {
		String[] slices = new String[klength];
		for(int k = 0; k < klength; k++) {
			String slice = sliceString(string, k, klength);
			slices[k] = slice;
		}
		return slices;
	}
	
	
	private HashMap<Character, Integer> countChars(HashSet<String> dictionary){
		HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i = 0; i < alphabet.length(); i++) {
			counts.put(alphabet.charAt(i), 0);
		}
		for(String word : dictionary) {
			for(int k = 0; k < word.length(); k++) {
				char c = word.charAt(k);
				if(counts.keySet().contains(c)) {
				counts.put(c, counts.get(c) +1);
				}
			}
			
		}
		return counts;	
	}
	
	public char mostCommonCharIn(HashSet<String> dictionary) {
		int max = 0;
		HashMap<Character, Integer> charCounts = countChars(dictionary);
		char most = 'a';
		for(Character c : charCounts.keySet()) {
			int count = charCounts.get(c);
			if(count > max) {
				max = count;
				most = c;
			}
		}
		return most;
	}
	
	public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
		//returns a Vigenere key for a given length
		String[] sliceArray = buildSliceArray(encrypted, klength);
		int[] nums = new int[klength];
		for(int i = 0; i < sliceArray.length; i++) {
			String slice = sliceArray[i];
			CaesarCracker cb = new CaesarCracker(mostCommon);
			int key = cb.getKey(slice);
			nums[i] = key;			
		}
		return nums;
	}
	
	public void breakVigenere() {
		System.out.println("Select encrypted file");
		FileResource fr = new FileResource();
		String secretMessage = fr.asString();
		//System.out.println("Select dictionary file");
		//FileResource fr2 = new FileResource();
		//HashSet<String> dictionary = readDictionary(fr2);
		//int[] inKey = tryKeyLength(secretMessage, keyLength, mostCommon);
		//VigenereCipher vg = new VigenereCipher(inKey);
		int maxKeyLength = 100;
		String decrypted = breakForAllLangs(secretMessage, maxKeyLength);
		System.out.println(decrypted);		//take substring here to get beginning
	}
	
	public HashSet<String> readDictionary(FileResource fr){
		HashSet<String> dictionary = new HashSet<String>();
		//fr contains one word per line
		for(String word : fr.lines()) {
			dictionary.add(word.toLowerCase());
		}
		return dictionary;
	}
	
	public int countWords(String message, HashSet<String> dictionary) {
		//counts the number of real words in a decrypted message
		String[] words = message.split("\\W+");
		int count = 0;
		for(String word: words) {
			word = word.toLowerCase();
			if(dictionary.contains(word)) {
				count += 1;
			}
		}
		return count;
	}
	
	public String breakForLanguage(String encrypted, HashSet<String> dictionary, int maxKeyLength) {
		String answer = "No answer";
		int maxWords = 0;
		char c = mostCommonCharIn(dictionary);
		ArrayList<Integer> finalKey = new ArrayList<Integer>();
		for(int i = 1; i <= maxKeyLength; i++) {
			int[] key = tryKeyLength(encrypted, i, c);
			VigenereCipher vc = new VigenereCipher(key);
			String message = vc.decrypt(encrypted);
			int numWords = countWords(message, dictionary);
			if(numWords > maxWords) { //being tricky
				maxWords = numWords;
				answer = message;
				finalKey.clear();
				for(int k = 0; k < key.length; k++) {
					finalKey.add(key[k]);
				}
			}
		}
		String lineOne = "Valid words: " + maxWords;
		StringBuilder keyString = new StringBuilder();
		for(int i = 0; i < finalKey.size(); i++) {
			keyString.append(finalKey.get(i));
			keyString.append(' ');
		}
		String lineTwo = "Key: " + keyString.toString();
		String lineThree = "Key length: " + finalKey.size();
		return lineOne + "\n" + lineTwo + "\n" + lineThree + "\n" + answer;
	}

	
	public String breakForAllLangs(String encrypted, int maxKeyLength) {
		buildLanguageDicts();
		int maxWords = 0;
		String languageFound = "No language yet";
		String answer = "No answer yet";
		String finalAnswer = "All of them";
		for(String language : languageDicts.keySet()) {
			HashSet<String> dictionary = languageDicts.get(language);
			String possAnswer = breakForLanguage(encrypted, dictionary, maxKeyLength);
			int words = countWords(possAnswer, dictionary);
			if(words > maxWords) {
				maxWords = words;
				answer = possAnswer;
				languageFound = language;
			}
		}
		String lineOne = "Language: " + languageFound;
		String lineTwo = "Max words: " + maxWords;
		finalAnswer = lineOne + "\n" + lineTwo + "\n" + answer;
		return finalAnswer;
	}
}

