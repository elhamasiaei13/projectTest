package com.parvanpajooh.cargoarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parvanpajooh.cargoarchive.config.MyUserPrincipal;
import com.parvanpajooh.cargoarchive.dao.UserDao;
import com.parvanpajooh.cargoarchive.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	static final Logger LOG = LoggerFactory.getLogger(MyUserDetailsService.class);
 
    @Autowired
    private UserDao userDao;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
			User user = userDao.findByUsername(username);
			if (user == null) {
			    throw new UsernameNotFoundException(username);
			}
			return new MyUserPrincipal(user);
		
        } catch (Exception e) {
        	LOG.error("Error occurred while finding user by userName [{}]", username);
		    throw new UsernameNotFoundException(username);
		}
    }
}