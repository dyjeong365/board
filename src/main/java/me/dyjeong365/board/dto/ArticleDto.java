package me.dyjeong365.board.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dyjeong365.board.domain.Article;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleDto {

    @Getter
    @AllArgsConstructor
    public static class Create{
        @Range(min=1, max=50)
        @NotNull
        private String title;

        @Range(min=1, max=1000)
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
    public static class Update{
        private Long id;

        @Range(min=1, max=50)
        @NotNull
        private String title;

        @Range(min=1, max=1000)
        @NotNull
        private String content;

        private LocalDateTime lastModifiedDate;

        public void updateId(Long id) {
            this.id = id;
        }
    }
}
