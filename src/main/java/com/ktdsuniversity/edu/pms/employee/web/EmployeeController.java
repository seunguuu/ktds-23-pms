package com.ktdsuniversity.edu.pms.employee.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.pms.beans.FileHandler;
import com.ktdsuniversity.edu.pms.employee.service.EmployeeService;
import com.ktdsuniversity.edu.pms.employee.vo.EmployeeListVO;
import com.ktdsuniversity.edu.pms.employee.vo.EmployeeVO;
import com.ktdsuniversity.edu.pms.employee.vo.SearchEmployeeVO;
import com.ktdsuniversity.edu.pms.utils.AjaxResponse;
import com.ktdsuniversity.edu.pms.utils.Validator;
import com.ktdsuniversity.edu.pms.utils.Validator.Type;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private FileHandler fileHandler;

	@GetMapping("/employee/search")
	public String viewEmployeeListPage(Model model, SearchEmployeeVO searchEmployeeVO) {
		EmployeeListVO employeeListVO = this.employeeService.searchAllEmployee(searchEmployeeVO);
		model.addAttribute("employeeList", employeeListVO);
		model.addAttribute("searchEmployeeVO", searchEmployeeVO);
		return "employee/employeelist"; // employeelist JSP에 보낸다.
	}

	/**
	 * 회원가입 페이지
	 */
	@GetMapping("/employee/regist")
	public String viewRegistPage() {
		return "employee/regist";
	}

	@ResponseBody
	@PostMapping("/ajax/employee/regist")
	public AjaxResponse doRegist(EmployeeVO employeeVO, @RequestParam(defaultValue = "/main/mainpage") String nextUrl, Model model) {
		/**
		 * 수정해야할 사항 
		 * 비밀번호를 직접 받지않고 사원번호 + 입사일 이런식으로 기본 비번 설정
		 * 임원여부 체크박스로 만들어서 체크 안하면 N 체크하면 Y
		 * 프로필 사진 첨부파일 기능 만들기
		 */
		
		Validator<EmployeeVO> validator = new Validator<>(employeeVO);
		
		validator.add("empId", Type.NOT_EMPTY, "사원번호를 입력해 주세요.")
				.add("empId", Type.EMPID, "사원번호 형식으로 입력해 주세요")
				.add("empName", Type.NOT_EMPTY, "사원이름을 입력해 주세요")
				.add("hireDt", Type.NOT_EMPTY, "입사일을 지정해 주세요")
				.add("addr", Type.NOT_EMPTY, "주소를 입력해 주세요")
				.add("brth", Type.NOT_EMPTY, "생일을 입력해 주세요")
				.add("email", Type.NOT_EMPTY, "이메일을 입력해 주세요")
				.add("email", Type.EMAIL, "이메일 형식으로 입력해 주세요")
				.add("deptId", Type.NOT_EMPTY, "부서ID를 입력해 주세요")
				.add("deptId", Type.DEPTID, "부서ID형식으로 입력해 주세요")
				.add("jobId", Type.NOT_EMPTY, "직무ID를 입력해 주세요")
				.add("jobId", Type.JOBID, "직무ID형식으로 입력해주세요")
				.add("pstnId", Type.NOT_EMPTY, "직급ID를 입력해 주세요")
				.add("pstnId", Type.PSTNID, "직급ID형식으로 입력해 주세요").start();
		
		if (validator.hasErrors()) {
			Map<String, List<String>> errors = validator.getErrors();
			return new AjaxResponse().append("errors", errors);
		}
		
		
		boolean createEmpSuccess = this.employeeService.createEmployee(employeeVO);
		
		// 사원 회원가입에 성공했다면
		if (createEmpSuccess) {
			// 임시로 메인페이지로 이동
			return new AjaxResponse().append("next", nextUrl);
		}
		return new AjaxResponse().append("errorMessage", "실패사유");
	}

}
