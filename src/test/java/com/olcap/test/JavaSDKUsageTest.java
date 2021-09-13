package com.olcap.test;

import java.io.Serializable;
import java.util.List;

import com.olcap.DataSource;

public class JavaSDKUsageTest {
	
	@SuppressWarnings("null")
	public static void main(String[] args) {
		
				// count
				DataSource.FromMysql("select * from user_info;", User.class)
				.count();
				
				// distinct
				DataSource.FromMysql("select * from user_info;", User.class)
				.distinct();
				
				// avg
				DataSource.FromMysql("select * from user_info;", User.class)
				.averageInt(u -> u.getAge());
				
				// sum
				DataSource.FromMysql("select * from user_info;", User.class)
				.sumInt(u -> u.getAge());
				
				// skip and toList
				DataSource.FromMysql("select * from user_info;", User.class)
				.skip(1)
				.toList();
				
				// orderBy, thenBy and reverse
				DataSource.FromMysql("select * from user_info;", User.class)
				.orderBy(u -> u.getAge())
				.thenBy(u -> u.getName())
				.reverse();
				
				
		
				DataSource.FromMysql("select * from user_info;", User.class)
				.distinct()
				.goThrough(u -> {
					// do something
				}).where(u -> {
					// test whether is true
					return true;
				}).skip(1)
				.orderBy(u -> {
					return u.getName();
				})
				.thenBy(u ->  {
					return u.getAge();
				})
				.thenByDescending(u -> {
					return u.getHome();
				})
				.groupBy(u -> u.getName(), u -> u)
				.aggregate(1, (accu, e) -> {
					int sumAge = accu;
					for(User u : e.getValue()){
						sumAge += u.getAge();
					}
					return sumAge;
				});
				
				
				// mysql and hbase
				DataSource.FromMysql("select * from user_info;", User.class)
				.intersect(
						DataSource.FromHbase("select * from user_data_table_in_tddl_another;", User.class)
				)
				.toSet();
				
				
				// mysql and pgsql
				DataSource.FromMysql("select * from user_info;", User.class)
				.intersect(
						DataSource.FromPgsql("select * from user_data_table_in_gpsql;", User.class)
				)
				.toSet();
				
				
				// mysql and rpc
				UserService userService = null;
				DataSource.FromMysql("select * from user_info;", User.class)
				.except(
						DataSource.FromRpc(() -> {
							
							List<User> users = userService.getUsersFromRemoteService();
							
							return DataSource.FromList(users);
						})
						
				)
				.toSet();
				
				
				DataSource.FromMysql("select * from user_info;", User.class)
				.intersect(
						DataSource.FromMysql("select * from user_data_table_in_tddl_another;", User.class)
				).except(
						DataSource.FromRpc(() -> {
							
							List<User> users = userService.getUsersFromRemoteService();
							
							return DataSource.FromList(users);
						})
						
				)
				.range(0, 100)
				.reverse()
				.union(
						DataSource.FromHbase("select * from user_data_table_in_tddl_another;", User.class)
				)
				.where(u -> u.getAge()==30)
				.<Car, Long, UserWithCar>leftJoin(
						u -> u.getCarId(), 
						DataSource.FromPgsql("select * from car_table_in_hbase;", Car.class), 
						c -> c.getId(),
						(u , c) -> {
							
							UserWithCar userWithCar = new UserWithCar();
							userWithCar.c = c;
							userWithCar.u = u;
							
							return userWithCar;
						}
				)
				.toSet();
				
	}
}

interface UserService {
	public List<User> getUsersFromRemoteService();
}

class User implements Serializable{

	private static final long serialVersionUID = 1656527409297906328L;

	public Long getId(){
		return null;
	}
	
	public String getName() {
		return null;
	}
	
	public int getAge() {
		return 0;
	}
	
	public String getHome() {
		return null;
	}
	
	public Long getCarId() {
		return null;
	}
}

class Car implements Serializable {

	private static final long serialVersionUID = -8309820729872354189L;

	public Long getId() {
		return null;
	}
	
	public String getType() {
		return "mazida";
	}
	
	public String getName() {
		return null;
	}
}

class UserWithCar implements Serializable{

	private static final long serialVersionUID = 1022314978984876252L;
	public User u;
	public Car c;
	
}
