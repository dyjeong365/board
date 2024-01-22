package me.dyjeong365.board.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.dyjeong365.board.domain.Article;
import me.dyjeong365.board.dto.ArticleDto;
import me.dyjeong365.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> postArticle(@RequestBody ArticleDto.Create request) {
        Article article = boardService.saveArticle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        Article article = boardService.findArticle(id);

        return ResponseEntity.ok(article);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = boardService.findArticles();

        return ResponseEntity.ok(articles);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patchArticle(@Valid @PathVariable Long id,
                                                @RequestBody ArticleDto.Update request) {
        request.updateId(id);
        Article article = boardService.updateArticle(request);

        return ResponseEntity.ok(article);
    }
}
