package me.vinuvicho.attemptSeven.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequest {              //userUpdate in User must be updated if something new added
    private Long id;
    private String email;
    private String about;
    private String username;
    private String profilePhoto;
    private ProfileType profileType;        //Not changing yet

    public UserRequest(Long id, String username, ProfileType profileType, String profilePhoto, String about, String email) {
        this.id = id;
        this.username = username;
        this.profileType = profileType;
        this.profilePhoto = profilePhoto;
        this.about = about;
        this.email = email;
    }

    public UserRequest() {
    }

    public UserRequest(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileType = user.getProfileType();
        this.profilePhoto = user.getProfilePhoto();
        this.about = user.getAbout();
        this.email = user.getEmail();
    }
}
