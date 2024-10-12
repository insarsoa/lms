package my.project.lms.controller;

import my.project.lms.AuthRequest;
import my.project.lms.AuthResponse;
import my.project.lms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // Creates JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        if(authRequest.getUsername().equals("user") && authRequest.getPassword().equals("password")){
            final String jwt = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));
        }else{
            return ResponseEntity.status(401).body("Invalid Credentials - Unauthorized");
        }
    }
}
