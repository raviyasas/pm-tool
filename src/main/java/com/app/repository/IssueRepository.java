package com.app.repository;

import com.app.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findAllByOrderByCreatedDateAsc();

    @Query(value="select * from issue a where a.project_id = :projectId", nativeQuery=true)
    List<Issue> findAllIssuesByProject(Integer projectId);
}
