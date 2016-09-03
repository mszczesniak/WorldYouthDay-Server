package pl.kielce.tu.worldyouthday.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.worldyouthday.security.token.TokenUtils;
import pl.kielce.tu.worldyouthday.security.token.json.request.AuthenticationRequest;
import pl.kielce.tu.worldyouthday.security.token.json.response.AuthenticationResponse;
import pl.kielce.tu.worldyouthday.security.user.LoggedUser;
import pl.kielce.tu.worldyouthday.user.converters.UserToResourceConverter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserToResourceConverter userToResourceConverter;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoggedUser user = (LoggedUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = tokenUtils.generateToken(user, device);

        AuthenticationResponse authenticationResponse =
                AuthenticationResponse
                        .newBuilder()
                        .withToken(token)
                        .withLoggedUser(userToResourceConverter.convert(user.getUser()))
                        .build();

        return ResponseEntity.ok(authenticationResponse);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<AuthenticationResponse> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader("X-Auth-Token");
        String username = tokenUtils.getUsernameFromToken(token);
        LoggedUser user = (LoggedUser) userDetailsService.loadUserByUsername(username);

        if (tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            AuthenticationResponse authenticationResponse =
                    AuthenticationResponse
                            .newBuilder()
                            .withToken(refreshedToken)
                            .withLoggedUser(userToResourceConverter.convert(user.getUser()))
                            .build();
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
