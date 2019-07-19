package reg.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlyInteger {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("(^[0-8]*$)");
		
		int onlyNum;
		String inputVal;
		Scanner iStream = new Scanner(System.in);
		
		inputVal = iStream.nextLine();
		Matcher m = p.matcher(inputVal);
		
		if(m.find()) {
			onlyNum = Integer.parseInt(inputVal);
			System.out.println(onlyNum);
		}
		else {
			System.out.println("숫자가 아니다.");
		}
		
		iStream.close();
	}
}
