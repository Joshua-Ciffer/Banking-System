package src.atm.account;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides the framework and methods for creating and utilizing different types of accounts.
 * <br><br>
 * This class cannot be instantiated.
 * 
 * @author Joshua Ciffer
 * @version 01/18/2018
 */
public abstract class Account {

	/**
	 * Provides means of storage and retrieval of accounts based on their account number.
	 */
	private static final HashMap<Integer, Account> ACCOUNT_MAP = new HashMap<>();

	/**
	 * Provides the date and time formatted for use in recording account history. Example: 01/18/2017 8:58 PM.
	 */
	static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

	/**
	 * Unique 6 digit number for identifying an account.
	 */
	final int ACCOUNT_NUMBER;

	/**
	 * The account holder's name.
	 */
	String accountName;

	/**
	 * The account's 4 digit PIN.
	 */
	Pin accountPin;

	/**
	 * Log of all transaction history and account changes.
	 */
	String accountHistory;

	/**
	 * Constructor called by subclasses that creates an account with the user's name, 4 digit PIN, and account history. The account history for all of accounts
	 * starts with an entry with the date and time the account was created. When the account is created, it is added to the account map.
	 * 
	 * @param accountName - The account holder's name.
	 * @param accountPin - The account's 4 digit PIN.
	 * @param accountHistory - A log of all transaction history and account changes.
	 */
	Account(String accountName, Pin accountPin) {
		ACCOUNT_NUMBER = GENERATE_ACCOUNT_NUMBER();
		this.accountName = accountName;
		this.accountPin = accountPin;
		accountHistory = DATE_TIME.format(LocalDateTime.now()) + " - Account Opened.\n";
		ACCOUNT_MAP.put(ACCOUNT_NUMBER, this);
	}

	/**
	 * Generates a random 6 digit account number that is currently not in use by any accounts in the account map.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return accountNumber - Unique 6 digit integer.
	 */
	private static final int GENERATE_ACCOUNT_NUMBER() {
		int accountNumber;
		do {
			accountNumber = (int)(Math.random() * 1_000_000);
			if ((accountNumber < 100_000) || (accountNumber > 999_999)) {
				continue;
			} else if (ACCOUNT_MAP.containsKey(accountNumber)) {
				continue;
			} else {
				break;
			}
		} while (true);
		return accountNumber;
	}

	/**
	 * Retrieves an account from the account map with a specified account number. The method also checks to make sure the correct PIN has been entered.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @param accountNumber - The account to be returned.
	 * @param accountPin - The account's PIN.
	 * @return The account with the given login credentials.
	 * @throws IllegalArgumentException - Thrown if an incorrect PIN is entered.
	 * @throws NullPointerException - Thrown if an account with the given account number cannot be found or does not exist.
	 */
	public static final Account GET_ACCOUNT(int accountNumber, String accountPin) throws IllegalArgumentException, NullPointerException {
		if (ACCOUNT_EXISTS(accountNumber) && Pin.IS_CORRECT_PIN(accountNumber, accountPin)) {
			return ACCOUNT_MAP.get(accountNumber);
		} else {
			return null;
		}
	}

	/**
	 * Checks the account map to see if an account with the given account number has been created.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @param accountNumber - The account number to check for.
	 * @return True - If an account with the given account number has been found in the account map.
	 * @throws NullPointerException - Thrown if an account with the given account number cannot be found or does not exist.
	 */
	public static final boolean ACCOUNT_EXISTS(int accountNumber) throws NullPointerException {
		if (ACCOUNT_MAP.containsKey(accountNumber)) {
			return true;
		} else {
			throw new NullPointerException("This account does not exist. Please check the account number.");
		}
	}

	/**
	 * Changes the PIN of the account the method is invoked on. User needs to provide their current PIN to authenticate.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @param oldPin - The current PIN.
	 * @param newPin - The new PIN.
	 * @param confirmPin - The new PIN entered again for confirmation.
	 * @throws IllegalArgumentException - Thrown if the current PIN is incorrect, or the new PIN is not valid, or if the new PIN and confirm PIN do not match.
	 */
	public final void changeAccountPin(String oldPin, String newPin, String confirmPin) throws IllegalArgumentException {
		accountPin.changePin(ACCOUNT_NUMBER, oldPin, newPin, confirmPin);
		accountHistory = accountHistory + DATE_TIME.format(LocalDateTime.now()) + " - Pin Changed.\n";
	}

	/**
	 * Closes the account that the method is invoked on. User needs to provide their current PIN to authenticate.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @param accountPin - The user's PIN.
	 * @throws IllegalArgumentException - Thrown if the PIN is incorrect.
	 */
	public final void closeAccount(String accountPin) throws IllegalArgumentException {
		if (Pin.IS_CORRECT_PIN(ACCOUNT_NUMBER, accountPin)) {
			ACCOUNT_MAP.remove(ACCOUNT_NUMBER, this);
		}
	}

	/**
	 * Returns an instance of the account map.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return A reference to the account map.
	 */
	public static final HashMap<Integer, Account> GET_ACCOUNT_MAP() {
		return ACCOUNT_MAP;
	}

	/**
	 * Checks the content of two accounts to see if they are equal.
	 * <br><br>
	 * This method must be implemented.
	 * 
	 * @param account - The account to test equality with.
	 * @return True - If the accounts are equal.
	 * <br> False - If the accounts are not equal.
	 */
	@Override
	public abstract boolean equals(Object account);

	/**
	 * Returns a string representation of the content of this account.
	 * <br><br>
	 * This method must be implemented.
	 * 
	 * @return A string representation of all of the data contained in this account.
	 */
	@Override
	public abstract String toString();

	/**
	 * Returns the account's number.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return The account's number.
	 */
	public final int getAccountNumber() {
		return ACCOUNT_NUMBER;
	}

	/**
	 * Returns the account holder's name.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return The account holder's name.
	 */
	public final String getAccountName() {
		return accountName;
	}

	/**
	 * Returns the account's 4 digit PIN.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return The account's 4 digit PIN.
	 */
	public final Pin getAccountPin() {
		return accountPin;
	}

	/**
	 * Returns the account's history record.
	 * <br><br>
	 * This method cannot be overridden.
	 * 
	 * @return The record of account history.
	 */
	public final String getAccountHistory() {
		return accountHistory;
	}

	/**
	 * Changes the account holder's name.
	 * <br><br>
	 * This method is only accessible from within src.atm.account.
	 * This method cannot be overridden.
	 * 
	 * @param accountName - The new account holder's name.
	 */
	final void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Changes the account's PIN.
	 * <br><br>
	 * This method is only accessible from within src.atm.account.
	 * This method cannot be overridden.
	 * 
	 * @param accountPin - The new account PIN.
	 */
	final void setAccountPin(Pin accountPin) {
		this.accountPin = accountPin;
	}

	/**
	 * Changes the account's history log.
	 * <br><br>
	 * This method is only accessible from within src.atm.account.
	 * This method cannot be overridden.
	 * 
	 * @param accountHistory - The new account history log.
	 */
	final void setAccountHistory(String accountHistory) {
		this.accountHistory = accountHistory;
	}

}