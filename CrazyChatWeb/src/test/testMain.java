package test;

import java.util.List;

import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.User;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDAOImpl udi=new UserDAOImpl();
		Boolean flag=true;
		List<User> users;
		try {
			users = udi.findAll();
			for(int i=0;i<users.size();i++){
				System.out.println(users.get(i).getUsername());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
