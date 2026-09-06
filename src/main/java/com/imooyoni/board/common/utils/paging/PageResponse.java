package com.imooyoni.board.common.utils.paging;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalCnt,
        int totalPages,
        boolean hasNext
) {

    private static final int PAGE_BLOCK_SIZE = 10;

    public static <T> PageResponse<T> of(List<T> content, PageCommand command, long totalElements) {
        int totalPages = command.size() == 0
                ? 0
                : (int) Math.ceil((double) totalElements / command.size());

        return new PageResponse<>(
                content,
                command.page(),
                command.size(),
                totalElements,
                totalPages,
                command.page() < totalPages);
    }

    public static <T> PageResponse<T> empty(PageCommand command) {
        return new PageResponse<>(List.of(), command.page(), command.size(), 0L, 0, false);
    }

    public <R> PageResponse<R> map(Function<? super T, ? extends R> mapper) {
        return new PageResponse<>(
                content.stream().<R>map(mapper).toList(),
                page,
                size,
                totalCnt,
                totalPages,
                hasNext);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    /** 페이지 번호 노출 블록의 시작 페이지. 1-based. */
    public int startPage() {
        return (page - 1) / PAGE_BLOCK_SIZE * PAGE_BLOCK_SIZE + 1;
    }

    /** 페이지 번호 노출 블록의 끝 페이지. 마지막 블록은 totalPages 에서 잘린다. */
    public int endPage() {
        return Math.min(startPage() + PAGE_BLOCK_SIZE - 1, totalPages);
    }
}
