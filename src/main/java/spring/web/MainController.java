package spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Welcome Page  : �������� ����� Controller �� ����.
 */
@Controller
public class MainController {
	
	///Constructor
	public MainController(){
		System.out.println("==> MainController default Constructor call.............");
	}


	  @GetMapping("/") public String index() {

	  System.out.println("[ MainController.index() start........]");//<== ������
	  //==> web.xml : <welcome-file>~~~.html or ~~.jsp</welcome-fle> : �������� �����̾��
	  return "redirect:/user/logon.jsp";

	  }

}
