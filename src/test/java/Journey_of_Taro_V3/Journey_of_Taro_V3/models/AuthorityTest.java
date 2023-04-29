package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

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
    public void testGetUsername() {
        String expectedUsername = "test_user";
        authority.setUsername(expectedUsername);
        assertEquals(expectedUsername, authority.getUsername());
    }

    @Test
    public void testGetAuthority() {
        String expectedAuthority = "ROLE_ADMIN";
        authority.setAuthority(expectedAuthority);
        assertEquals(expectedAuthority, authority.getAuthority());
    }

    @Test
    public void testConstructor() {
        String expectedUsername = "test_user";
        String expectedAuthority = "ROLE_ADMIN";
        Authority authority = new Authority(expectedUsername, expectedAuthority);
        assertEquals(expectedUsername, authority.getUsername());
        assertEquals(expectedAuthority, authority.getAuthority());
    }
}
