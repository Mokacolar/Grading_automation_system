package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.nio.file.*;

public class SortingFile_ver2 {

	final static String dirPath = "C:\\Users\\User\\Desktop\\TA\\finalterm";
	static List<String> student; 

	public static void main(String[] args) throws IOException{
		
		Scanner input = new Scanner(System.in);
		System.out.print("몇 주차 : ");
		int week = input.nextInt();
		
		StringBuilder sb = new StringBuilder();	
		
		//경로가 담기는 List
		List<String> fileLst = new ArrayList<String> ();
		
		//학생들 이름 넣기
		inputName();

		//grading 폴더 안에 있는 모든 .java 파일의 경로를  fileLst에 담음.
		scanDir(dirPath.toString()+"\\temp", fileLst);

		for(String fullPath : fileLst) {		

			sb.append(fullPath);
			
			//파일 이름 구하기. (p1, p2)
			String fileName = fileName(sb);

			File sourceF = new File(fullPath);
			File targetF = new File(makedirs(searchStudentName(sb.toString()), week)+fileName);

			copyFile(sourceF, targetF);
		
			sb = new StringBuilder();
		}

	}

	public static void scanDir(String folderPath, List<String> fileLst) {

		File[] files = new File(folderPath).listFiles(); 

		for(File f : files) { 

			if(f.isDirectory()) {

				scanDir(f.getAbsolutePath(), fileLst); 

			} else if(f.getName().endsWith(".java") && !f.getName().contains("info")) { 

				fileLst.add(f.getAbsolutePath());
			} 
		}
	}

	public static String fileName(StringBuilder sb) {

		Stack<Integer> pivot = new Stack<>(); 

		for(int i = 0; i < sb.length(); i++) {
			if(sb.toString().charAt(i) == '\\') {
				pivot.push(i);
			}
		}

		String temp = sb.substring(sb.length()-4, sb.length());
		temp = sb.substring(pivot.pop()+1, sb.length());

		String result = temp;

		return result;
	}

	public static String makedirs(String name, int week)  {

		StringBuilder dirNamePath = new StringBuilder();
		dirNamePath.append(dirPath);

		dirNamePath.append("\\"+name);
		File file = new File(dirNamePath.toString());
		file.mkdirs();

		return dirNamePath.toString()+"\\";
	}

	public static void inputName() throws FileNotFoundException{

		student = new ArrayList<>();

		Scanner input = new Scanner(new File("names.txt"));

		while(input.hasNext()) {
			student.add(input.next());
		}

	}

	public static void copyFile(File sourceFile, File targetFile) throws IOException {

		if (!Files.exists(targetFile.toPath().getParent())) {
			Files.createDirectories(targetFile.toPath().getParent());
		}

		try {
			Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch(Exception e) {
			System.out.println(targetFile.toPath()+"의 파일이 복사가 진행되지 않았습니다.");
		}
		
		System.out.println(sourceFile.toString() +"\n=>>  "+targetFile.toString()+"\n");

	}

	public static String searchStudentName(String path) {
		String name = "noname";
		
		for(int i = 0; i < student.size(); i++) {
			if(path.contains(student.get(i))) {
				name = student.get(i);
				return name;
			}
		}
		return name;
	}

}
