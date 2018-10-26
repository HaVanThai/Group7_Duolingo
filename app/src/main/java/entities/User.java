package entities;

public class User {
    private String email;
    private String fullName;

    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
