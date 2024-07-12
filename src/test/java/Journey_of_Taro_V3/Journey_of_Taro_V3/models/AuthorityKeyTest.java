package Journey_of_Taro_V3.Journey_of_Taro_V3.models;

import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.AuthorityKey;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorityKeyTest {

    @Test
    public void testEqualsAndHashCode() {
        AuthorityKey key1 = new AuthorityKey();
        key1.setUsername("test_user");
        key1.setAuthority("ROLE_ADMIN");

        AuthorityKey key2 = new AuthorityKey();
        key2.setUsername("test_user");
        key2.setAuthority("ROLE_ADMIN");

        AuthorityKey key3 = new AuthorityKey();
        key3.setUsername("test_user");
        key3.setAuthority("ROLE_USER");

        // Test equals() method
        assertTrue(key1.equals(key2)); // Should be equal to another key with the same fields
        assertFalse(key1.equals(key3)); // Should not be equal to a key with different authority

        // Test hashCode() method
        assertEquals(key1.hashCode(), key2.hashCode()); // Hash codes should be equal for equal objects
        assertNotEquals(key1.hashCode(), key3.hashCode()); // Hash codes should not be equal for different authorities
    }

    @Test
    public void testNullEqualsAndHashCode() {
        AuthorityKey key1 = new AuthorityKey();
        AuthorityKey key2 = new AuthorityKey();

        // Test equals() method with null fields
        assertTrue(key1.equals(key2)); // Both keys are empty, hence equal
        assertTrue(key2.equals(key1)); // Test symmetry

        // Test hashCode() method with null fields
        assertEquals(key1.hashCode(), key2.hashCode()); // Hash codes should be equal for both empty keys
    }
}
