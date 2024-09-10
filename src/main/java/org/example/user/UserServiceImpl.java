package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(x -> UserMapper.toUserDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);
    }
}
