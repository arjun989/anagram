package com.arjun.arangram;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RootServlet extends HttpServlet {
	private String output = "";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// set the response type to be html text
		resp.setContentType("text/html");

		// we need to get access to the google user service
		UserService us = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User u = us.getCurrentUser();
		String login_url = us.createLoginURL("/");
		String logout_url = us.createLogoutURL("/");

		// attach a few things to the request such that we can access them in
		// the jsp
		req.setAttribute("user", u);
		req.setAttribute("login_url", login_url);
		req.setAttribute("logout_url", logout_url);
		req.setAttribute("result", output);

		// get a request dispatcher and launch a jsp that will render our page
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
		output = "";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		PrintWriter out = resp.getWriter();
		// get access to the user service to get our user
		UserService us = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User u = us.getCurrentUser();

		// Dealing with Query
		if (req.getParameter("Search") != null) {
			// getting submitted word
			String inp = req.getParameter("anagram_seach");
			// validation
			if (!inp.matches("[a-zA-Z]+")) {
				displayAlert("Invalid Query !", out);
				return;
			}
			inp = inp.toLowerCase();
			char[] c = inp.toCharArray();
			Arrays.sort(c);
			// Lexically sorted anagram key
			String KEY = new String(c);

			PersistenceManager pm = null;
			Anagram ang;
			Key user_key = KeyFactory.createKey("Anagram", KEY);

			try {
				// Check if the KEY exists...
				pm = PMF.get().getPersistenceManager();
				ang = pm.getObjectById(Anagram.class, user_key);
				output = ang.getResults(inp);
			} catch (Exception e) {
				// This category anagram doesnt exist yet, so add it...
				output = "Nothing Found !";
			} finally {
				pm.close();
			}
			resp.sendRedirect("/");
		} else {
			// getting submitted word
			String inp = req.getParameter("anagram");
			// validation
			if (!inp.matches("[a-zA-Z]+")) {
				displayAlert("Invalid Input !", out);
				return;
			}

			inp = inp.toLowerCase();
			char[] c = inp.toCharArray();
			Arrays.sort(c);
			// Lexically sorted anagram key
			String KEY = new String(c);

			PersistenceManager pm = null;
			Anagram ang;
			Key user_key = KeyFactory.createKey("Anagram", KEY);

			try {
				// Check if the KEY exists...
				pm = PMF.get().getPersistenceManager();
				ang = pm.getObjectById(Anagram.class, user_key);
				if (ang.addAnagram(inp))
					displayAlert("Anagram Added !", out);
				else
					displayAlert("Already Exists !", out);
				pm.makePersistent(ang);
			} catch (Exception e) {
				// This category anagram doesnt exist yet, so add it...
				ang = new Anagram();
				ang.setLexicalKey(user_key);
				ang.addAnagram(inp);
				pm.makePersistent(ang);
				displayAlert("Anagram Added !", out);
			} finally {
				pm.close();
			}
		}
		// resp.sendRedirect("/");
	}

	private void displayAlert(String msg, PrintWriter out) {
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + msg + "');");
		out.println("location='/';");
		out.println("</script>");
	}

}
