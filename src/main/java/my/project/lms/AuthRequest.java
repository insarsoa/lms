package my.project.lms;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    public String username;
    public String password;
}