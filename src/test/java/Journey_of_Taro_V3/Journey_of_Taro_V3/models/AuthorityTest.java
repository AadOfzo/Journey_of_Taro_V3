package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorityTest {

    @InjectMocks
    private Authority authority;

    @Test
    public void testGettersAndSetters() {
        // Create a user
        User user = new User();
        user.setUsername("test_user");

        // Test setter and getter for User
        authority.setUser(user);
        assertEquals(user, authority.getUser());

        // Test setter and getter for Authority
        String expectedAuthority = "ROLE_ADMIN";
        authority.setAuthority(expectedAuthority);
        assertEquals(expectedAuthority, authority.getAuthority());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setUsername("test_user1");

        User user2 = new User();
        user2.setUsername("test_user2");

        // Create authorities with the same user and authority
        Authority authority1 = new Authority(user1, "ROLE_ADMIN");
        Authority authority2 = new Authority(user1, "ROLE_ADMIN");

        // Test equals() method
        assertEquals(authority1, authority2);

        // Test hashCode() method
        assertEquals(authority1.hashCode(), authority2.hashCode());

        // Change the authority for authority2 and test again
        authority2.setAuthority("ROLE_USER");

        // Test equals() method again
        assertNotEquals(authority1, authority2);

        // Test hashCode() method again
        assertNotEquals(authority1.hashCode(), authority2.hashCode());

        // Test against null and different class
        assertNotEquals(authority1, null);
        assertNotEquals(authority1, new Object());
    }

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
