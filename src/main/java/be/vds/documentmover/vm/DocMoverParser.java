package be.vds.documentmover.vm;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocMoverParser {

	private String destinationFolder;
	private String dtg;
	private String sender;
	private String name;
	private String extension;
	private Pattern digitPattern = Pattern.compile("\\d*");

	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}

	public void setDtg(String dtg) {
		this.dtg = dtg;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDtg() {
		return dtg;
	}

	public String getSender() {
		return sender;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public String getDestinationFolder() {
		return destinationFolder;
	}

	public void parse(File file) {
		if (file.isDirectory()) {
			destinationFolder = file.getAbsolutePath();
		} else {
			destinationFolder =  file.getParent();
			String fileName = file.getName();

			Pattern p = Pattern.compile("(\\w*)\\s*_\\s*(\\w*)\\s*_\\s*(.*)\\s*\\.(.*)");
			Matcher m = p.matcher(fileName);
			if (m.matches()) {
				extension = m.group(4).trim();
				String group2 = m.group(2).trim();
				String group1 = m.group(1).trim();

				if (!isDigit(group1) && !isDigit(group2)) {
					name = fileName.substring(0, fileName.lastIndexOf("."));
					extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				} else if (isDigit(group1)) {
					dtg = group1;
					sender = group2;
					name = m.group(3).trim();
				} else {
					dtg = group2;
					sender = group1;
					name = m.group(3).trim();
				}
			} else {
				name = fileName.substring(0, fileName.lastIndexOf("."));
				extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
		}
	}

	private boolean isDigit(String text) {
		return digitPattern.matcher(text).matches();
	}

	public File toFile() {
		StringBuilder sb = new StringBuilder(destinationFolder);
		sb.append(File.separatorChar);
		if(null!=dtg && dtg.trim().length()>0){
			sb.append(dtg).append("_");
		}
		sb.append(sender).append("_").append(name).append(".").append(extension);
		return new File(sb.toString());
	}

}
