import java.text.ParseException;

public class VodoApp {

	public static void main(String[] args) throws NoSuchFieldException, ParseException {
		UserInterface UI = new UserInterface(args[0]);
		UI.run();
	}
}
