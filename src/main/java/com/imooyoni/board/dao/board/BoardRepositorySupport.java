package com.imooyoni.board.dao.board;

import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardSearch;

import java.util.List;

public interface BoardRepositorySupport {
    long countBy(BoardSearch paging);

    List<Board> selectBy(BoardSearch paging);

    Board selectById(Long id);

    int deleteByPk(Long id);

    int deleteAllByPk(List<Long> ids);
}
