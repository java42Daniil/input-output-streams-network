package telran.io.service;

import telran.view.InputOutput;

public interface DirectoryFilesCopy {
/**
 * 
 * @param directoryPath
 * @param maxDepth (if maxDepth < 0 , no depth limitation)
 * @param io - InputOutput
 * Example for max depth 4
 * Dir. name
 *    name1 dir
 *       name11 file
 *       name12 dir
 *          name121 file
 *       name13 file
 *       name14 dir
 *           name141 dir
 *             name1411 file
 *             name1412 dir
 *       name15 file
 *    name2 file
 */
	void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io);
	/**
	 * copies file from pathFileSrc to pathFileDest
	 * @param pathFileSrc
	 * @param pathFileDest
	 * @param flOverwrite - if true an existing destination will be overwritten,
	 *  otherwise operation will be denied 
	 * @return byte rate - number of bytes in one millisecond
	 */
	long copyFiles(String pathFileSrc, String pathFileDest, boolean flOverwrite);
}