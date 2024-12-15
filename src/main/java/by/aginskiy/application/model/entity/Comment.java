package by.aginskiy.application.model.entity;

import java.time.LocalDateTime;

/**
 * Entity of comment.
 *
 * @author Dzmitry Ahinski
 */
public class Comment extends AbstractEntity {

    private String text;
    private String author;
    private LocalDateTime date;
    private String photoName;
    private boolean likedByUser;
    private int likesNumber;
    private boolean active;
    private boolean blocked;

    public Comment(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public Comment(int id, String text, String author, LocalDateTime date, String photoName, boolean active,
                   boolean blocked) {
        super(id);
        this.text = text;
        this.author = author;
        this.date = date;
        this.photoName = photoName;
        this.active = active;
        this.blocked = blocked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (likedByUser != comment.likedByUser) return false;
        if (likesNumber != comment.likesNumber) return false;
        if (active != comment.active) return false;
        if (blocked != comment.blocked) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        return photoName != null ? photoName.equals(comment.photoName) : comment.photoName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (photoName != null ? photoName.hashCode() : 0);
        result = 31 * result + (likedByUser ? 1 : 0);
        result = 31 * result + likesNumber;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("text='").append(text).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", date=").append(date);
        sb.append(", photoName='").append(photoName).append('\'');
        sb.append(", likedByUser=").append(likedByUser);
        sb.append(", likesNumber=").append(likesNumber);
        sb.append(", active=").append(active);
        sb.append(", blocked=").append(blocked);
        sb.append('}');
        return sb.toString();
    }
}
