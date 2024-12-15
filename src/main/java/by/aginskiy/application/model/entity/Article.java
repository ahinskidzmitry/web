package by.aginskiy.application.model.entity;

import java.time.LocalDateTime;

/**
 * Entity of article.
 *
 * @author Dzmitry Ahinski
 */
public class Article extends AbstractEntity {

    private String topic;
    private String author;
    private String text;
    private String photoName;
    private LocalDateTime date;
    private int positiveRating;
    private int negativeRating;
    private int commentsNumber;
    private Rating ratingByUser;
    private boolean active;
    private boolean blocked;

    public Article(String topic, String author, String text) {
        this.topic = topic;
        this.author = author;
        this.text = text;
    }

    public Article(int id, String topic, String author, String text, String photoName, LocalDateTime date,
                   boolean active, boolean blocked) {
        super(id);
        this.topic = topic;
        this.author = author;
        this.text = text;
        this.photoName = photoName;
        this.date = date;
        this.active = active;
        this.blocked = blocked;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getPositiveRating() {
        return positiveRating;
    }

    public void setPositiveRating(int positiveRating) {
        this.positiveRating = positiveRating;
    }

    public int getNegativeRating() {
        return negativeRating;
    }

    public void setNegativeRating(int negativeRating) {
        this.negativeRating = negativeRating;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public Rating getRatingByUser() {
        return ratingByUser;
    }

    public void setRatingByUser(Rating ratingByUser) {
        this.ratingByUser = ratingByUser;
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

        Article article = (Article) o;

        if (positiveRating != article.positiveRating) return false;
        if (negativeRating != article.negativeRating) return false;
        if (commentsNumber != article.commentsNumber) return false;
        if (active != article.active) return false;
        if (blocked != article.blocked) return false;
        if (topic != null ? !topic.equals(article.topic) : article.topic != null) return false;
        if (author != null ? !author.equals(article.author) : article.author != null) return false;
        if (text != null ? !text.equals(article.text) : article.text != null) return false;
        if (photoName != null ? !photoName.equals(article.photoName) : article.photoName != null) return false;
        if (date != null ? !date.equals(article.date) : article.date != null) return false;
        return ratingByUser == article.ratingByUser;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (photoName != null ? photoName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + positiveRating;
        result = 31 * result + negativeRating;
        result = 31 * result + commentsNumber;
        result = 31 * result + (ratingByUser != null ? ratingByUser.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (blocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("topic='").append(topic).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", photoName='").append(photoName).append('\'');
        sb.append(", date=").append(date);
        sb.append(", positiveRating=").append(positiveRating);
        sb.append(", negativeRating=").append(negativeRating);
        sb.append(", commentsNumber=").append(commentsNumber);
        sb.append(", ratingByUser=").append(ratingByUser);
        sb.append(", active=").append(active);
        sb.append(", blocked=").append(blocked);
        sb.append('}');
        return sb.toString();
    }
}
