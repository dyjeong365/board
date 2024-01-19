package me.dyjeong365.board.service;

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

    public Article saveArticle(ArticleDto.Create request) {
        return boardRepository.save(request.toEntity());
    }

    public Article findArticle(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id가 없습니다."));
    }

    public List<Article> findArticles() {
        return boardRepository.findAll();
    }
}
