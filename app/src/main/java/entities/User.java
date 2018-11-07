package entities;

public class User {
    private String email;
    private String fullName;
    private String progress;

    public User(String email, String fullName, String progress) {
        this.email = email;
        this.fullName = fullName;
        this.progress = progress;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
