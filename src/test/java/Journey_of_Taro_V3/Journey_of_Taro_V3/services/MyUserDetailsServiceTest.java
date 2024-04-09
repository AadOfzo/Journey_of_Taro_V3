package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.config.users.MyUserDetailsService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
import Journey_of_Taro_V3.Journey_of_Taro_V3.services.users.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;


    @Test
    public void testLoadUserByUsername_Successful() {
        // Arrange
        String username = "Test User";
        User user = new User();
        user.setUsername(username);
        user.setPassword("hashed_password"); // Mock hashed password

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadAdminByUsername_Successful() {
        // Arrange
        String username = "Test Admin";
        User user = new User();
        user.setUsername(username);
        user.setPassword("hashed_password"); // Mock hashed password

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(username, userDetails.getUsername());
    }

    @Test(expected = org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public void testLoadUserByUsername_UserNotFound() {
        // Mock the UserRepository to return a user not found
        String username = "User doesn't exists!";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Call the method under test - this should throw UsernameNotFoundException
        userDetailsService.loadUserByUsername(username);
    }

    @Test
    public void testAddRole_UserExists() {
        // Arrange
        String username = "testUser";
        String role = "ROLE_USER";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        userService.addRole(username, role);

        // Assert
        Mockito.verify(userRepository).save(user);
        Assert.assertTrue(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role)));
    }

    @Test
    public void testAddRole_AdminExists() {
        // Arrange
        String username = "testAdmin";
        String role = "ROLE_ADMIN";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        userService.addRole(username, role);

        // Assert
        Mockito.verify(userRepository).save(user);
        Assert.assertTrue(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role)));
    }
    @Test(expected = org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public void testAddRole_UserNotExists() {

        // Arrange
        String username = "User doesn't exist";
        String role = "ROLE_USER";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(false);

        // Act
        userService.addRole(username, role);
    }

    @Test(expected = org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public void testAddRole_AdminNotExists() {
        // Arrange
        String username = "Admin doesn't exist";
        String role = "ROLE_ADMIN";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(false);

        // Act
        userService.addRole(username, role);
    }
}
