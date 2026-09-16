package com.imooyoni.board.dao.board;

import com.imooyoni.board.data.domain.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>, BoardRepositorySupport {
}
