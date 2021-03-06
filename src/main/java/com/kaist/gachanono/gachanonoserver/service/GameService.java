package com.kaist.gachanono.gachanonoserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.domain.Game.Game;
import com.kaist.gachanono.gachanonoserver.domain.Game.GameRepository;
import com.kaist.gachanono.gachanonoserver.domain.Game.Item;
import com.kaist.gachanono.gachanonoserver.domain.Game.ItemRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final ItemRepository itemRepository;

    /* CREATE */
    // @Transactional
    // public Long save(PostsDto.Request dto, String nickname) {
    //     /* User 정보를 가져와 dto에 담아준다. */
    //     log.info("dto[{}], nickname[{}]", dto.toString(), nickname);
    //     User user = userRepository.findByNickname(nickname);
    //     dto.setUser(user);
    //     log.info("PostsService save() 실행");
    //     Posts posts = dto.toEntity();
    //     postsRepository.save(posts);

    //     return posts.getId();
    // }

    /* READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선 */
    // @Transactional(readOnly = true)
    // public PostsDto.Response findById(Long id) {
    //     Posts posts = postsRepository.findById(id).orElseThrow(() ->
    //             new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

    //     return new PostsDto.Response(posts);
    // }

    /* UPDATE (dirty checking 영속성 컨텍스트)
     *  User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
     * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다. */
    // @Transactional
    // public void update(Long id, PostsDto.Request dto) {
    //     Posts posts = postsRepository.findById(id).orElseThrow(() ->
    //             new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

    //     posts.update(dto.getTitle(), dto.getContent());
    // }

    /* DELETE */
    // @Transactional
    // public void delete(Long id) {
    //     Posts posts = postsRepository.findById(id).orElseThrow(() ->
    //             new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

    //     postsRepository.delete(posts);
    // }

    // /* Views Counting */
    // @Transactional
    // public int updateView(Long id) {
    //     return postsRepository.updateView(id);
    // }

    // /* Goods Counting */
    // @Transactional
    // public int updateGood(Long id) {
    //     return postsRepository.updateGood(id);
    // }


    /* Paging and Sort */
    @Transactional(readOnly = true)
    public List<Game> gameList() {
        return gameRepository.findAll();
    }

    /* Paging and Sort */
    @Transactional(readOnly = true)
    public Page<Item> getItemList(Long gameid, Pageable pageable) {
        log.info("gameid[{}]", gameid);
        return itemRepository.findByGameid(gameid, pageable);
    }

    @Transactional(readOnly = true)
    public String getProbtableName(Long gameid) {
        return null;
    }

    // /* search */
    // @Transactional(readOnly = true)
    // public Page<Posts> search(String keyword, Pageable pageable) {
    //     Page<Posts> postsList = postsRepository.findByTitleContaining(keyword, pageable);
    //     return postsList;
    // }
}

