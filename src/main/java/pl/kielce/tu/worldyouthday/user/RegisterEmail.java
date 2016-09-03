package pl.kielce.tu.worldyouthday.user;


public class RegisterEmail {

    private final String userEmail;

    private final String userFirstName;

    private final String userLastName;

    private final String userLogin;

    private final String userPassword;

    public RegisterEmail(User user, String password) {
        this.userEmail = user.getEmail();
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();
        this.userLogin = user.getLogin();
        this.userPassword = password;
    }

    public String to() {
        return userEmail;
    }

    public String subject() {
        return "Potwierdzenie rejestracji na Światowe Dni Młodzieży";
    }

    public String text() {
        return "Witaj " + userFirstName + " " + userLastName + "!"
                + "\n"
                + "\n"
                + "Administrator utworzył ci konto"
                + "\n"
                + "\n"
                + "Twój login to: " + userLogin
                + "\n"
                + "Twoje hasło to: " + userPassword
                + "\n"
                + "\n"
                + "Pozdrawia zespół WorldYouthDay.";
    }
}