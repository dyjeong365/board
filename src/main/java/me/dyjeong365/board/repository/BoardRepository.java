package me.dyjeong365.board.repository;

import me.dyjeong365.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Article, Long> {
}
