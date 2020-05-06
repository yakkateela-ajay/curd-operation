package controller;
import  java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Blog;
import model.User;
import service.CRUDOperations;

import utility.CheckBlogPost;


@WebServlet(urlPatterns= {"/blog"})
public class BlogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BlogController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=this.getServletContext().getRequestDispatcher("/WEB-INF/views/blogView.jsp");
		rd.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blogDetails = request.getParameter("selectedAnswers");
		System.out.println(blogDetails);
		
		String[] userBlog=blogDetails.split(",");
		String title = userBlog[0];
		String description = userBlog[1];
		LocalDate postedOn = LocalDate.now();
		System.out.println(title);
		System.out.println(description);
		
		User user = null;
		Blog blog=new Blog(title,description,postedOn);
		System.out.println(title);
		System.out.println(description);
		
		blog.setBlogTitle(title);
		blog.setBlogDescription(description);
		blog.setDate(postedOn);
		
		CheckBlogPost checkBlog=new CheckBlogPost();
		boolean check=checkBlog.checkBlog(blog);
		
		CRUDOperations crud=new CRUDOperations();
		List<Blog> listblog = crud.createBlog(blog);
		Collections listblog1 = null;
		listblog1.sort(listblog,(m1,m2)->m1.getDate().compareTo(m2.getDate()));
		Collections listblog2 = null;
		listblog2.sort(listblog,(m1,m2)->m1.getBlogTitle().compareTo(m2.getBlogTitle()));
		System.out.println(listblog1);
		System.out.println(listblog2);
		if(check) {
			request.setAttribute("listBlog", listblog);
//			request.setAttribute("blog", blog);
//			request.setAttribute("user",user);
			RequestDispatcher rd=this.getServletContext().getRequestDispatcher("/WEB-INF/views/blogView.jsp");
			rd.forward(request, response);
		}
		else
		{
			request.setAttribute("message","Your blog contains offensive words. Please use appropriate words.");
			RequestDispatcher rd=this.getServletContext().getRequestDispatcher("/WEB-INF/views/blogView.jsp");
			rd.forward(request, response);
		}
		
	}

}
