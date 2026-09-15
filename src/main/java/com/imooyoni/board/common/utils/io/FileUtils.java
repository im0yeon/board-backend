package com.imooyoni.board.common.utils.io;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.imooyoni.board.common.BaseException;
import com.imooyoni.board.common.utils.lang.StringUtils;
import com.imooyoni.board.config.exception.ErrorCode;

public final class FileUtils {

	public static final long MAX_ATTACHMENT_SIZE = 10L * 1024 * 1024;

	public static final Set<String> IMAGE_EXTENSIONS =
			Set.of("jpg", "jpeg", "png", "gif", "webp", "bmp");

	public static final Set<String> DOCUMENT_EXTENSIONS =
			Set.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "hwp", "hwpx", "txt", "csv", "zip");

	public static final Set<String> ATTACHMENT_EXTENSIONS =
			Set.copyOf(union(IMAGE_EXTENSIONS, DOCUMENT_EXTENSIONS));

	private static final char EXTENSION_SEPARATOR = '.';
	private static final String[] SIZE_UNITS = { "B", "KB", "MB", "GB" };

	private FileUtils() {
	}

	public static boolean isEmpty(MultipartFile file) {
		return file == null || file.isEmpty();
	}

	// 경로 구분자 제거 - 디렉터리 탈출 방지
	public static String sanitize(String filename) {
		if (StringUtils.isBlank(filename)) {
			return "";
		}

		String name = filename.strip().replace('\\', '/');
		name = name.substring(name.lastIndexOf('/') + 1);

		return name.equals(".") || name.equals("..") ? "" : name;
	}

	public static String extensionOf(String filename) {
		String name = sanitize(filename);
		int dot = name.lastIndexOf(EXTENSION_SEPARATOR);

		if (dot < 1 || dot == name.length() - 1) {
			return "";
		}
		return name.substring(dot + 1).toLowerCase(Locale.ROOT);
	}

	public static String baseNameOf(String filename) {
		String name = sanitize(filename);
		int dot = name.lastIndexOf(EXTENSION_SEPARATOR);

		return dot < 1 ? name : name.substring(0, dot);
	}

	// 저장용 파일명 - UUID + 원본 확장자
	public static String toStoredName(String originalFilename) {
		String extension = extensionOf(originalFilename);
		String name = UUID.randomUUID().toString().replace("-", "");

		return extension.isEmpty() ? name : name + EXTENSION_SEPARATOR + extension;
	}

	public static boolean hasAllowedExtension(String filename, Set<String> allowedExtensions) {
		return allowedExtensions.contains(extensionOf(filename));
	}

	public static void verify(MultipartFile file) {
		verify(file, MAX_ATTACHMENT_SIZE, ATTACHMENT_EXTENSIONS);
	}

	public static void verify(MultipartFile file, long maxBytes, Set<String> allowedExtensions) {
		if (isEmpty(file)) {
			throw BaseException.of(ErrorCode.INVALID_INPUT);
		}

		if (file.getSize() > maxBytes) {
			throw BaseException.of(ErrorCode.FILE_TOO_LARGE, null, formatSize(maxBytes));
		}

		if (!hasAllowedExtension(file.getOriginalFilename(), allowedExtensions)) {
			throw BaseException.of(ErrorCode.UNSUPPORTED_FILE_TYPE, null,
					String.join(", ", new TreeSet<>(allowedExtensions)));
		}
	}

	public static String formatSize(long bytes) {
		double size = bytes;
		int unit = 0;

		while (size >= 1024 && unit < SIZE_UNITS.length - 1) {
			size /= 1024;
			unit++;
		}

		return size == Math.floor(size)
				? String.format("%d%s", (long) size, SIZE_UNITS[unit])
				: String.format("%.1f%s", size, SIZE_UNITS[unit]);
	}

	private static Set<String> union(Set<String> left, Set<String> right) {
		Set<String> merged = new TreeSet<>(left);
		merged.addAll(right);
		return merged;
	}
}
