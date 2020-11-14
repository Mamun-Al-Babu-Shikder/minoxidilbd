package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.TeamMember;
import com.mcubes.minoxidilbd.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/24/2020.
 */
@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public List<TeamMember> getTeamMembers(){
        return (List<TeamMember>) teamMemberRepository.findAll();
    }
}
