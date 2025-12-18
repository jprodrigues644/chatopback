package com.op.chatopback.controller;


import com.op.chatopback.dto.UserDto;
import com.op.chatopback.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller for handling user-related endpoints.
 * <p>
 * Provides an endpoint for retrieving user details by ID.
 * </p>
 */
@RestController
    @RequestMapping("/api/user")
    public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }
/**
     * Retrieves user details by ID.
     *
     * @param id the ID of the user to retrieve
     * @return a response entity containing the user details
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
    }

