package me.dyjeong365.board.controller;

import lombok.RequiredArgsConstructor;
import me.dyjeong365.board.domain.Article;
import me.dyjeong365.board.dto.ArticleDto;
import me.dyjeong365.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> saveArticle(@RequestBody ArticleDto.Create request){
        Article article = boardService.saveArticle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article);
    }
}
