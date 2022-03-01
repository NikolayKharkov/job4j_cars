package models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date created;
    private boolean sold;
    @ManyToOne
    @JoinColumn(name = "mark_id", foreignKey = @ForeignKey(name = "CarMark_ID_FK"))
    private CarMark carMark;
    @ManyToOne
    @JoinColumn(name = "body_id", foreignKey = @ForeignKey(name = "CarBody_ID_FK"))
    private CarBody carBody;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "User_ID_FK"))
    private User user;

    public static Post of(String name,
                          String description,
                          Boolean sold,
                          CarMark carMark,
                          CarBody carBody,
                          User user) {
        Post result = new Post();
        result.name = name;
        result.description = description;
        result.created = new Date(System.currentTimeMillis());
        result.sold = sold;
        result.carMark = carMark;
        result.carBody = carBody;
        result.user = user;
        return result;
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", sold=" + sold
                + ", carMark=" + carMark
                + ", carBody=" + carBody
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public CarMark getCarMark() {
        return carMark;
    }

    public void setCarMark(CarMark carMark) {
        this.carMark = carMark;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && sold == post.sold
                && Objects.equals(name, post.name)
                && Objects.equals(description, post.description)
                && Objects.equals(created, post.created)
                && Objects.equals(carMark, post.carMark)
                && Objects.equals(carBody, post.carBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created, sold, carMark, carBody);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
