package com.imooyoni.board.controller.board;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.BaseResponse;
import com.imooyoni.board.config.exception.ErrorCode;
import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardForm;
import com.imooyoni.board.data.domain.board.BoardRemoveRequest;
import com.imooyoni.board.data.domain.board.BoardSearch;
import com.imooyoni.board.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BaseResponse<Long> regist(@Valid @RequestBody BoardForm form) {
        return BaseResponse.success(
                boardService.registBoard(form.getTitle(), form.getContent(), form.getWriter())
        );
    }

    @PutMapping("/{id}")
    public BaseResponse<Void> modify(@PathVariable Long id, @Valid @RequestBody BoardForm form) {
        boardService.modifyBoard(id, form.getTitle(), form.getContent(), form.getWriter());
        return BaseResponse.success();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> remove(@PathVariable Long id) {
        boardService.removeBoard(id);
        return BaseResponse.success();
    }

    @DeleteMapping
    public BaseResponse<Void> remove(@Valid @RequestBody BoardRemoveRequest request) {
        boardService.removeBoards(request.ids());
        return BaseResponse.success();
    }

    // 입력란 옆에 붙일 수 있도록 필드별로 내려준다
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(ErrorCode.INVALID_INPUT.getStatus())
                .body(BaseResponse.error(BaseException.of(ErrorCode.INVALID_INPUT, fieldErrors)));
    }


    @Controller
    @RequestMapping("/board")
    @RequiredArgsConstructor
    static class View {

        private final BoardService boardService;

        @GetMapping(value = {"", "/"})
        public String listView(BoardSearch paging, ModelMap model) {
            model.put("paging", boardService.getBoards(paging));
            return "board/list";
        }

        @GetMapping("/{id}")
        public String detailView(@PathVariable Long id
                , BoardSearch paging
                , ModelMap model) {
            model.put("paging", paging);
            model.put("board", boardService.getBoard(id));
            return "board/detail";
        }

        @GetMapping("/form")
        public String registForm(@ModelAttribute("form") BoardForm form, BoardSearch paging, ModelMap model) {
            model.put("paging", paging);
            model.put("form", form);
            return "board/form";
        }

        @GetMapping("/{id}/form")
        public String modifyForm(@PathVariable Long id
                , BoardSearch paging
                , ModelMap model) {
            Board board = boardService.getBoard(id);
            model.put("boardId", id);
            return registForm(BoardForm.from(board), paging, model);
        }
    }

}
