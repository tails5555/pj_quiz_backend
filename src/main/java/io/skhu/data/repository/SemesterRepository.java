package io.skhu.data.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.skhu.data.domain.Semester;
public interface SemesterRepository extends JpaRepository<Semester, Integer>{
	@Query("SELECT s FROM Semester s WHERE :date BETWEEN s.startDate AND s.endDate")
	Semester findByCurrentDate(@Param("date") Date date);
}
