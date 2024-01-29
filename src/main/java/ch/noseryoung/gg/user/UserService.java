package ch.noseryoung.gg.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
        String username = userDto.getUsername();
        if (!userRepository.existsByUsername(username)) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userDto.getUsername());
            userEntity.setPassword(userDto.getPassword_hash());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setRole(userDto.getRole());

            UserEntity savedUserEntity = userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("username already exists");
        }
    }

    public UserDto readUserById(int id) {
        try {
            Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

            if (optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.orElseThrow();

                return new UserDto(
                        userEntity.getUsername(),
                        userEntity.getPassword(),
                        userEntity.getEmail(),
                        userEntity.getRole()
                );
            }
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User with ID: " + id + " not found in database");
        }
        return null;
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

    public void updateUserById(int id, UserDto userDto) {
        try {
            Optional<UserEntity> existingEntityOptional = userRepository.findById(id);

            UserEntity updatedEntity;
            if (existingEntityOptional.isPresent()) {
                UserEntity existingEntity = existingEntityOptional.get();
                existingEntity.setUsername(userDto.getUsername());
                existingEntity.setPassword_hash(userDto.getPassword_hash());
                existingEntity.setEmail(userDto.getEmail());
                existingEntity.setRole(userDto.getRole());

                updatedEntity = userRepository.save(existingEntity);
            }
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User with ID: " + id + " not found in database");
        }
    }

    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User with ID: " + id + " not found in database");
        }
    }
}
