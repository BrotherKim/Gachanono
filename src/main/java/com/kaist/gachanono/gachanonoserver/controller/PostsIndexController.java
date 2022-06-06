package com.kaist.gachanono.gachanonoserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.config.auth.LoginUser;
import com.kaist.gachanono.gachanonoserver.domain.Board.Posts;
import com.kaist.gachanono.gachanonoserver.domain.Game.Gacha;
import com.kaist.gachanono.gachanonoserver.domain.Game.Game;
import com.kaist.gachanono.gachanonoserver.dto.CommentDto;
import com.kaist.gachanono.gachanonoserver.dto.GameDto;
import com.kaist.gachanono.gachanonoserver.dto.PostsDto;
import com.kaist.gachanono.gachanonoserver.dto.UserDto;
import com.kaist.gachanono.gachanonoserver.service.GachaService;
import com.kaist.gachanono.gachanonoserver.service.GameService;
import com.kaist.gachanono.gachanonoserver.service.PostsService;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostsIndexController {

    private final PostsService postsService;
    private final GameService gameService;
    private final GachaService gachaService;

    /* default page = 0, size = 10  */
    @GetMapping("/posts/free")
    public String free(
        Model model,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
        @LoginUser UserDto.Response user
    ) {
        Page<Posts> list = postsService.pageList(pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "posts/free";
    }

    @GetMapping("/")/* default page = 0, size = 10  */
    public String index(
        Model model,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
        @LoginUser UserDto.Response user
    ) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        List<Gacha> gachas = gachaService.gachaList();
        model.addAttribute("gachas", gachas);

        return "prob/gacha";
    }
    /* 글 작성 */
    @GetMapping("/posts/write")
    public String write(@LoginUser UserDto.Response user, Model model) {
        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-write";
    }

    /* 글 상세보기 */
    @GetMapping("/posts/read/{id}")
    public String read(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        PostsDto.Response dto = postsService.findById(id);
        List < CommentDto.Response > comments = dto.getComments();

        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                model.addAttribute("isWriter", true);
            }
            /*            for (int i = 0; i < comments.size(); i++) {
                boolean isWriter = comments.get(i).getUserId().equals(user.getId());
                model.addAttribute("isWriter",isWriter);
            }*/
        }

        postsService.updateView(id); // views ++
        model.addAttribute("posts", dto);
        return "posts/posts-read";
    }

    @GetMapping("/posts/update/{id}")
    public String update(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        List<Game> games = gameService.gameList();
        model.addAttribute("games", games);

        PostsDto.Response dto = postsService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", dto);

        return "posts/posts-update";
    }

    @GetMapping("/posts/good/{id}")
    public String good(
        @PathVariable Long id,
        @LoginUser UserDto.Response user,
        Model model
    ) {
        PostsDto.Response dto = postsService.findById(id);
        List < CommentDto.Response > comments = dto.getComments();

        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                model.addAttribute("isWriter", true);
            }
            /*            for (int i = 0; i < comments.size(); i++) {
                boolean isWriter = comments.get(i).getUserId().equals(user.getId());
                model.addAttribute("isWriter",isWriter);
            }*/
        }

        postsService.updateGood(id); // goods ++
        model.addAttribute("posts", dto);
        return "posts/posts-read";
    }

    @GetMapping("/posts/search")
    public String search(
        String keyword,
        Model model,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
        @LoginUser UserDto.Response user
    ) {
        Page<Posts> searchList = postsService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "posts/posts-search";
    }
}
