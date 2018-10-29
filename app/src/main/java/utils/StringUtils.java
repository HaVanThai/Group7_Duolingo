package utils;

public class StringUtils {
    public static String upperCaseFirstLetter(String input) {
        if(!input.trim().isEmpty()) {
            return Character.toString(input.charAt(0)).toUpperCase() + input.substring(1);
        }
        return input;
    }
}
