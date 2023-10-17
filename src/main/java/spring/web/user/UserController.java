package spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.domain.User;
import spring.service.user.UserService;

/*
 *  FileName : UserController.java
 *  - Controller Coding Policy 
 *  	: return type : String ���� 
 *  	: Method parameter  
 *  		- Client Data : @ModelAttribute / @RequestParam ������ ���
 *  	    - �ʿ�� : Servlet API ���
 *  	: ȭ�鿡 ������ Model �� org.springframework.ui.Model ���
 *      
 *  - �ڵ鷯 �������� : Controller ���� Mapping�� �ϰ�,  Method �̸��� URI�� �����Ŵ
 *      ��) http://ip:port/Spring13/app/user/logonView
 *           �� Spring13  webApp ��...
 *           �� /app/         �� �����ϴ� ==> web.xml �� ��ϵ�
 *           �� /user/        �� @RequestMapping("/user/*") �� Mapping �� Controller
 *           �� /logonView    logonView() Method ȣ��                              
 */                                       
@Controller
@RequestMapping("/user")
public class UserController {
	
	///Field
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	///Constructor
	public UserController(){
		System.out.println("==> UserController default Constructor call.............");
	}

	
	//==> �ܼ� navigation
	@RequestMapping("/logon")
	public String logon(Model model){
		
		System.out.println("[ logon() start........]");//<== ������
		
		//==> Client �� ������  Message ���� 
		String message = "[ logon() ] ���̵�,�н����� 3���̻� �Է��ϼ���.";
		
		//==> Model (data) 
		model.addAttribute("message", message);
		
		System.out.println("[ logon()  end........]\n");
		
		
		//============= Transaction Test ���� �߰���====================//
//		try {
//			userService.addUser(new User("user04","�ָ�","user04",null,0));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//==========================================================//
		
		return "/user/logon.jsp";
	}

	
	//==> �ܼ� navigation
	@RequestMapping("/home")
	public String home( Model model ){
		
		System.out.println("[ home() start........]");//<== ������
		
		String message = "[ home()  ] WELCOME";
		
		//==> Model (data) 
		model.addAttribute("message", message);
		
		System.out.println("[ home()  end........]\n");
		
		return  "/user/home.jsp" ;
	}

	
	// Business Logic ���� / Navigation  
	//============  ������ �κ� =============================/
	// Get ��ĵ� Interceptor ���� ó����.
	@RequestMapping( value="/logonAction", method=RequestMethod.POST )
//	@RequestMapping( value="logonAction")
	public String  logonAction(	@ModelAttribute("user") User user,
													Model model , HttpSession session ) throws Exception{

		System.out.println("[ logonAction() method=RequestMethod.POST start........]");

		String viewName = "/user/logon.jsp";
		
//		User returnUser =  userService.getUser(user.getUserId());
		User returnUser = new User();
		returnUser.setActive(true);
		user = returnUser;
//		if( returnUser.getPassword().equals(user.getPassword())){
//			returnUser.setActive(true);
//			user = returnUser;
//		}
		
		if( user.isActive() ){
			viewName = "/user/home.jsp";
			session.setAttribute("sessionUser", user);
		}
			
		System.out.println("[ action : "+viewName+ "]");		//<== ������
		
		//==> Client �� ������  Message ���� 
		String message = null;
		if( viewName.equals("/user/home.jsp")){
			message = "[ LogonAction() ] WELCOME";
		}else{
			message = "[ LogonAction() ] ���̵�,�н����� 3���̻� �Է��ϼ���.";
		}
		
		//==> Model (data) 
		model.addAttribute("message", message);
		
		System.out.println("[ logonAction() method=RequestMethod.POST end........]\n");
		
		return viewName;
	}
	
	
	 // ����(logon ��������) Ȯ�� / navigation
	@RequestMapping("/logout")
	public String logout( Model model , HttpSession session){
		
		System.out.println("[ Logout() start........]");//<== ������

		//==> logon ���� ����
		session.removeAttribute("sessionUser");

		//==> Client �� ������  Message ���� 
		String message = "[Logout()] ���̵�,�н����� 3���̻� �Է��ϼ���.";
		
		//==> Model (data) 
		model.addAttribute("message", message);
		
		System.out.println("[ Logout() end........]\n");
		
		return "/user/logon.jsp"  ;
	}
	
}