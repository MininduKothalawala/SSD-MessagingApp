package messagingapp.ssd.Controllers;

import messagingapp.ssd.Config.JwtTokenUtil;
import messagingapp.ssd.Models.JwtRequest;
import messagingapp.ssd.Models.User;
import messagingapp.ssd.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final UserService userService;

    public UserController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Signup(@RequestBody User user){
        try {
            userService.RegisterUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> Signin(@RequestBody JwtRequest request){
        try {
            Boolean authentication = userService.AuthenticateUser(request.getUsername(), request.getPassword());

            final UserDetails userDetails = userService.GetUserByUsername(request.getUsername());

            if(authentication){
                final String token = jwtTokenUtil.generateToken(userDetails);
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
