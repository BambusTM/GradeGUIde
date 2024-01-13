package ch.noseryoung.gg.service;

import ch.noseryoung.gg.dto.UserDto;
import ch.noseryoung.gg.entity.UserEntity;
import ch.noseryoung.gg.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {

    }

    public UserDto readUserById(int id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        if(optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.orElseThrow();

            return new UserDto(
                    userEntity.getUsername(),
                    userEntity.getPassword_hash(),
                    userEntity.getEmail(),
                    userEntity.getRole()
            );
        } else {
            return null;
        }
    }

    public List<UserDto> readAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDto> userDtoList = userEntities.stream().map(userEntity -> {
            return new UserDto(
                    userEntity.getUsername(),
                    userEntity.getPassword_hash(),
                    userEntity.getEmail(),
                    userEntity.getRole()
            );
        }).collect(Collectors.toList());
        return userDtoList;
    }
}
