package com.imooyoni.board.data.domain.board;

import com.fixelsoft.util.common.domain.QueryParams;
import com.fixelsoft.util.common.web.Paging;
import com.imooyoni.board.common.utils.time.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class BoardSearch extends Paging<Board, BoardSearch> {

    private String searchTitle;
    private String searchWriter;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public BoardSearch() {
        super(5);
    }

    public LocalDateTime getStartDateTime() {
        return DateUtils.startOfDay(startDate);
    }

    // 종료일 당일 포함 - 다음 날 0시 미만으로 비교
    public LocalDateTime getEndDateTime() {
        return DateUtils.startOfNextDay(endDate);
    }

    @Override
    protected void appendCustomParams(QueryParams paramMap) {
        paramMap.add("searchTitle", searchTitle);
        paramMap.add("searchWriter", searchWriter);
        paramMap.add("startDate", Objects.toString(startDate, null));
        paramMap.add("endDate", Objects.toString(endDate, null));
    }

    @Override
    protected Class<BoardSearch> selfType() {
        return BoardSearch.class;
    }
}
