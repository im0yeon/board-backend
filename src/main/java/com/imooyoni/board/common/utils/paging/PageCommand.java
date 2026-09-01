package com.imooyoni.board.common.utils.paging;

public record PageCommand(int page, int size) {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    public PageCommand {
        page = page < DEFAULT_PAGE ? DEFAULT_PAGE : page;
        size = size < 1 ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    }

    public static PageCommand of(Integer page, Integer size) {
        return new PageCommand(
                page == null ? DEFAULT_PAGE : page,
                size == null ? DEFAULT_SIZE : size);
    }

    public static PageCommand defaults() {
        return new PageCommand(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    /** Querydsl / JPQL의 offset 값. int 오버플로를 피하기 위해 long으로 계산한다. */
    public long offset() {
        return (long) (page - DEFAULT_PAGE) * size;
    }

    public int limit() {
        return size;
    }
}
