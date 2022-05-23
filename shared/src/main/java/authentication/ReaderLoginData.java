package authentication;

public class ReaderLoginData {
    public static Account logIn(){
        System.out.println("Please, enter your login (if you haven't got any account enter 'Register'): ");
        String login = System.console().readLine();
        if (login.trim().equalsIgnoreCase("register")) return register();
        System.out.println("Please, enter your password: ");
        char [] password = System.console().readPassword();
        return new Account(login, String.valueOf(password), TypeAuthentication.LOGIN);
    }

    private static Account register(){
        System.out.println("Please, think of your login : ");
        String login = System.console().readLine();
        if (login.trim().equalsIgnoreCase("register")) return new Account(login, "", TypeAuthentication.LOGIN);
        System.out.println("Please, think of your password: ");
        char [] password = System.console().readPassword();
        return new Account(login, String.valueOf(password), TypeAuthentication.REGISTRATION);
    }
}
