package Journey_of_Taro_V3.Journey_of_Taro_V3.services.users;


import Journey_of_Taro_V3.Journey_of_Taro_V3.dtos.users.UserDto;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.security.Authority;
import Journey_of_Taro_V3.Journey_of_Taro_V3.models.users.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = userService.getUser(username);


        String password = userDto.getPassword();

        List<Role> roles = userDto.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role: roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
    }

}