package com.example.questionnaire.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.QuestionId;


@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionId> {
	
	public void deleteAllByQnIdIn(List<Integer> qnidList);
	
//	public List<Question> findByQuIdAndQnId(List<Integer> idList, int qnId);
	public void deleteAllByQnIdAndQuIdIn(int qnId,List<Integer> quIdList);
	public List<Question> findByQuIdIn(List<Integer> idList);
	public List<Question>findAllByQnIdIn(List<Integer> idList);
	
	@Modifying
	@Transactional
	@Query(value = "insert into question (q_title,option_type,is_necessary,q_option)"
	+ "values(?1,?2,?3,?4)",nativeQuery = true)
	public int insert(String qTitle ,String optionType, boolean isNecessary, String qOption);
}
