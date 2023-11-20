package com.example.questionnaire.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.QuestionId;
import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionId> {
	
	public void deleteAllByQnIdIn(List<Integer> qnidList);
	
//	public List<Question> findByQuIdAndQnId(List<Integer> idList, int qnId);
	public void deleteAllByQnIdAndQuIdIn(int qnId,List<Integer> quIdList);
	public List<Question> findByQuIdIn(List<Integer> idList);
	public List<Question>findAllByQnIdIn(List<Integer> idList);
}
