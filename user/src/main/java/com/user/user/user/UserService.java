package com.user.user.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(userModel -> convertToDTO(userModel))
                .toList();
    }

    public UserDTO getUser(Long id) {

        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

        return convertToDTO(user);
    }

    public UserDTO deleteUser(Long id) {

        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

        userRepository.delete(user);

        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {

        UserModel userModel = convertToUser(userDTO);

        userRepository.save(userModel);

        return convertToDTO(userModel);

    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {

        UserModel user = userRepository.findById(id).orElseThrow( () -> new RuntimeException("User not found."));

        user.setNickname(userDTO.getNickname());

        userRepository.save(user);

        return convertToDTO(user);
    }

    private UserDTO convertToDTO(UserModel userModel) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(userModel.getId());
        userDTO.setNickname(userModel.getNickname());

        return userDTO;
    }

    private UserModel convertToUser(UserDTO userDTO) {

        UserModel userModel = new UserModel();

        userModel.setNickname(userDTO.getNickname());

        return userModel;
    }
}
