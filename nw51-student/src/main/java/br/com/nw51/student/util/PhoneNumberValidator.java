package br.com.nw51.student.util;

public class PhoneNumberValidator {

	public static void main(String[] args) {
		System.out.println("Phone number 963422736 validation result: "+validatePhoneNumber("963422736"));
		System.out.println("Phone number 96-3242-2736 validation result: "+validatePhoneNumber("96-3242-2736"));
		System.out.println("Phone number (96) 3242-2736 validation result: "+validatePhoneNumber("(96) 3242-2736"));
		System.out.println("Phone number (96) 3242 2736 validation result: "+validatePhoneNumber("(96) 3242 2736"));
		System.out.println("Phone number (96) 32422736 validation result: "+validatePhoneNumber("(96) 32422736"));
		System.out.println("Phone number 96 3242 2736 validation result: "+validatePhoneNumber("96 3242 2736"));
		System.out.println("Phone number 96 3242-2736 validation result: "+validatePhoneNumber("96 3242-2736"));
		System.out.println("Phone number 96 32422736 validation result: "+validatePhoneNumber("96 32422736"));
		
		
		/*System.out.println("Mobile 51983404455 validation result: "+isValidMobile("51983404455"));
		System.out.println("Mobile 5183404455 validation result: "+isValidMobile("5183404455"));
		System.out.println("Mobile 51-58340-4455 validation result: "+isValidMobile("51-58340-4455"));
		System.out.println("Mobile 51-8340-4455 validation result: "+isValidMobile("51-8340-4455"));
		System.out.println("Mobile 51 58340 4455 validation result: "+isValidMobile("51 58340 4455"));
		System.out.println("Mobile (51)-58340-4455 validation result: "+isValidMobile("(51)-58340-4455"));
		System.out.println("Mobile (51) 8340 4455 validation result: "+isValidMobile("(51) 8340 4455"));
		System.out.println("Mobile (51) 83404455 validation result: "+isValidMobile("(51) 83404455"));*/
	}
	
	private static boolean validatePhoneNumber(String phoneNo) {
		//validate phone numbers of format "963422736"
		if (phoneNo.matches("\\d{9}")) return true;
		//validating phone number with - or spaces
		else if(phoneNo.matches("\\d{2}[-\\s]\\d{4}[-\\s]?\\d{4}")) return true;		
		//validating phone number where area code is in braces ()
		else if(phoneNo.matches("\\(\\d{2}\\) \\d{4}[-\\s]?\\d{4}")) return true;
		//return false if nothing matches the input
		else return false;		
	}
	
	private static boolean isValidMobile (String mobile) {
		boolean isValid = false;
		//validate phone numbers of format "51983404455" 
		if (mobile.matches("\\d{10,11}")
				|| mobile.matches("\\d{2}[-\\s]\\d{4,5}[-\\s]?\\d{4}")
				|| mobile.matches("\\(\\d{2}\\)[-\\s]\\d{4,5}[-\\s]?\\d{4}")) {
			isValid = true;
		}
		
		/*if (phoneNo.matches("\\d{9}")) return true;
		//validating phone number with - or spaces
		else if(phoneNo.matches("\\d{2}[-\\s]\\d{4}[-\\s]?\\d{4}")) return true;		
		//validating phone number where area code is in braces ()
		else if(phoneNo.matches("\\(\\d{2}\\) \\d{4}[-\\s]?\\d{4}")) return true;
		//return false if nothing matches the input
*/		return isValid;
	}

}
