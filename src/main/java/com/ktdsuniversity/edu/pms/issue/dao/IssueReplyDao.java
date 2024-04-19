package com.ktdsuniversity.edu.pms.issue.dao;

import java.util.List;

import com.ktdsuniversity.edu.pms.issue.vo.IssueReplyVO;
import com.ktdsuniversity.edu.pms.issue.vo.SearchIssueReplyVO;

public interface IssueReplyDao {

	public String NAME_SPACE = "com.ktdsuniversity.edu.pms.issue.dao.IssueReplyDao";
	
	public List<IssueReplyVO> getAllReplies(SearchIssueReplyVO searchIssueReplyVO);
	
	public IssueReplyVO getOneReply(String replyId);
	
	public int createNewIssueReply(String replyId);
	
	public int deleteOneIssueReply(String replyId);
	
	public int modifyOneIssueReply(IssueReplyVO issueReplyVO);
	
	public int recommendOneIssueReply(String replyId);
}
