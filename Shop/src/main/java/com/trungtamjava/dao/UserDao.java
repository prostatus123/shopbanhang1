package com.trungtamjava.dao;

import java.util.List;
import com.trungtamjava.entity.User;

public interface UserDao {
	void insert(User user);

	void update(User user);

	void delete(User user);

	User get(Long id);

	User getByUserName(String userName);

	List<User> search(String findName, int start, int length);

}
