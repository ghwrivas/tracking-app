package ve.com.tracking.strings;

import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

public class TestStrings {
	public static void main(String[] args){
		System.out.println(RandomStringUtils.randomAlphanumeric(10));
		
		String regex = "\\S+";
		
		System.out.println(Pattern.matches(regex, " asdfasd "));
	}
}
