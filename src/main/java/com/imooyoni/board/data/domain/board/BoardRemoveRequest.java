package com.imooyoni.board.data.domain.board;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BoardRemoveRequest(
        @NotEmpty(message = "삭제할 게시글을 선택하세요")
        List<Long> ids) {
}
