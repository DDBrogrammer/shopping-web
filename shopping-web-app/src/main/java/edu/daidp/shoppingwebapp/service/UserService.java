package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.constant.Role;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.dto.UserDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.User;
import edu.daidp.shoppingwebapp.repository.CartRepository;
import edu.daidp.shoppingwebapp.repository.PhotoRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import edu.daidp.shoppingwebapp.repository.VideoRepository;
import edu.daidp.shoppingwebapp.util.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static edu.daidp.shoppingwebapp.common.constant.UserStatus.ENABLE;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository,
                       PhotoRepository photoRepository,
                       VideoRepository videoRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.userMapper = userMapper;
    }


    public Optional<Page<UserDto>> findAll(Integer pageNumber, Integer pageSize) {
        Page<User> page = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        if (!page.isEmpty()) {

            Page<UserDto> pageDto = page.map(userMapper::toDto);
            return Optional.of(pageDto);
        }
        return Optional.empty();
    }
    @Transactional
    public UserDto save(UserDto userDto) throws NoContentFoundException {
        if (userDto.getId() == null) {
            User user = User.builder().firstName(userDto.getFirstName()).middleName(userDto.getMiddleName())
                    .lastName(userDto.getLastName())
                    .phoneNo(userDto.getPhoneNo())
                    .userStatus(ENABLE)
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(Role.CUSTOMER)
                    .build();

            User savedUser = userRepository.save(user);
            cartRepository.save(Cart.builder().subTotal(BigDecimal.ZERO).createAt(Timestamp.valueOf(
                    LocalDateTime.now())).user(savedUser).build());
            return userMapper.toDto(savedUser);
        }
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isPresent()) {
            return userMapper.toDto(userRepository.save(userOptional.get()));
        }

        throw new NoContentFoundException("No user found with Id: " + userDto.getId().toString());


    }

    @Transactional
    public boolean deleteUser(Long userId) throws NoContentFoundException {
        Optional<User> userOptional = userRepository.findById(BigInteger.valueOf(userId));
        if (userOptional.isEmpty()) {
            throw new NoContentFoundException("User with id" + userId + " not exist");
        }
        User user = userOptional.get();
        if (user.getRole().equals(Role.CUSTOMER)) {
            cartRepository.deleteCartByUser(user);
        }
        userRepository.delete(userOptional.get());
        return true;
    }
}
