import java.util.HashSet;

import edu.duke.*;

public class Tester {
	public void testSliceString() {
		VigenereBreaker vb = new VigenereBreaker();
		String testString = "abcdefghijklm";
		System.out.println(vb.sliceString(testString, 0,  3));
		System.out.println(vb.sliceString(testString, 1,  3));
		System.out.println(vb.sliceString(testString, 2,  3));
		System.out.println(vb.sliceString(testString, 0,  4));
		System.out.println(vb.sliceString(testString, 1,  4));
		System.out.println(vb.sliceString(testString, 2,  4));
		System.out.println(vb.sliceString(testString, 3,  4));
		System.out.println(vb.sliceString(testString, 0,  5));
		System.out.println(vb.sliceString(testString, 1,  5));
		System.out.println(vb.sliceString(testString, 2,  5));
		System.out.println(vb.sliceString(testString, 3,  5));
		System.out.println(vb.sliceString(testString, 4,  5));
	}
	
	public void testCaesarCipher() {
		int key = 4;
		CaesarCipher cc = new CaesarCipher(key);
		String message = "I love you";
		System.out.println("Message: " + message);
		String encrypted = cc.encrypt(message);
		System.out.println("Encrypted: " + encrypted);
		String decrypted = cc.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted);
	}
	
	public void testCaesarCracker() {
		CaesarCracker cb = new CaesarCracker();
		FileResource fr = new FileResource("VigenereTestData/titus-small_key5.txt");
		String message = fr.asString();
		System.out.println("Message: " + message);
		String decrypted = cb.decrypt(message);
		System.out.println("Decrypted: " + decrypted);
		CaesarCracker cb2 = new CaesarCracker('a');
		FileResource fr2 = new FileResource("VigenereTestData/oslusiadas_key17.txt");
		String message2 = fr2.asString();
		System.out.println("Message 2: " + message2);
		String decrypted2 = cb2.decrypt(message2);
		System.out.println("Decrypted 2: " + decrypted2);
	}
	
	private int[] encodeKey(String key) {
		key = key.toLowerCase();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		int[] keyNums = new int[key.length()];
		for(int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int num = alphabet.indexOf(c);
			keyNums[i] = num;
		}
		return keyNums;
	}
	
	public void testVigenereCipher() {
		int[] key = encodeKey("rome");
		FileResource fr = new FileResource("VigenereTestData/titus-small.txt");
		String message = fr.asString();
		System.out.println("Message: " + message);
		VigenereCipher vc = new VigenereCipher(key);
		String encrypted = vc.encrypt(message);
		System.out.println("Encrypted: " + encrypted);
		String decrypted = vc.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted);
	}
	
	public void testTryKeyLength(int keyLen) {
		FileResource fr = new FileResource();
		String secretMessage = fr.asString();
		VigenereBreaker vb = new VigenereBreaker();
		int[] keyArray = vb.tryKeyLength(secretMessage, keyLen, 'e');
		for(int i = 0; i < keyArray.length; i++) {
			System.out.print(keyArray[i] + " ");
		}
	}
	
	public void testBreakVigenere() {
		VigenereBreaker vb = new VigenereBreaker();
		vb.breakVigenere();
	}
	
	
	//have to make countChars public to do this again
	/*public void testCountChars() {
		VigenereBreaker vb = new VigenereBreaker();
		System.out.println("Select dictionary file");
		FileResource dRes = new FileResource();
		HashSet<String> dictionary = vb.readDictionary(dRes);
		System.out.println(vb.countChars(dictionary));
	}*/
	
	public void testMostCommonCharIn() {
		VigenereBreaker vb = new VigenereBreaker();
		System.out.println("Select dictionary file");
		FileResource dRes = new FileResource();
		HashSet<String> dictionary = vb.readDictionary(dRes);
		System.out.println("Most common char: " + vb.mostCommonCharIn(dictionary));
	}
	
	public void testBreakForAllLangs() {
		int maxKeyLength = 100;
		System.out.println("Select message file");
		FileResource fr = new FileResource();
		String encrypted = fr.asString();
		VigenereBreaker vb = new VigenereBreaker();
		System.out.println(vb.breakForAllLangs(encrypted, maxKeyLength));
	}
	


}
