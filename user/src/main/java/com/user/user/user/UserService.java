package com.user.user.user;

import com.user.user.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authManager;

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public UserDTO register(UserDTO userDTO) {

        userRepository.findByUsername(userDTO.getUsername()).ifPresent(
                (user) -> {
                    throw new RuntimeException("User already exists");
                });

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserModel user = userRepository.save(convertToUser(userDTO));

        return convertToDTO(user);

    }

    public String login(UserDTO userDTO) {

        UserModel userModel = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new RuntimeException("No such user exists!"));

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Login unsuccessful");
        }

        return jwtTokenUtils.generateToken(userModel.getId());

    }

    private UserDTO convertToDTO(UserModel userModel) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userModel.getId());
        userDTO.setUsername(userModel.getUsername());
        userDTO.setPassword(userModel.getPassword());

        return userDTO;
    }

    private UserModel convertToUser(UserDTO userDTO) {

        UserModel userModel = new UserModel();

        userModel.setUsername(userDTO.getUsername());
        userModel.setPassword(userDTO.getPassword());

        return userModel;
    }
}
