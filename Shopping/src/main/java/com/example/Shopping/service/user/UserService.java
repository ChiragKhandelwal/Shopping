package com.example.Shopping.service.user;

import com.example.Shopping.dto.UserDto;
import com.example.Shopping.exceptions.AlreadyExistException;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.User;
import com.example.Shopping.repository.UserRepository;
import com.example.Shopping.request.CreateUserRequest;
import com.example.Shopping.request.UserUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;
    public User getUserById(Long id){
        try {
            return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not Exist"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public User createUser(CreateUserRequest request) throws AlreadyExistException {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistException("Oops!" +request.getEmail() +" already exists!"));
        //return
    }

    public User updateUser(UserUpdateRequest request,Long id) throws ResourceNotFoundException {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not Exist"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            try {
                throw new ResourceNotFoundException("User not found!");
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
