package com.example.questionnaire.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.Repository.QuestionDao;
import com.example.questionnaire.Repository.QuestionnaireDao;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;

@EnableScheduling
@Service
public class QuizServiceImpl implements QuizService{
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;

	@Transactional
	@Override
	public QuizRes create(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		List<Question>quList = req.getQuestionList();
		if( checkResult != null) {
			return checkResult;
		}
		int quId = questionnaireDao.save(req.getQuestionnaire()).getId();
//		questionnaireDao.save(req.getQuestionnaire());
//		List<Question> quList = req.getQuestionList();
		if (quList.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		for(Question qu: quList) {
			qu.setQnId(quId);
		}
		
		questionDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
		
	}

	private QuizRes checkParam(QuizReq req) {
		Questionnaire qn = req.getQuestionnaire();
		if(!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription()) 
				|| qn.getStartDate() == null || qn.getEndDate() == null || qn.getStartDate().isAfter(qn.getEndDate()))
			 {
			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);
		}
		
		
		List<Question> quList = req.getQuestionList();
		for(Question qu:quList) {
			if(qu.getQuId() <= 0 || !StringUtils.hasText(qu.getqTitle()) 
					|| !StringUtils.hasText(qu.getOptionType()) || !StringUtils.hasText(qu.getOption())) {
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		return null;
	}
	
	@Transactional
	@Override
	public QuizRes update(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		if( checkResult != null) {
			return checkResult;
		}
		checkResult = checkQuestionnaireId(req);
		if(checkResult != null) {
			return checkResult;
		}
		Optional<Questionnaire> qnOp = questionnaireDao.findById(req.getQuestionnaire().getId());
		if(qnOp.isEmpty()) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
		
		//collection delete_question_id
		List<Integer> deleteQuIdList = new ArrayList<>();
		for(Question qu : req.getDeleteQuestionList()) {
			deleteQuIdList.add(qu.getQuId());
		}
		
		
		Questionnaire qn = qnOp.get();
		//可以修改的條件:
		//1.尚未發布:is_published == false 可以修改
		//2.已發布但尚未開始進行:is_published == true 當前時間必須小於start_date
		if(qn.isPublished()== false || (qn.isPublished()==true && LocalDate.now().isBefore(qn.getStartDate()))) {
			questionnaireDao.save(req.getQuestionnaire());
			questionDao.saveAll(req.getQuestionList());
			if(!deleteQuIdList.isEmpty()) {
				questionDao.deleteAllByQnIdAndQuIdIn(qn.getId(), deleteQuIdList);
			}
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}

	
	

	
	
	private QuizRes checkQuestionnaireId(QuizReq req) {
		if(req.getQuestionnaire().getId()<= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQuestionnaire().getId()) {
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}
		List<Question> quDelList = req.getDeleteQuestionList();
			for(Question qu :quDelList) {
				if(qu.getQnId() != req.getQuestionnaire().getId()) {
					return new QuizRes(RtnCode.QUESTION_ID_PARAM_ERROR);
				}
			}
		
		return null;
	}
	
	@Transactional
	@Override
	public QuizRes deleteQustionnaire(List<Integer> qnIdList) {
		List<Questionnaire> qnList = questionnaireDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList<>();
		for( Questionnaire qn :qnList) {
			if(!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
//				questionnaireDao.deleteById(qn.getId());
				idList.add(qn.getId());
				
			}
		}
		if(!idList.isEmpty()) {
			questionnaireDao.deleteAllById(idList);
			questionDao.deleteAllByQnIdIn(idList);
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}
	
	@Transactional
	@Override
	public QuizRes deleteQustion(int qnId, List<Integer> quIdList) {
	Optional<Questionnaire> qnOp = questionnaireDao.findById(qnId);
	if(qnOp.isEmpty()) {
		return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
	}
	Questionnaire qn = qnOp.get();
	if(!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
		
		questionDao.deleteAllByQnIdAndQuIdIn(qnId, quIdList);
	}
	
	return new QuizRes(RtnCode.SUCCESSFUL);
	}
	
//	@Cacheable(cacheNames = "search",
//			//key = "test_2023-11-25_2023-11-30"
//			key = "#title.concat('_').concat(#startDate.toString()).concat('_').concat(#endDate.toString())",
//			unless = "#result.rtnCode.code != 200")
//	@CacheEvict(cacheNames = "deleteQuestionnaire", allEntries = true)//清除緩存
	@Override
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		title = StringUtils.hasText(title)? title:"";
		startDate =startDate != null ? startDate:LocalDate.of(1971,1,1);
		endDate = endDate != null? endDate: LocalDate.of(2099,12,31);
//		if(!StringUtils.hasText(title)) {
//			title = "";
//		}
//		if(startDate == null) {
//			startDate = LocalDate.of(1971,1,1);
//			
//		}
//		if(endDate == null) {
//			endDate = LocalDate.of(2099,12,31);
//		}
		List<Questionnaire> qnList = questionnaireDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		List<Integer> qnIdList = new ArrayList<>();
		for(Questionnaire qu:qnList) {
			qnIdList.add(qu.getId());
		}
		
		
		List<Question>quList = questionDao.findAllByQnIdIn(qnIdList);
		List<QuizVo> quizVoList = new ArrayList<>();
		for(Questionnaire qn :qnList) {
			QuizVo vo = new QuizVo();
			vo.setQuestionnaire(qn);
			List<Question>questionList = new ArrayList<>();
			for(Question qu :quList) {
				if(qu.getQnId() == qn.getId()) {
					questionList.add(qu);
				}
			}
		vo.setQuestionList(questionList);
		quizVoList.add(vo);
	}
	return new QuizRes(quizVoList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isAll) {
		title = StringUtils.hasText(title)? title:"";
		startDate =startDate == null ? LocalDate.of(1971,1,1):startDate;
		endDate = endDate == null? LocalDate.of(2099,12,31):endDate;
		List<Questionnaire> qnList = new ArrayList<>();
		if(!isAll) {
			 qnList = questionnaireDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(title, startDate, endDate);
			 
		}else {
			qnList = questionnaireDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		}
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionRes searchQuestionList(int qnId) {
		if(qnId<=0) {
			return new QuestionRes (null,RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		
		List<Question> quList = questionDao.findAllByQnIdIn(new ArrayList<>(Arrays.asList(qnId)));
		return new QuestionRes(quList,RtnCode.SUCCESSFUL);
	}
	
	
//	@Scheduled(cron = "0/5 * 15 * * * ")
//	public void schedule() {
//		System.out.println(LocalDateTime.now());
//		
//	}
	
	
//	@Scheduled(cron = "0 0 0 * * * ")
//	public void updateQnStatue(){
//		LocalDate today = LocalDate.now();
//		int res = questionnaireDao.updateQnStatus(today);
//	}
	
	
}
