package telran.io.service;

import telran.view.InputOutput;

import static org.junit.Assert.assertThrows;

import java.io.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class DirectoryFilesCopyImpl implements DirectoryFilesCopy {

	private static final int DEFAULT_SPACES_PER_LEVEL = 2;
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
	private int spacesPerLevel = DEFAULT_SPACES_PER_LEVEL;
	private int bufferSize = DEFAULT_BUFFER_SIZE;

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getSpacesPerLevel() {
		return spacesPerLevel;
	}

	public void setSpacesPerLevel(int spacesPerLevel) {
		this.spacesPerLevel = spacesPerLevel;
	}

	@Override
	public void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io) {
		File directory = new File(directoryPath);
		if (!directory.exists() || !directory.isDirectory()) {
			throw new RuntimeException(String.format("%s is not directory", directoryPath));
		}

		try {
			io.writeObjectLine("Content of directory " + directory.getAbsoluteFile().getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		Arrays.asList(directory.listFiles())
				.forEach(n -> displayDirectoryContent(n, maxDepth < 0 ? Integer.MAX_VALUE : maxDepth, io, 1));

	}

	private void displayDirectoryContent(File node, int maxDepth, InputOutput io, int level) {
		if (!node.exists()) {
			throw new RuntimeException(String.format("directory %s not found\n", node.getName()));
		}
		if (level <= maxDepth) {
			boolean flDir = node.isDirectory();

			try {
				String name = node.getName();
				io.writeObjectLine(String.format("%s%s  %s", getIndent(level),
						name.isEmpty() ? "no permission to read" : name, flDir ? "dir" : "file"));
				if (flDir) {
					Arrays.stream(node.listFiles()).forEach(n -> displayDirectoryContent(n, maxDepth, io, level + 1));
				}
			} catch (Exception e) {

			}
		}

	}

	private String getIndent(int level) {

		return " ".repeat(level * spacesPerLevel);
	}

	@Override
	public long copyFiles(String pathFileSrc, String pathFileDest, boolean flOverwrite) {
		if (pathFileSrc.equals(pathFileDest)) {
			throw new RuntimeException("source can not be the same as destination");
		}
		File fileSrc = new File(pathFileSrc);
		checkSrcFile(pathFileSrc, fileSrc);
		File fileDest = new File(pathFileDest);
		checkDestFile(pathFileDest, flOverwrite, fileDest);
		try {
			return copyFiles(fileSrc, fileDest);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private long copyFiles(File fileSrc, File fileDest) throws Exception {
		byte[] buffer = new byte[bufferSize];
		long totalBytesCount = 0;

		try (InputStream is = new FileInputStream(fileSrc); OutputStream os = new FileOutputStream(fileDest)) {
			Instant start = Instant.now();
			int bytesCount = 0;
			while ((bytesCount = is.read(buffer)) > 0) {
				os.write(buffer, 0, bytesCount);
				totalBytesCount += bytesCount;

			}
			long runningTime = ChronoUnit.MILLIS.between(start, Instant.now());
			if (runningTime <= 2) {
				String srcName = fileSrc.getName();
				throw new RuntimeException(
						String.format("Copying from %s to %s succeded but %s too small " + "for byte-rate computing",
								srcName, fileDest.getName(), srcName));
			}

			return totalBytesCount / runningTime;

		}
	}

	private void checkDestFile(String pathFileDest, boolean flOverwrite, File fileDest) {
		if (fileDest.exists() && !flOverwrite) {
			throw new RuntimeException(String.format("file %s can not be overwritten", pathFileDest));
		}
		if (!fileDest.exists()) {
			try {
				fileDest.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(String.format("file %s can not be created", fileDest));
			}
		}
		if (!fileDest.canWrite()) {
			throw new RuntimeException(String.format("file %s doesn't have permission for writing", fileDest));
		}
	}

	private void checkSrcFile(String pathFileSrc, File fileSrc) {
		if (!fileSrc.exists()) {
			throw new RuntimeException(String.format("file %s doesn't exist", pathFileSrc));
		}
		if (!fileSrc.isFile()) {
			throw new RuntimeException(String.format(" %s is not file", pathFileSrc));
		}
		if (!fileSrc.canRead()) {
			throw new RuntimeException(String.format("file %s can not be read", pathFileSrc));
		}
	}

}