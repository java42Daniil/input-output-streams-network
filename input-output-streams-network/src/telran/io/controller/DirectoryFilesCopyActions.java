package telran.io.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import telran.io.service.DirectoryFilesCopy;
import telran.view.*;

public class DirectoryFilesCopyActions {
static private DirectoryFilesCopy dfc;
static private ArrayList<Item> items;
	public static ArrayList<Item> getItems(DirectoryFilesCopy dfc) {
		
		if (items == null) {
			DirectoryFilesCopyActions.dfc = dfc;
			items = new ArrayList<>(Arrays
					.asList(new Item[] { 
							Item.of("Display directory content", DirectoryFilesCopyActions::displayDir),
							Item.of("Copy files", DirectoryFilesCopyActions::copyFiles), 
							Item.exit()}));
		}
		return items;
	}
	static void displayDir(InputOutput io) {
		String path = io.readString("Enter directory path");
		int maxDepth = io.readInt("Emter maximal depth of the content");
		dfc.displayDirectoryContent(path, maxDepth, io);
	}
	static void copyFiles(InputOutput io) {
		String pathSrc = io.readString("Enter path of source file");
		String pathDest = io.readString("Enter path of destination file");
		String yesNo[] = {"YES", "NO"};
		String canOverwrite = io.readStringOption("Can be overwritten (YES/NO)", 
				new HashSet<String>(Arrays.asList(yesNo)));
		long byteRate = dfc.copyFiles(pathSrc, pathDest, canOverwrite.equals("YES"));
		io.writeObjectLine(String.format("byte-rate is %d bytes/millisecond", byteRate));
		
	}
}