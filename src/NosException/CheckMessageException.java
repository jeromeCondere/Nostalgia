package NosException;

public class CheckMessageException extends Exception {
 public CheckMessageException(String s)
 {
	 super("Not a valid message: "+s);
 }
}
