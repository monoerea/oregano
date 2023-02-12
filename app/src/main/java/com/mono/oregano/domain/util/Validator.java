package com.mono.oregano.domain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    String string;
    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).{8,20}$";
    Pattern PASSWORD = Pattern.compile(regex);
    Matcher matcher = PASSWORD.matcher(string);
}
