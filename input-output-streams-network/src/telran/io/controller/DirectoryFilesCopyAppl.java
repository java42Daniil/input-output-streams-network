package telran.io.controller;
import telran.view.*;
import telran.io.service.*;
public class DirectoryFilesCopyAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		DirectoryFilesCopy dfc = new DirectoryFilesCopyImpl();
		Menu menu = new Menu("File System Actions", DirectoryFilesCopyActions.getItems(dfc));
		menu.perform(io);

	}

}