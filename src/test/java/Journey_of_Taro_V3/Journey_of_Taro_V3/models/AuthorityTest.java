package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorityTest {

    @InjectMocks
    private Authority authority;

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUsername("test_user");
        authority.setUser(user);
        assertEquals(user, authority.getUser());
    }

    @Test
    public void testGetAuthority() {
        String expectedAuthority = "ROLE_ADMIN";
        authority.setAuthority(expectedAuthority);
        assertEquals(expectedAuthority, authority.getAuthority());
    }

//    @Test
//    public void testConstructor() {
//
//        User expectedUsername = "test_user";
//        String expectedAuthority = "ROLE_ADMIN";
//        Authority authority = new Authority(expectedUsername, expectedAuthority);
//        assertEquals(expectedUsername, authority.getUser());
//        assertEquals(expectedAuthority, authority.getAuthority());
//    }

    @Test
    public void testConstructor() {
        User user = new User();
        user.setUsername("test_user");
        String expectedAuthority = "ROLE_ADMIN";
        Authority authority = new Authority(user, expectedAuthority);
        assertEquals(user, authority.getUser());
        assertEquals(expectedAuthority, authority.getAuthority());
    }
}
