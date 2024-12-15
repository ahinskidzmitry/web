package by.aginskiy.application.model.entity;

/**
 * Entity of user.
 *
 * @author Dzmitry Ahinski
 */
public class User extends AbstractEntity {

    private String name;
    private String email;
    private String login;
    private String photoName;
    private boolean blocked;
    private UserRole role;

    public User(String name, String email, String login, boolean blocked, UserRole role) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.blocked = blocked;
        this.role = role;
    }


    public User(int id, String name, String email, String login, String photoName, boolean blocked, UserRole role) {
        super(id);
        this.name = name;
        this.email = email;
        this.login = login;
        this.photoName = photoName;
        this.blocked = blocked;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (blocked != user.blocked) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (photoName != null ? !photoName.equals(user.photoName) : user.photoName != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (photoName != null ? photoName.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", photoName='").append(photoName).append('\'');
        sb.append(", blocked=").append(blocked);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
