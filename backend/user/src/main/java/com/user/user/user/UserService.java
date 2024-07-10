package com.user.user.user;

import com.user.user.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public UserDTO throwException(String message) {
        throw new RuntimeException(message);
    }

    public UserDTO getUser(Jwt jwt) {

        UserModel user = userRepository.findById(Long.valueOf(jwt.getSubject())).orElseThrow(() -> new RuntimeException("No user found."));

        return user.getId().equals(Long.valueOf(jwt.getSubject())) ? convertToDTO(user) : throwException("You are requesting a wrong user.");
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

    public UserToken login(UserDTO userDTO) {

        UserModel userModel = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new RuntimeException("No such user exists!"));

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Login unsuccessful");
        }

        return UserToken.builder()
                .token(jwtTokenUtils.generateToken(userModel))
                .build();

    }

    private UserDTO convertToDTO(UserModel userModel) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userModel.getId());
        userDTO.setUsername(userModel.getUsername());
        userDTO.setPassword(userModel.getPassword());
        userDTO.setName(userModel.getName());
        userDTO.setSurname(userModel.getSurname());

        return userDTO;
    }

    private UserModel convertToUser(UserDTO userDTO) {

        UserModel userModel = new UserModel();

        userModel.setUsername(userDTO.getUsername());
        userModel.setPassword(userDTO.getPassword());
        userModel.setName(userDTO.getName());
        userModel.setSurname(userDTO.getSurname());

        return userModel;
    }
}
