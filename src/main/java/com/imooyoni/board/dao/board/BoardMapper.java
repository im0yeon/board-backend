package com.imooyoni.board.dao.board;

import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    long countBy(@Param("paging") BoardSearch paging);

    List<Board> selectBy(@Param("paging") BoardSearch paging);

    Board selectById(@Param("id") Long id);

    int deleteByPk(@Param("id") Long id);

    int deleteAllByPk(@Param("ids") List<Long> ids);
}
