package com.kaist.gachanono.gachanonoserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.kaist.gachanono.gachanonoserver.domain.History.History;
import com.kaist.gachanono.gachanonoserver.domain.User.User;
import com.kaist.gachanono.gachanonoserver.domain.persistence.HistoryRepository;
import com.kaist.gachanono.gachanonoserver.domain.persistence.UserRepository;
import com.kaist.gachanono.gachanonoserver.dto.HistoryDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HistoryService {

    private final HistoryRepository HistoryRepository;
    private final UserRepository userRepository;

    /* CREATE */
    @Transactional
    public Long save(HistoryDto.Request dto, String nickname) {
        /* User 정보를 가져와 dto에 담아준다. */
        log.info("dto[{}], nickname[{}]", dto.toString(), nickname);
        User user = userRepository.findByNickname(nickname);
        dto.setUser(user);
        log.info("HistoryService save() 실행");
        History History = dto.toEntity();
        HistoryRepository.save(History);

        return History.getId();
    }

    /* READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선 */
    @Transactional(readOnly = true)
    public HistoryDto.Response findById(Long id) {
        History History = HistoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        return new HistoryDto.Response(History);
    }

    // /* UPDATE (dirty checking 영속성 컨텍스트)
    //  *  User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
    //  * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다. */
    // @Transactional
    // public void update(Long id, HistoryDto.Request dto) {
    //     History History = HistoryRepository.findById(id).orElseThrow(() ->
    //             new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

    //     History.update(dto.getTitle(), dto.getContent());
    // }

    /* DELETE */
    @Transactional
    public void delete(Long id) {
        History History = HistoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        HistoryRepository.delete(History);
    }

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return HistoryRepository.updateView(id);
    }

    /* Goods Counting */
    @Transactional
    public int updateGood(Long id) {
        return HistoryRepository.updateGood(id);
    }


    /* Paging and Sort */
    @Transactional(readOnly = true)
    public Page<History> pageList(Pageable pageable) {
        return HistoryRepository.findAll(pageable);
    }

    /* search */
    @Transactional(readOnly = true)
    public Page<History> search(String keyword, Pageable pageable) {
        Page<History> HistoryList = HistoryRepository.findByTitleContaining(keyword, pageable);
        return HistoryList;
    }
}

