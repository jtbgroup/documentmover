package be.vds.documentmover.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class FileUtils {

public static void copyFileJava7(File sourceFile, File destFile) throws IOException{
	Files.move(sourceFile.toPath(), destFile.toPath());
}

	public static void moveFile(File sourceFile, File destFile) throws IOException{
		if(!destFile.getParentFile().exists()){
			destFile.getParentFile().mkdirs();
		}
		Files.move(sourceFile.toPath(), destFile.toPath());
	}

	public static String getExtension(File file) {
		String path = file.getName();
		int idx = path.lastIndexOf(".");
		return path.substring(idx+1);
	}

	public static void deleteFile(File file) {
		file.delete();
	}
	
}
