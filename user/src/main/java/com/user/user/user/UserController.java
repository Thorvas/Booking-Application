package com.user.user.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUsers(){

        return "Get all my bookings.";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUser(@PathVariable Long id) {

        return "Return specific booking.";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody UserDTO userDTO) {

        return "Create specific booking";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {

        return "Update specific booking";
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable Long id) {

        return "Delete specific booking";
    }
}
