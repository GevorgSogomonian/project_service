package faang.school.projectservice.jpa;

import faang.school.projectservice.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
    @Query(
            "SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM Project p " +
                    "WHERE p.ownerId = :ownerId AND p.name = :name"
    )
    boolean existsByOwnerIdAndName(Long ownerId, String name);

    @Query(
            value = "WITH RECURSIVE search(id) AS (" +
                    "   SELECT p.id" +
                    "   FROM project p" +
                    "   WHERE p.parent_project_id = :projectId" +
                    "   UNION ALL" +
                    "   SELECT p.id" +
                    "   FROM project p" +
                    "       INNER JOIN search s " +
                    "           ON s.id = p.parent_project_id" +
                    ") " +
                    "SELECT DISTINCT project.id, name, description, storage_size, max_storage_size, owner_id, " +
                    "parent_project_id, created_at, updated_at, status, visibility, cover_image_id " +
                    "   FROM project " +
                    "       INNER JOIN search ON search.id = project.id",
            nativeQuery = true
    )
    List<Project> getAllSubProjectsFor(Long projectId);
}

