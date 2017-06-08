package com.github.zjor;

import org.springframework.core.env.PropertySource;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Populates random property value.
 * Supports:
 * ${random.string.<length>}
 */
public class RandomPropertySource extends PropertySource<Random> {

    private static final Pattern STRING_REGEX = Pattern.compile("random\\.string\\.(\\d+)");

    public RandomPropertySource(String name, Random source) {
        super(name, source);
    }

    public RandomPropertySource(String name) {
        super(name, new Random());
    }

    public Object getProperty(String s) {
        Matcher m = STRING_REGEX.matcher(s);
        if (m.find()) {
            return getRandomString(Integer.parseInt(m.group(1)));
        }
        return null;
    }

    private String getRandomString(int length) {
        Random random = getSource();
        int limit = 'z' - 'a';
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append((char)(random.nextInt(limit) + 'a'));
        }
        return builder.toString();
    }
}
