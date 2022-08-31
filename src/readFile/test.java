package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws FileNotFoundException{
		
		ArrayList<String> student = new ArrayList<String>();
		
		Scanner input = new Scanner(new File("names.txt"));
		
		while(input.hasNext()) {
			student.add(input.next());
		}
		
		System.out.println(student);
	}

}
