package com.jinjin.jintranet.commuting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jinjin.jintranet.commuting.dto.CommutingsInterface;
import com.jinjin.jintranet.model.Commuting;
import com.jinjin.jintranet.model.Member;

public interface CommutingRepository extends JpaRepository<Commuting, Integer>{
	
	@Query(value="SELECT commutingTm FROM commuting WHERE memberId = ?1 AND attendYn ='Y' AND left(commutingTm,10) = left(now(),10) order by commutingTm desc limit 1", nativeQuery = true)
	String goToWorkTime(Member member);
	
	@Query(value="SELECT commutingTm FROM commuting WHERE memberId = ?1 AND attendYn ='N' AND left(commutingTm,10) = left(now(),10) order by commutingTm desc limit 1", nativeQuery = true)
	String offToWorkTime(Member member);
	
	@Query(value="select if(attendYn = 'Y' , '근무중' , '퇴근') from commuting where curdate() = date_format(commutingTm,'%Y-%m-%d') AND memberId = ?1 order by commutingTm desc limit 1", nativeQuery = true)
	String workingStatus(Member member);
	
	@Query(value="SELECT com.id , com.commutingTm , com.attendYn ,  count(com.commutingTm) over(partition by date_format(commutingTm,'%Y-%m-%d') , attendYn order by id rows 1 preceding) as cnt FROM commuting com WHERE com.memberId = ?1 AND date_format(com.commutingTm,'%Y-%m-%d') >= ?2 AND date_format(com.commutingTm,'%Y-%m-%d') <= ?3 and attendYn='Y' order by com.commutingTm desc", nativeQuery = true)
	//@Query(value="SELECT * FROM commuting WHERE memberId = ?1 AND date_format(commutingTm,'%Y-%m-%d') >= ?2 AND date_format(commutingTm,'%Y-%m-%d') <= ?3 order by commutingTm desc", nativeQuery = true)
	List<CommutingsInterface> findCommuteOn(Member member , String strDt , String endDt);
	
	@Query(value="SELECT com.id , com.commutingTm , com.attendYn ,  count(com.commutingTm) over(partition by date_format(commutingTm,'%Y-%m-%d') , attendYn order by id desc rows 1 preceding) as cnt FROM commuting com WHERE com.memberId = ?1 AND date_format(com.commutingTm,'%Y-%m-%d') >= ?2 AND date_format(com.commutingTm,'%Y-%m-%d') <= ?3 and attendYn!='Y' order by com.commutingTm desc", nativeQuery = true)
	//@Query(value="SELECT * FROM commuting WHERE memberId = ?1 AND date_format(commutingTm,'%Y-%m-%d') >= ?2 AND date_format(commutingTm,'%Y-%m-%d') <= ?3 order by commutingTm desc", nativeQuery = true)
	List<CommutingsInterface> findCommuteOff(Member member , String strDt , String endDt);
}
