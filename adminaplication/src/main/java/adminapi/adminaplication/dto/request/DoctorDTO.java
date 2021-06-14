package adminapi.adminaplication.dto.request;

public class DoctorDTO {
    private int id;
    private String username;
    private String email;
    private String password;

    public DoctorDTO(){}

    public DoctorDTO(String username, String email, String password) {
        this.email = email;
        this.password = password;
    }

    public DoctorDTO(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}