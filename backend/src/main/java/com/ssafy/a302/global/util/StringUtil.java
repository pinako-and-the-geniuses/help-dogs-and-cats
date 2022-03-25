package com.ssafy.a302.global.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    public String mask(String input) {
        /**
         * 12       ->  1*
         * 123      ->  1*3
         * 1234     ->  1**4
         * 12345    ->  12**5
         * 123456   ->  12***6
         * 1234567  ->  12***67
         * 12345678 ->  12****78
         * 123456789->  12****789
         */
        final int SIZE = input.length();
        final int START_POINT = SIZE <= 4 ? 0 : 1;
        final int HALF = SIZE / 2 + START_POINT;
        StringBuilder maskedData = new StringBuilder(input);
        for (int i = 1 + START_POINT; i <= HALF; i++) {
            maskedData.setCharAt(i, '*');
        }

        return maskedData.toString();
    }
}
