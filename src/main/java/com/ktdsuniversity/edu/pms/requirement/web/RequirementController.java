package com.ktdsuniversity.edu.pms.requirement.web;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.pms.commoncode.service.CommonCodeService;
import com.ktdsuniversity.edu.pms.commoncode.vo.CommonCodeVO;
import com.ktdsuniversity.edu.pms.login.web.LoginController;
import com.ktdsuniversity.edu.pms.project.service.ProjectService;
import com.ktdsuniversity.edu.pms.project.vo.ProjectListVO;
import com.ktdsuniversity.edu.pms.requirement.service.RequirementService;
import com.ktdsuniversity.edu.pms.requirement.vo.RequirementVO;
import com.ktdsuniversity.edu.pms.team.service.TeamService;
import com.ktdsuniversity.edu.pms.utils.AjaxResponse;

@Controller
public class RequirementController {

	@Autowired
	private RequirementService requirementService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private TeamService teamService ;

	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/requirement")
	public String viewAllRequirement(
			/* @SessionAttribute , */
			@RequestParam String prjId,
			 Model model) {

		// TODO 본인 프로젝트가 아닐경우, 잘못된 프로젝트 아이디가 입력된경우 에러페이지 & 메시지 전달

		List<RequirementVO> requirementList = requirementService.getAllRequirement();
				
//				.stream()
//				.filter((requirement) -> requirement.getPrjId().equals(prjId)).collect(Collectors.toList());

		model.addAttribute("resultList", requirementList);

		return "requirement/requirementlist";
	}

	@GetMapping("/requirement/view")
	public String viewOneRequirement(
			/* @SessionAttribute , */
			@RequestParam("prjId") String prjId, @RequestParam("rqmId") String rqmId, Model model) {
		// TODO 본인 프로젝트가 아닐경우, 잘못된 프로젝트 아이디가 입력된경우 에러페이지 & 메시지 전달
		RequirementVO requirement = this.requirementService.getOneRequirement(rqmId);

		model.addAttribute("requirement", requirement);

		return "requirement/requirementview";

	}

	@GetMapping("/requirement/write")
	public String viewwritePage(Model model
	/* @SessionAttribute , */) {
		// TODO 사원리스트도 보내줘야 담당자, 테스터, 확인자 체크가능 ->현재는 임의의 사원번호를 넣는중
		ProjectListVO projectList = this.projectService.getAllProject();
		List<CommonCodeVO> scdStsList = this.commonCodeService.getAllCommonCodeListByPId("500");
		List<CommonCodeVO> rqmStsList = this.commonCodeService.getAllCommonCodeListByPId("600");

		model.addAttribute("projectList", projectList).addAttribute("scdSts", scdStsList).addAttribute("rqmSts",
				rqmStsList);

		return "requirement/requirementwrite";

	}

	@PostMapping("/project/requirement/write")
	public String createRequirement(/* @SessionAttribute , */
			RequirementVO requirementVO, @RequestParam MultipartFile file, Model model) {
		boolean isSuccess = this.requirementService.insertOneRequirement(requirementVO, null);

		return "redirect:/project/requirement?prjId=" + requirementVO.getPrjId();
	}

	@GetMapping("/project/requirement/modify")
	public String viewModifyPage(/* @SessionAttribute , */
			@RequestParam("prjId") String prjId, @RequestParam("rqmId") String rqmId, Model model) {
//		TODO 사원리스트도 보내줘야 담당자, 테스터, 확인자 체크가능 ->현재는 임의의 사원번호를 넣는중
//		TODO 사용자 체크: 수정은 본인과 관리자만 가능함

		RequirementVO requirement = this.requirementService.getOneRequirement(rqmId);
		ProjectListVO projectList = this.projectService.getAllProject();
		List<CommonCodeVO> scdSts = this.commonCodeService.getAllCommonCodeListByPId("500");
		List<CommonCodeVO> rqmSts = this.commonCodeService.getAllCommonCodeListByPId("600");

		model.addAttribute("requirement", requirement).addAttribute("projectList", projectList)
				.addAttribute("scdSts", scdSts).addAttribute("rqmSts", rqmSts);

		return "requirement/requirementmodify";
	}

	@PostMapping("/project/requirement/modify")
	public String modifyRequirement(@RequestParam String rqmId,
			/* @SessionAttribute , */
			RequirementVO requirementVO, @RequestParam MultipartFile file) {
//		TODO 입력된 정보가 올바른지 확인 필요,  파일 업로드 체크(아직 체크 안함)

//		TODO isSuccess 의 결과에 따라 값을 다르게 반환
		boolean isSuccess = this.requirementService.updateRequirement(requirementVO, file);

		return "redirect:/project/requirement?prjId=" + requirementVO.getPrjId();

	}

	@GetMapping("/project/requirement/delete")
	public String deleteRequirement(
			/* @SessionAttribute , */
			@RequestParam String rqmId) {
		// TODO 사용자 체크: 삭제는 본인과 관리자만 가능함
		RequirementVO requirementVO = this.requirementService.getOneRequirement(rqmId);

//		TODO isSuccess 의 결과에 따라 값을 다르게 반환
		boolean isSuccess = this.requirementService.deleteOneRequirement(requirementVO);

		return "redirect:/project/requirement?prjId=" + requirementVO.getPrjId();
	}
	@ResponseBody
	@GetMapping("/project/requirement/delaycall")
	public AjaxResponse delayRequirement(/* @SessionAttribute , */
			@RequestParam String rqmId) {
//		2. 연기요청 처리
		RequirementVO thisRequirement = this.requirementService.getOneRequirement(rqmId);
		// setter 로 정보 변경후 업데이트
		boolean isSuccess = this.requirementService.delayRequirement(thisRequirement);
		
		 AjaxResponse ajax = new AjaxResponse();
		return ajax.append("result", isSuccess);

	}
	@ResponseBody
	@GetMapping("/project/requirement/delayaccess")
	public AjaxResponse accessDelay(
			/* @SessionAttribute , */
			@RequestParam String rqmId, @RequestParam boolean dalayApprove) {

		boolean isSuccess = this.requirementService.updateDelayRequirement(rqmId, dalayApprove);
		
		AjaxResponse ajax= new AjaxResponse();
		return ajax.append("result", isSuccess).append("dalayApprove", dalayApprove);
		
	}

}
