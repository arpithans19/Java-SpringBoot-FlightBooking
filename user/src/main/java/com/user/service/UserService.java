package com.user.service;


import com.user.entity.User;


public interface UserService {

	public User saveUser(User user);
	public User getUser(int userId);
	public User modifyUser(User user);
	public void deleteUser(int userId);
	public User userByName(String userName);
	public int getSequenceNumber(String sequenceName);
}
