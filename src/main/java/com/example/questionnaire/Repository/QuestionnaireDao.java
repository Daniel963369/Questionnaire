package com.example.questionnaire.Repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer> {
	

	
	
	public List<Questionnaire> findByIdIn(List<Integer> idList);
	
	public List<Questionnaire>  findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title, LocalDate startDate, LocalDate endDate);
	public List<Questionnaire>  findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title, LocalDate startDate, LocalDate endDate);
	
	@Modifying
	@Transactional
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+"values (:title,:desp,:isPublished,:startDate,:endDate)",nativeQuery = true)
	public int insert
			(@Param("title") String title, @Param("desp") String description,
			@Param("isPublished") boolean isPublished, @Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate);
	
	
	@Modifying
	@Transactional
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+"values (?1,?2,?3,?4,?5)",nativeQuery = true)
	public int insertData
			(String title,
			String description,
			boolean isPublished,
			LocalDate startDate, 
			LocalDate endDate);
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update questionnaire set title = :title, description = :desp"
	 + " where id = :id",nativeQuery = true)
	public int update(
			@Param("id") int id,
			@Param ("title")String title,
			@Param ("desp") String description);
	
	@Modifying
	@Transactional
	@Query(value = "update Questionnaire set title = :title, description = :desp, start_date = :startDate"
	+ " where id = :id",nativeQuery = true)
	public int updateData(
			@Param("id") int id,
			@Param ("title")String title,
			@Param ("desp") String description,
			@Param ("startDate") LocalDate startDate);
			
}
