package me.dyjeong365.board.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import java.util.List;
import me.dyjeong365.board.domain.Article;
import me.dyjeong365.board.dto.ArticleDto;
import me.dyjeong365.board.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BoardApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("saveArticle(): 게시판 글을 성공적으로 작성한다.")
    @Test
    void postArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        ArticleDto.Create request = new ArticleDto.Create(title, content);
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("findArticle(): 특정 id의 글을 검색한다.")
    @Test
    void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        ArticleDto.Create request = new ArticleDto.Create(title, content);
        Article article = boardRepository.save(request.toEntity());

        // when
        ResultActions resultActions = mockMvc.perform(get(url, article.getId()));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("findArticles(): 게시판 글 목록을 검색한다.")
    @Test
    void findArticles() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        ArticleDto.Create request = new ArticleDto.Create(title, content);
        boardRepository.save(request.toEntity());

        final String title2 = "title2";
        final String content2 = "content2";
        ArticleDto.Create request2 = new ArticleDto.Create(title2, content2);
        boardRepository.save(request2.toEntity());

        // when
        ResultActions resultActions = mockMvc.perform(get(url));

        // then
        MvcResult result = resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content))
                .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$");
        assertThat(list.size(), is(2));
    }
}
