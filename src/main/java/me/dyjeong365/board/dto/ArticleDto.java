package me.dyjeong365.board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dyjeong365.board.domain.Article;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDto {

    @Getter
    @AllArgsConstructor
    public static class Create {
        @Size(min = 1, max = 50)
        @NotNull
        private String title;

        @Size(min = 1, max = 1000)
        @NotNull
        private String content;

        public Article toEntity() {
            return Article.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Update {
        @Size(min = 1, max = 50)
        @NotNull
        private String title;

        @Size(min = 1, max = 1000)
        @NotNull
        private String content;
    }
}
