
public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	public void run() {
		Tester test = new Tester();
		
		//testing given classes
	
		//test.testCaesarCipher();
		//test.testCaesarCracker();
		//test.testVigenereCipher();
		
		//testing VigenereBreaker methods I wrote for known language and keylength
		
		//test.testSliceString();
		//test.testTryKeyLength(38);
		//test.testBreakVigenere(4, 'e');
		
		//testing updated breakVigenere for unknown key
		
		//test.testBreakVigenere('e');
		//test.testCountWords(57);
		
		//testing code for any language
		//test.testCountChars();
		// test.testMostCommonCharIn();
		//test.testFileResource();
		test.testBreakForAllLangs();
	}

}
