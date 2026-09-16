package com.imooyoni.board.controller.board;

import com.imooyoni.board.data.domain.board.Board;
import com.imooyoni.board.data.domain.board.BoardForm;
import com.imooyoni.board.data.domain.board.BoardSearch;
import com.imooyoni.board.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

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

    @PostMapping
    public String registAction(@Valid @ModelAttribute("form") BoardForm form
            , BindingResult binding
            , BoardSearch paging
            , ModelMap model) {
        if (binding.hasErrors()) {
            return registForm(form, paging, model);
        }
        Long id = boardService.registBoard(form.getTitle(), form.getContent(), form.getWriter());
        return redirect("/board/" + id, paging.setPage(1));
    }

    @GetMapping("/{id}/form")
    public String modifyForm(@PathVariable Long id
            , BoardSearch paging
            , ModelMap model) {
        Board board = boardService.getBoard(id);
        return registForm(BoardForm.from(board), paging, model);
    }

    @PostMapping("/{id}")
    public String modifyAction(@PathVariable Long id, @Valid @ModelAttribute("form") BoardForm form
            , BindingResult binding
            , BoardSearch paging
            , ModelMap model) {
        if (binding.hasErrors()) {
            Board board = boardService.getBoard(id);
            return registForm(BoardForm.from(board), paging, model);
        }
        boardService.modifyBoard(id, form.getTitle(), form.getContent(), form.getWriter());
        return redirect("/board" + id, paging);
    }

    @PostMapping("/{id}/remove")
    public String deleteAction(@PathVariable Long id, BoardSearch paging) {
        boardService.removeBoard(id);
        return redirect("/board", paging);
    }

    @PostMapping("/remove")
    public String deleteAllAction(@RequestParam(required = false) List<Long> ids, BoardSearch paging) {
        boardService.removeBoards(ids);
        return redirect("/board", paging);
    }

    // 목록 검색·페이지 상태를 redirect 뒤에도 유지
    private static String redirect(String path, BoardSearch paging) {
        return "redirect:" + path + "?" + paging.getQueryString();
    }
}
