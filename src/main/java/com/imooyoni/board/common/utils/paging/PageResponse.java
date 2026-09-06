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
}
