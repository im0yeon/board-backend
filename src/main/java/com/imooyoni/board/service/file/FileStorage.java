package com.imooyoni.board.service.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.utils.io.FileUtils;
import com.imooyoni.board.common.utils.lang.StringUtils;
import com.imooyoni.board.config.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileStorage {

	public static final String URL_PREFIX = "/files";

	private final Path baseDir;

	public FileStorage(@Value("${file.base-dir}") String baseDir) {
		this.baseDir = Paths.get(baseDir).toAbsolutePath().normalize();
	}

	public Path getBaseDir() {
		return baseDir;
	}

	// 저장 위치: files/{메뉴}/{키 id}/{저장 파일명}
	public String store(MultipartFile file, String menu, Long keyId) {
		String storedName = FileUtils.toStoredName(file.getOriginalFilename());
		Path directory = baseDir.resolve(menu).resolve(String.valueOf(keyId));

		try {
			Files.createDirectories(directory);
			file.transferTo(directory.resolve(storedName));
		} catch (IOException e) {
			log.error("파일 저장 실패: menu={}, keyId={}, storedName={}", menu, keyId, storedName, e);
			throw BaseException.of(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return String.join("/", URL_PREFIX, menu, String.valueOf(keyId), storedName);
	}

	// 삭제 실패는 게시글 처리를 막지 않는다
	public void delete(String fileUrl) {
		Path target = resolve(fileUrl);
		if (target == null) {
			return;
		}

		try {
			Files.deleteIfExists(target);
		} catch (IOException e) {
			log.warn("파일 삭제 실패: fileUrl={}", fileUrl, e);
		}
	}

	// 저장 루트 밖을 가리키면 무시한다
	private Path resolve(String fileUrl) {
		if (StringUtils.isBlank(fileUrl) || !fileUrl.startsWith(URL_PREFIX + "/")) {
			return null;
		}

		Path target = baseDir.resolve(fileUrl.substring(URL_PREFIX.length() + 1)).normalize();
		return target.startsWith(baseDir) ? target : null;
	}
}
