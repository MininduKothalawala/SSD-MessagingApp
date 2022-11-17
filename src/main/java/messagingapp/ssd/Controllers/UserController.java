package messagingapp.ssd.Controllers;

import messagingapp.ssd.Config.JwtTokenUtil;
import messagingapp.ssd.Models.JwtRequest;
import messagingapp.ssd.Models.JwtResponse;
import messagingapp.ssd.Models.User;
import messagingapp.ssd.Services.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
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
    public ResponseEntity<?> Signup(@RequestBody User user, @RequestHeader String Authorization){

        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = userService.GetUserRoleByUsername(username);

        try {
            if (role.equalsIgnoreCase("Admin")){
                userService.RegisterUser(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
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
                final String role = userService.GetUserRoleByUsername(userDetails.getUsername());

                JwtResponse response = new JwtResponse();
                response.setRole(role);
                response.setToken(token);

                return new ResponseEntity<>(response, HttpStatus.OK);
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
