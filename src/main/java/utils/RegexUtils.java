package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexUtils {
    public static final Pattern EMPTY_OBJECT = Pattern.compile(".+\\{\\s*}(,)?");
    public static final Pattern EMPTY_LINE = Pattern.compile("(?m)^[ \t]*\r?\n");
}
