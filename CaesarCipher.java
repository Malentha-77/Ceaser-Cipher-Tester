import java.util.Scanner;

// CaesarCipher class implements a Caesar cipher for encrypting and decrypting text.
// It supports interactive mode and command-line operations including encryption, decryption,
// brute force decryption, and frequency-based shift guessing.
public class CaesarCipher {
    // Main method: Entry point of the program. Handles both interactive and command-line modes.
    public static void main(String[] args) {
        if (args.length == 0) {
            // Interactive mode: Prompts user for input via console.
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter text to encrypt: ");
            String inputText = scanner.nextLine();

            System.out.print("Enter shift key (0-25): ");
            int shiftKey = scanner.nextInt();

            String encrypted = encrypt(inputText, shiftKey);
            System.out.println("Encrypted text: " + encrypted);
        } else {
            // Command-line mode: Processes flags and arguments.
            if (args.length < 2) {
                System.out.println("Usage: java CaesarCipher [-e|-d|-b|-g] <text> [shift]");
                return;
            }
            String flag = args[0];
            String text = args[1];
            if (flag.equals("-e")) {
                // Encrypt mode: Requires text and shift.
                if (args.length < 3) {
                    System.out.println("Usage: java CaesarCipher -e <text> <shift>");
                    return;
                }
                int shift = Integer.parseInt(args[2]);
                String encrypted = encrypt(text, shift);
                System.out.println("Encrypted: " + encrypted);
            } else if (flag.equals("-d")) {
                // Decrypt mode: Requires text and shift.
                if (args.length < 3) {
                    System.out.println("Usage: java CaesarCipher -d <text> <shift>");
                    return;
                }
                int shift = Integer.parseInt(args[2]);
                String decrypted = decrypt(text, shift);
                System.out.println("Decrypted: " + decrypted);
            } else if (flag.equals("-b")) {
                // Brute force mode: Tries all shifts to decrypt.
                bruteForceDecrypt(text);
            } else if (flag.equals("-g")) {
                // Guess mode: Uses frequency analysis to guess shift and decrypt.
                guessAndDecrypt(text);
            } else {
                System.out.println("Invalid flag. Use -e, -d, -b, or -g");
            }
        }
    }

    // Encrypt method: Shifts each letter in the text by the given shift amount.
    // Non-letter characters remain unchanged. Handles both uppercase and lowercase.
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                // Determine base ('a' or 'A') based on case.
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                // Calculate shifted character, wrapping around alphabet.
                char shifted = (char) ((character - base + shift) % 26 + base);
                result.append(shifted);
            } else {
                // Keep non-letters as is.
                result.append(character);
            }
        }

        return result.toString();
    }

    // Decrypt method: Reverses encryption by shifting in the opposite direction.
    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift);
    }

    // Brute force decrypt: Tries decrypting with all possible shifts (1-25) and prints results.
    public static void bruteForceDecrypt(String text) {
        System.out.println("Brute force decryption:");
        for (int shift = 1; shift < 26; shift++) {
            String decrypted = decrypt(text, shift);
            System.out.println("Shift " + shift + ": " + decrypted);
        }
    }

    // Guess and decrypt: Analyzes letter frequency to guess the shift, then decrypts.
    // Assumes 'e' is the most common letter in English.
    public static void guessAndDecrypt(String text) {
        char mostFrequent = findMostFrequentLetter(text);
        int shift = (mostFrequent - 'e' + 26) % 26;
        String decrypted = decrypt(text, shift);
        System.out.println("Guessed shift: " + shift + " (most frequent letter: " + mostFrequent + ")");
        System.out.println("Decrypted: " + decrypted);
    }

    // Find most frequent letter: Counts occurrences of each letter (case-insensitive)
    // and returns the most frequent one.
    public static char findMostFrequentLetter(String text) {
        int[] freq = new int[26]; // Array for a-z frequencies.
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                freq[lower - 'a']++;
            }
        }
        int maxIndex = 0;
        for (int i = 1; i < 26; i++) {
            if (freq[i] > freq[maxIndex]) {
                maxIndex = i;
            }
        }
        return (char) ('a' + maxIndex);
    }
}