package edu.tcu.cs.peerevaluation.security;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public Result getLoginInfo(Authentication authentication){
    LOGGER.debug("Authenticated user: '{}'", authentication.getName());
    System.out.println(authentication.toString());
    System.out.println("peepeepoopoo");
    return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.createLoginInfo(authentication));
  }
}
