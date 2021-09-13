package com.sp.lang.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sp.lang.buildinfuncs.DataSource;

public class JavaSDKUsageTest {
	
	@SuppressWarnings("null")
	public static void main(String[] args) {

				List<User> users = new ArrayList<>();
				// TODO 为users加入数据

				// count
				DataSource.FromList(users)
				.count();
				
				// distinct
				DataSource.FromList(users)
				.distinct();
				
				// avg
				DataSource.FromList(users)
				.averageInt(u -> u.getAge());
				
				// sum
				DataSource.FromList(users)
				.sumInt(u -> u.getAge());
				
				// skip and toList
				DataSource.FromList(users)
				.skip(1)
				.toList();
				
				// orderBy, thenBy and reverse
				DataSource.FromList(users)
				.orderBy(u -> u.getAge())
				.thenBy(u -> u.getName())
				.reverse();



				DataSource.FromList(users)
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


				List<User> users_other = new ArrayList<>();
				// TODO 为users_other加入数据

				DataSource.FromList(users)
				.intersect(
						DataSource.FromList(users_other)
				)
				.toSet();


				List<User> users_another = new ArrayList<>();
				// TODO 为users_another加入数据
				List<Car> cars = new ArrayList<>();

				DataSource.FromList(users)
				.intersect(
						DataSource.FromList(users_other)
				).except(
						DataSource.FromList(users_another)

				)
				.range(0, 100)
				.reverse()
				.where(u -> u.getAge()==30)
				.<Car, Long, UserWithCar>leftJoin(
						u -> u.getCarId(),
						DataSource.FromList(cars),
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
