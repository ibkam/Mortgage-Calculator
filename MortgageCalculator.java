package Main;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

//import java.util.Scanner;
public class MortgageCalculator {
	final static byte MONTHS_IN_YEARS = 12;
	final static byte PERCENT = 100;
	
	static Scanner  scanner = new Scanner(System.in);
	
	// Main function
	public static void main (String[] args) {
		int principal = (int) readInputs("Please input Principal($1k-$1M): ", 1000, 1_000_000);
		float annualInterestRate = (float) readInputs("Inputs Annual Interest Rate(1% -30%): ", 1, 30);
		byte years = (byte) readInputs("Input Period(1 year - 30 year): ", 1, 30);
		
		printMortgage(principal, annualInterestRate, years);
		
		paymentSchedule(principal, annualInterestRate, years);
		
		scanner.close();
		
	}
	
	// Prints the balance
	public static void paymentSchedule(int principal, float annualInterestRate, byte  years) {
		System.out.println();
		System.out.println("Payment Period");
		System.out.println("_ _ _ _ _ _");
		
		for (short month1 = 1; month1 <= years * MONTHS_IN_YEARS; month1++) {
            double balance = balanceOnMortgage(principal, annualInterestRate, years, month1);
            System.out.println("Payment Schedule for Month " + month1 + ": " +
                    NumberFormat.getCurrencyInstance().format(balance));
		}
	}
	
	// Prints the monthly mortgage payments
	public static void printMortgage(int principal, float annualInterestRate, byte years ) {
		System.out.println();
		System.out.println("Mortgage");
		System.out.println("_ _ _ _ _ _");
		
		double mortgage = calculateMortgage(principal, annualInterestRate, years);
		System.out.println("Monthly Payments: " + NumberFormat.getCurrencyInstance().format(mortgage));
	}
	
	// Functions that reads inputs from the scanner
	public static double readInputs(String prompt, double min, double max) {
		
		double value = 0;
		
		// reading inputs and catch mismatches
		try {
        while (true) {
            System.out.print(prompt);
            value = scanner.nextDouble();
            if (value >= min && value <= max) {
                break;
            }
            System.out.println("Enter a value between " + min + " and " + max);
        }
        
        // handling exceptions
		} catch (InputMismatchException e) {
			System.out.println("Please input correct vlaues: ");
			// Consumes the invalid inputs to clear the buffer
			scanner.nextLine();
			// recursive call to retry input until valid input is fed
			value = readInputs(prompt, min, max);
		
		}
        return value;
        
		}
        
	// Functions that Calculate balance on mortgage
	public static double balanceOnMortgage(int principal, float annualInterestRate, byte years, short numberOfPaymentsMade) {
		float montlyInterest = annualInterestRate / MONTHS_IN_YEARS / PERCENT;
		short numberOfPayments = (short) (years * 12);
		
		double balance = principal * (Math.pow(1 + montlyInterest, numberOfPayments)
				- (Math.pow(1 + montlyInterest, numberOfPaymentsMade))) 
				/ (Math.pow(1 + montlyInterest, numberOfPayments) - 1);
		
		return balance;
	}
		
	// Calculate mortgage
	public static double calculateMortgage(int principal, float annualInterestRate, byte years) {
		float montlyInterest = annualInterestRate / MONTHS_IN_YEARS / PERCENT;
		short numberOfPayments = (short) (years * 12);
		return principal * (montlyInterest * Math.pow(1 + montlyInterest, numberOfPayments))/(Math.pow(1 + montlyInterest, numberOfPayments));
			}
			
}
