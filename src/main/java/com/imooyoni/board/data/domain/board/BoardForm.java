package com.imooyoni.board.data.domain.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 200, message = "제목은 200자 이내로 입력하세요")
    private String title;

    @NotBlank(message = "작성자를 입력하세요")
    @Size(max = 50, message = "작성자는 50자 이내로 입력하세요")
    private String writer;

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    public static BoardForm from(Board board) {
        BoardForm form = new BoardForm();
        form.setTitle(board.getTitle());
        form.setWriter(board.getWriter());
        form.setContent(board.getContent());
        return form;
    }
}
