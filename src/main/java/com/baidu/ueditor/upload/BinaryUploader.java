package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader {

	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;

			try {
				if (isAjaxUpload) {
					mr.setCharacterEncoding("UTF-8");
				}

				if (mr.getMultiFileMap().size() <= 0) {
					return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
				}
				Iterator<MultipartFile> iterator = mr.getFileMap().values().iterator();
				while (iterator.hasNext()) {
					MultipartFile file = iterator.next();
					String savePath = (String) conf.get("savePath");
					String originFileName = file.getOriginalFilename();
					String suffix = FileType.getSuffixByFilename(originFileName);

					originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
					savePath = savePath + suffix;

					long maxSize = ((Long) conf.get("maxSize")).longValue();

					if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
						return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
					}

					savePath = PathFormat.parse(savePath, originFileName);

					String physicalPath = (String) conf.get("rootPath") + savePath;

					InputStream is = file.getInputStream();
					State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
					is.close();

					if (storageState.isSuccess()) {
						storageState.putInfo("url", PathFormat.format(savePath));
						storageState.putInfo("type", suffix);
						storageState.putInfo("original", originFileName + suffix);
					}

					return storageState;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
