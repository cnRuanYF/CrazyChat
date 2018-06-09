package com.crazychat.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.crazychat.dao.UserDAO;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.User;
import com.crazychat.util.EncryptionUtils;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 请求内容的类型
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password=EncryptionUtils.getSHA1(password);
		int picAddress = Integer.parseInt(request.getParameter("picID")) ;
		//将已选的头像存在session中
		//session.setAttribute("picAddress", picAddress);
		System.out.println(picAddress);
		User user = new User(username, password);
		user.setNickname(username);
		user.setPictureId(picAddress);
		Boolean flag = true;
		UserDAO dao = new UserDAOImpl();
		try {
			List<User> users = dao.findAll();
			// 判断用户是否已经存在,用户存在就设置flag为false
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUsername().equals(username)) {
					flag = false;
				}
			}
			if (flag) {// 如果用户不存在就将用户添加到数据库
				try {
					// 添加用户
					if (dao.add(user)) {
						// 添加成功跳转
						request.getRequestDispatcher("register_success1.jsp").forward(request, response);
					} else {
						// 添加失败
						System.out.println("error");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// 如果用户存在，提示用户已经存在
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// response.sendRedirect("recoverPassword.jsp");
	}

}
