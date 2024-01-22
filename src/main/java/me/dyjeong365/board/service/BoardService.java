package me.dyjeong365.board.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.dyjeong365.board.domain.Article;
import me.dyjeong365.board.dto.ArticleDto;
import me.dyjeong365.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private static final String NOT_FOUND_ARTICLE = "해당하는 id가 없습니다.";

    public Article saveArticle(ArticleDto.Create request) {
        return boardRepository.save(request.toEntity());
    }

    public Article findArticle(Long id) {
        return validateId(id);
    }

    public List<Article> findArticles() {
        return boardRepository.findAll();
    }

    @Transactional
    public Article updateArticle(Long id, ArticleDto.Update request) {
        Article exisitingArticle = validateId(id);

        exisitingArticle.update(request.getTitle(), request.getContent());

        return boardRepository.save(exisitingArticle);
    }
    private Article validateId(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ARTICLE));
    }
}
