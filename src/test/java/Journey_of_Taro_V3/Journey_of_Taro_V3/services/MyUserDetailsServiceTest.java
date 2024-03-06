package Journey_of_Taro_V3.Journey_of_Taro_V3.services;

import Journey_of_Taro_V3.Journey_of_Taro_V3.config.users.MyUserDetailsService;
import Journey_of_Taro_V3.Journey_of_Taro_V3.exceptions.UsernameNotFoundException;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import Journey_of_Taro_V3.Journey_of_Taro_V3.repositories.users.UserRepository;
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
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;


    @Test
    public void testLoadUserByUsername_Successful() {
        // Mock the UserRepository to return a user with the provided username
        String username = "TestAdmin";
        User user = new User();
        user.setUsername(username);
        user.setPassword("hashed_password"); // Mock hashed password

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Call the method under test
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assertions
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(username, userDetails.getUsername());
        // Add more assertions if needed
    }

    @Test(expected = org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public void testLoadUserByUsername_UserNotFound() {
        // Mock the UserRepository to return an empty optional, simulating user not found
        String username = "NonExistentUser";

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Call the method under test - this should throw UsernameNotFoundException
        userDetailsService.loadUserByUsername(username);
    }


}
