package com.parvanpajooh.cargoarchive.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.parvanpajooh.cargoarchive.model.User;

public class MyUserPrincipal implements UserDetails {
	
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private User user;
 
    public MyUserPrincipal(User user) {
        this.user = user;
    }
    //...

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
    	GrantedAuthority ga = new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return user.getRole();
			}
		};
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(ga);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}