package telran.bullsandcowls;

public class Gamer {
    private String username;
    private String birthdate;

    public Gamer(String username) {
        this.username = username;
        this.birthdate = "01-01-1990"; // Пример даты рождения
    }

    public String getUsername() {
        return username;
    }

    public String getBirthdate() {
        return birthdate;
    }
}
