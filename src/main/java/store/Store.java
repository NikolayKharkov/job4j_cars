package store;

import models.CarBody;
import models.CarMark;
import models.Post;
import models.User;

import java.util.List;

public interface Store {

    User addUser(User user);
    User findUserByEmail(String email);
    User findUserById(int id);

    Post addPost(Post post);
    void deletePost(Post post);
    void updatePost(Post post);
    void updatePostStatus(int idPost);
    void updatePostPhotoStatus(int idPost);
    List<Post> findAllPosts();
    Post findPostById(int id);
    List<Post> findAllUsersPosts(int userId);

    CarBody addCarBody(CarBody carBody);
    List<CarBody> findAllCarBody();
    CarBody findCarBodyById(int carBodyId);

    CarMark addCarMark(CarMark carMark);
    List<CarMark> findAllCarMark();
    CarMark findCarMarkById(int carMarkId);



}
