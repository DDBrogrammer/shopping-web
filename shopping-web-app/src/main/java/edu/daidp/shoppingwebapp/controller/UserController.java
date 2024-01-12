package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import edu.daidp.shoppingwebapp.common.exception.ApplicationResponse;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.dto.UserDto;
import edu.daidp.shoppingwebapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ApplicationResponse<Page<UserDto>>> retrieveUsers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return userService.findAll(pageNumber, pageSize).map(cars -> ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<Page<UserDto>>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                               COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                               cars, List.of()

                ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
    }

    @PostMapping()
    public ResponseEntity<ApplicationResponse<UserDto> > saveUser(@RequestBody UserDto userDto) throws
            NoContentFoundException {
        return  ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<UserDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         userService.save(userDto), List.of()));
    }

    @PutMapping()
    public ResponseEntity<ApplicationResponse<UserDto> > editUser(@RequestBody UserDto userDto) throws
            NoContentFoundException {
        return  ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<UserDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         userService.save(userDto), List.of()));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApplicationResponse<String> > deleteUser(@PathVariable Long userId) throws
            NoContentFoundException {

        return  ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                     COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                     userService.deleteUser(userId)? "DELETE SUCCESS":"DELETE FAILED"
                        , List.of()));
    }
}
