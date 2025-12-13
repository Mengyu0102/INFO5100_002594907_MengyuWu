import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

    public static void main(String[] args) {
        System.out.println("=== Class Exercise # 4: Regular Expression Demonstration ===");


        // 1. Quantifiers: Match one or more letters
        demonstratePattern("Quantifier (+): Match one or more letters",
                "^[a-zA-Z]+$",
                "HelloWorld", // Positive Case
                "123",        // Negative Case
                "Hello World!"); // Negative Case (space and special char)

        // 2. Character Classes: Match exactly three digits
        demonstratePattern("Character Class (\\d): Match exactly three digits",
                "^\\d{3}$",
                "901",       // Positive Case
                "12A",       // Negative Case (contains letter)
                "1234");     // Negative Case (too many digits)

        // 3. Boundary Matchers: Starts with 'Java' and ends with '.'
        // Note: '.' is a special character in RegEx, must be escaped with '\\.'
        demonstratePattern("Boundary Matchers (^ and $): Starts with 'Java' and ends with '.'",
                "^Java.*\\.$",
                "Java is powerful.", // Positive Case
                "Java is not.",      // Positive Case
                "Java is simple",    // Negative Case (missing dot at end)
                "not Java.");        // Negative Case (doesn't start with Java)

        // 4. Special Character: Match 'a' followed by any two characters followed by 'c'
        demonstratePattern("Special Character (.): Match 'a' followed by any two characters followed by 'c'",
                "a..c",
                "aXyc",     // Positive Case
                "az9c",     // Positive Case
                "axc",      // Negative Case (only one character in between)
                "abcde");   // Negative Case (ends too late)

        // 5. Grouping and Alternation: Match 'Apple' OR 'Orange'
        demonstratePattern("Grouping and Alternation (|): Match 'Apple' OR 'Orange'",
                "^(Apple|Orange)$",
                "Apple",    // Positive Case
                "Orange",   // Positive Case
                "Banana",   // Negative Case
                "apple");   // Negative Case (case sensitive)

        System.out.println("\n========================================================");
    }

    /**
     * Core demonstration method: tests the given pattern against subject strings.
     * @param description Description of the pattern.
     * @param regexPattern The regular expression pattern to test.
     * @param subjectStrings A variable number of strings to test the pattern against.
     */
    private static void demonstratePattern(String description, String regexPattern, String... subjectStrings) {
        System.out.println("\n--- Demonstration: " + description + " ---");
        System.out.println("Pattern: \"" + regexPattern + "\"");

        // 编译正则表达式
        Pattern p = Pattern.compile(regexPattern);

        // 遍历所有测试字符串
        for (String subject : subjectStrings) {
            Matcher m = p.matcher(subject);
            boolean matches = m.matches(); // matches() 尝试匹配整个输入序列

            String result = matches ? "MATCHES (Positive Case)" : "DOES NOT MATCH (Negative Case)";

            System.out.printf("  String: \"%s\" -> %s\n", subject, result);
        }
    }
} // 类的结尾