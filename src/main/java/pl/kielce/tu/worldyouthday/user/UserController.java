package pl.kielce.tu.worldyouthday.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.worldyouthday.user.resources.ChangePasswordResource;
import pl.kielce.tu.worldyouthday.user.resources.NewUserResource;
import pl.kielce.tu.worldyouthday.user.resources.UpdateUserResource;
import pl.kielce.tu.worldyouthday.user.services.*;

@RestController
@RequestMapping("${spring.data.rest.base-path}/users")
public class UserController {

    @Autowired
    private ChangePasswordUserService changePasswordUserService;

    @Autowired
    private FindUserService findUserService;

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private RemoveUserService removeUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @PreAuthorize("#id == principal.id or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> userById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(findUserService.getUserById(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        try {
            return new ResponseEntity<>(findUserService.getUserList(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody NewUserResource user) {
        try {
            return new ResponseEntity<>(registerUserService.registerUser(user), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("#id == principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateUserResource user) {
        try {
            return new ResponseEntity<>(updateUserService.updateUser(id, user), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}/changepassword", method = RequestMethod.PUT)
    @PreAuthorize("#id == principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ChangePasswordResource changePasswordResource) {
        try {
            return new ResponseEntity<>(changePasswordUserService.changePassword(id, changePasswordResource), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable String id) {
        try {
            removeUserService.removeUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
