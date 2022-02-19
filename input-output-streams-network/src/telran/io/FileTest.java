package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Arrays;

class FileTest {
	File nodeFile = new File("file.txt");
	File nodeDir = new File("dir1/dir2");
	File printStream = new File("printStream.txt");
	File printWriter = new File("printWriter.txt");
	File personStream = new File("person.data");
	String text = "HELLO";
	@BeforeEach
	void setUp() throws Exception {
		nodeFile.delete();
		nodeDir.delete();
		printStream.delete();
		printWriter.delete();
		personStream.delete();
	}

	@Test
	void testInitial() throws IOException {
		
		assertFalse(nodeFile.exists());
		assertFalse(nodeDir.exists());
		nodeFile.createNewFile();
		nodeDir.mkdirs();
		assertTrue(nodeFile.exists());
		assertTrue(nodeDir.exists());
		assertTrue(nodeFile.isFile());
		assertTrue(nodeDir.isDirectory());
		File nodeFile1 = new File("dir1/file1.txt");
		nodeFile1.createNewFile();
		File nodeDir1 = new File("dir1");
		System.out.println(nodeDir1.getName());
		Arrays.stream(nodeDir1.listFiles()).forEach(n -> System.out.printf("  %s: %s\n"
				,n.getName(), n.isDirectory() ? "dir" : "file"));
//		dir1
//		  dir2: dir
//		  file1.txt: file
		
		
		
	}
	@Test
	void copyTest() throws IOException {
		InputStream is = new FileInputStream("srcFile.txt");
		File destFile = new File("destFile.txt");
		System.out.printf("file %s exists : %s\n", destFile.getName(), destFile.exists());
		OutputStream os = new FileOutputStream(destFile);
		byte[] buffer = new byte[is.available()]; //works only for small files
		System.out.printf("read from input stream returns: %d\n", is.read(buffer));
		os.write(buffer);
		is.close();
		os.close();
	}
	@Test
	void testPrintStream() throws Exception {
		PrintStream stream = new PrintStream(printStream);
		stream.println(text);
		BufferedReader reader = new BufferedReader(new FileReader(printStream));
		assertEquals(text, reader.readLine());
		stream.close();
		reader.close();
		
		
	}
	@Test
	void testPrintWriter() throws Exception {
		PrintWriter stream = new PrintWriter(printWriter);
		stream.println(text);
		stream.flush();
		BufferedReader reader = new BufferedReader(new FileReader(printWriter));
		assertEquals(text, reader.readLine());
		stream.close();
		reader.close();
		
		
	}
	@Test
	void objectOrientedStreamTest() throws Exception{
		Person person = new Person(123, "Vasya", null);
		person.setAnotherPerson(person);
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(personStream));
		output.writeObject(person);
		output.close();
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(personStream));
		Person anotherPerson = (Person) input.readObject();
		assertEquals(person, anotherPerson);
		input.close();
		
	}
	
	

}