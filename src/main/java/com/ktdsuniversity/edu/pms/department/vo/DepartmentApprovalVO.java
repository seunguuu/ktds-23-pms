package com.ktdsuniversity.edu.pms.department.vo;

import com.ktdsuniversity.edu.pms.approval.vo.ApprovalVO;
import com.ktdsuniversity.edu.pms.employee.vo.EmployeeVO;

public class DepartmentApprovalVO extends DepartmentVO {

	private String deptApprId;
	private String deptApprType;
	private String delYn;
	private String deptApprYn;
	private ApprovalVO approvalVO;
	private String deptApprReqtr;
	private String deptApprReason;
	private EmployeeVO employeeVO;

	public EmployeeVO getEmployeeVO() {
		return employeeVO;
	}

	public String getDeptApprReason() {
		return deptApprReason;
	}

	public void setDeptApprReason(String deptApprReason) {
		this.deptApprReason = deptApprReason;
	}

	public void setEmployeeVO(EmployeeVO employeeVO) {
		this.employeeVO = employeeVO;
	}

	public String getDeptApprReqtr() {
		return deptApprReqtr;
	}

	public void setDeptApprReqtr(String deptApprReqtr) {
		this.deptApprReqtr = deptApprReqtr;
	}

	public String getDeptApprId() {
		return deptApprId;
	}

	public void setDeptApprId(String deptApprId) {
		this.deptApprId = deptApprId;
	}

	public String getDeptApprType() {
		return deptApprType;
	}

	public void setDeptApprType(String deptApprType) {
		this.deptApprType = deptApprType;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getDeptApprYn() {
		return deptApprYn;
	}

	public void setDeptApprYn(String deptApprYn) {
		this.deptApprYn = deptApprYn;
	}

	public ApprovalVO getApprovalVO() {
		return approvalVO;
	}

	public void setApprovalVO(ApprovalVO approvalVO) {
		this.approvalVO = approvalVO;
	}

}
