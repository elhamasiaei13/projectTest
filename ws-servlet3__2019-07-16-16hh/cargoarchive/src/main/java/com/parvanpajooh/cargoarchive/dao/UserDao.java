package com.parvanpajooh.cargoarchive.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.parvanpajooh.cargoarchive.model.User;;

public class UserDao {

	static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

	JdbcTemplate template;

	/**
	 * 
	 * @param template
	 */
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User get(Long id) throws Exception {

		LOG.debug("Entering get (id: {})", id);
		
		User user = null;
		try {
			if (id != null) {
				String sql = "SELECT * FROM user_tbl WHERE id = ?";
				user = template.queryForObject(
						sql,
						new Object[] {id},
						new BeanPropertyRowMapper<User>(User.class));
				
			}

		} catch (Exception e) {
			throw new Exception("Error occurred while getting user by id", e);
		}
		
		return user;
	}

	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findByUsername(String username) throws Exception {

		LOG.debug("Entering findByUsername(username: {})", username);
		
		User user = null;
		try {
			if (username != null) {
				String sql = "SELECT * FROM user_tbl WHERE username = ?";
				user = template.queryForObject(
						sql,
						new Object[] {username},
						new BeanPropertyRowMapper<User>(User.class));
				
			}

		} catch (Exception e) {
			throw new Exception("Error occurred while getting user by id", e);
		}
		
		return user;
	}

}
