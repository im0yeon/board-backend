package com.imooyoni.board.data.domain.member;

import com.fixelsoft.util.common.web.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearch extends Paging<Member, MemberSearch> {

    private String userName;

    private Integer age;

    public MemberSearch() {
        super(20);
    }

    @Override
    protected Class<MemberSearch> selfType() {
        return MemberSearch.class;
    }
}
