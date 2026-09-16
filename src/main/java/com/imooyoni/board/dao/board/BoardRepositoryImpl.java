package com.imooyoni.board.dao.board;

import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositorySupport {

    private final BoardMapper boardMapper;

    @Override
    public long countBy(BoardSearch paging) {
        return boardMapper.countBy(paging);
    }

    @Override
    public List<Board> selectBy(BoardSearch paging) {
        return boardMapper.selectBy(paging);
    }

    @Override
    public Board selectById(Long id) {
        return boardMapper.selectById(id);
    }

    @Override
    public int deleteByPk(Long id) {
        return boardMapper.deleteByPk(id);
    }

    @Override
    public int deleteAllByPk(List<Long> ids) {
        return boardMapper.deleteAllByPk(ids);
    }
}
