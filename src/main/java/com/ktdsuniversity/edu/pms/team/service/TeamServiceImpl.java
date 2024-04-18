package com.ktdsuniversity.edu.pms.team.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.pms.team.dao.TeamDao;
import com.ktdsuniversity.edu.pms.team.vo.TeamListVO;
import com.ktdsuniversity.edu.pms.team.vo.TeamVO;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	private TeamDao teamDao;
	
	@Override
	public boolean createNewTeam(TeamVO teamVO) {
		
		int insertedCount = this.teamDao.createNewTeam(teamVO);
		return insertedCount > 0;
	}

	@Override
	public TeamVO selectOneTeam(String teamId) {

		TeamVO teamVO = this.teamDao.getOneTeam(teamId);
		
		return teamVO;
	}

	@Transactional
	@Override
	public boolean modifyOneTeam(TeamVO teamVO) {
		
		return teamDao.updateOneTeam(teamVO) > 0;
	}

	@Override
	public TeamListVO getOnlyTeam() {
		
		List<TeamVO> onlyTeamListVO = this.teamDao.getOnlyTeam();
		TeamListVO teamListVO = new TeamListVO();
		teamListVO.setTeamList(onlyTeamListVO);
		return teamListVO;
	}

}
