package com.ktdsuniversity.edu.pms.supply.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.pms.supply.service.SupplyService;
import com.ktdsuniversity.edu.pms.supply.vo.SearchSupplyVO;
import com.ktdsuniversity.edu.pms.supply.vo.SupplyListVO;
import com.ktdsuniversity.edu.pms.supply.vo.SupplyVO;
import com.ktdsuniversity.edu.pms.utils.ApiResponse;
import com.ktdsuniversity.edu.pms.utils.ValidationUtils;

@RestController
@RequestMapping("/api/v1")
public class ApiSupplyController {
	
	@Autowired
	private SupplyService supplyService;
	
	@GetMapping("/supply")
	public ApiResponse getSupplyList(SearchSupplyVO searchSupplyVO) {
		SupplyListVO supplyListVO = this.supplyService.searchAllSupply(searchSupplyVO);
		
		return ApiResponse.Ok(supplyListVO.getSupplyList(), 
							  supplyListVO.getSupplyCnt(), 
							  searchSupplyVO.getPageCount(), 
							  searchSupplyVO.getPageNo() < searchSupplyVO.getPageCount() - 1);
	}
	
	@GetMapping("/supply/{splId}")
	public ApiResponse getSupply(@PathVariable String splId) {
		SupplyVO supplyVO = this.supplyService.getOneSupply(splId);
		
		return ApiResponse.Ok(supplyVO);
	}
	
	@PostMapping("/supply")
	public ApiResponse doSupplyRegistration(SupplyVO supplyVO, Authentication authentication) {
		boolean isNotEmptyName = ValidationUtils.notEmpty(supplyVO.getSplName());
		boolean isNotEmptyCategory = ValidationUtils.notEmpty(supplyVO.getSplCtgr());
		boolean isNotEmptyPrice = ValidationUtils.notEmpty(Integer.toString(supplyVO.getSplPrice()));
		boolean isNotEmptyImage = ValidationUtils.notEmpty(supplyVO.getSplImg());
		boolean isNotEmptyDetail = ValidationUtils.notEmpty(supplyVO.getSplDtl());
		
		List<String> errorMessage = null;
		
		if (!isNotEmptyName) {
			if (errorMessage == null) {
				errorMessage = new ArrayList<>();
			}
			errorMessage.add("제품명을 입력해 주세요.");
		}
		
		if (!isNotEmptyCategory) {
			if (errorMessage == null) {
				errorMessage = new ArrayList<>();
			}
			errorMessage.add("제품 카테고리를 입력해 주세요.");
		}
		
		if (!isNotEmptyPrice) {
			if (errorMessage == null) {
				errorMessage = new ArrayList<>();
			}
			errorMessage.add("제품 가격을 입력해 주세요.");
		}
		
		if (!isNotEmptyImage) {
			if (errorMessage == null) {
				errorMessage = new ArrayList<>();
			}
			errorMessage.add("제품 이미지를 삽입해 주세요.");
		}
		
		if (!isNotEmptyDetail) {
			if (errorMessage == null) {
				errorMessage = new ArrayList<>();
			}
			errorMessage.add("제품 설명을 입력해 주세요.");
		}
		
		if (errorMessage != null) {
			return ApiResponse.BAD_REQUEST(errorMessage);
		}
		
//		supplyVO.setSplRegtId(authentication.getName());
		supplyVO.setSplRegtId("system01");
		
		boolean isCreateSuccess = this.supplyService.registerNewSupply(supplyVO);
		
		return ApiResponse.Ok(isCreateSuccess);
	}

}