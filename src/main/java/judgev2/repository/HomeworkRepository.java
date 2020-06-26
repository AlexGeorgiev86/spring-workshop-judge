package judgev2.repository;

import judgev2.model.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {

    Collection<Homework> findAllByAuthor_Id(String authorId);
}
