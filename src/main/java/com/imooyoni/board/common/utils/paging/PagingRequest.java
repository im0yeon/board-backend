package com.imooyoni.board.common.utils.paging;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 페이징 파라미터(page, size)를 포함하는 검색 요청의 공통 부모.
 * 도메인별 검색 요청은 이 클래스를 상속받아 검색 조건 필드를 추가하고,
 * {@link #addSearchParams(List)}를 구현해 쿼리스트링에 조건을 실어 보낸다.
 */
@Getter
@Setter
public abstract class PagingRequest {

	private Integer page;
	private Integer size;

	public PageCommand toPageCommand() {
		return PageCommand.of(page, size);
	}

	/** 현재 검색 조건 + 페이징을 링크에 실어 보내기 위한 쿼리스트링. 조건이 없으면 빈 문자열. */
	public final String queryString() {
		List<String> params = new ArrayList<>();
		addSearchParams(params);
		add(params, "page", page);
		add(params, "size", size);
		return params.isEmpty() ? "" : "?" + String.join("&", params);
	}

	/** 하위 클래스가 페이징 외의 검색 조건을 쿼리스트링에 추가한다. */
	protected abstract void addSearchParams(List<String> params);

	/** null·빈 값은 제외하고 URL 인코딩하여 추가한다. */
	protected static void add(List<String> params, String name, Object value) {
		if (value == null || value.toString().isBlank()) {
			return;
		}
		params.add(name + "=" + URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
	}
}
