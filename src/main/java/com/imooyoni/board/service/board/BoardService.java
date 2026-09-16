package com.imooyoni.board.service.board;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.config.exception.ErrorCode;
import com.imooyoni.board.dao.board.BoardRepository;
import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardEntity;
import com.imooyoni.board.data.domain.board.BoardSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardSearch getBoards(BoardSearch paging) {
        return paging
                .count(boardRepository.countBy(paging))
                .body(boardRepository::selectBy);
    }

    public Board getBoard(Long id) {
        Board board = boardRepository.selectById(id);
        if (board == null) {
            throw BaseException.of(ErrorCode.NOT_FOUND, null, "게시글");
        }
        return board;
    }

    @Transactional
    public Long registBoard(String title, String content, String writer) {
        return boardRepository.save(BoardEntity.write(title, content, writer)).getId();
    }

    @Transactional
    public void modifyBoard(Long id, String title, String content, String writer) {
        BoardEntity board = getEntity(id);
        board.modify(title, content, writer);
        boardRepository.save(board);
    }

    @Transactional
    public int removeBoard(Long id) {
        return boardRepository.deleteByPk(id);
    }

    @Transactional
    public int removeBoards(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            return boardRepository.deleteAllByPk(ids);
        }
        return 0;
    }

    // 쓰기 경로는 영속 상태 엔티티가 필요하다
    private BoardEntity getEntity(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> BaseException.of(ErrorCode.NOT_FOUND, null, "게시글"));
    }
}
