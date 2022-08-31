package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.nio.file.*;


public class MidtermFile {

	final static String dirPath = "C:\\Users\\User\\Desktop\\TA\\finalterm";
	static List<String> student; 
	static List<String> fileLst = new ArrayList<String>();

	public static void main(String[] args) throws IOException{

		StringBuilder sb = new StringBuilder();

		inputName();
		scanDir(dirPath.toString(), fileLst);

		for(String fullPath : fileLst) {		
			
			//이름만 자르기
			sb.append(fullPath);
			sb.delete(0, 41);
			String name = sb.substring(0, 3);
			
			File sourceF = new File(fullPath);
			File targetF = new File(makedirs(name)+"\\"+fileName(sb));
			
			if (!Files.exists(targetF.toPath().getParent())) {
	            Files.createDirectories(targetF.toPath().getParent());
	        }
			
			try {
				Files.copy(sourceF.toPath(), targetF.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			catch(Exception e) {
				System.out.println(name+"의 파일이 복사가 진행되지 않았습니다.");
			}
			
			System.out.println(sourceF.toString() +"\n=>>  "+targetF.toString()+"\n");
			
			sb = new StringBuilder();	
		}

	}

	/**
	 * folderPath로 부터 재귀함수 호출로 .java 파일을 fileLst에 경로들을 담음.
	 * @param folderPath 초기 경로
	 * @param fileLst	 파일 경로를 저장한 List<String>
	 */
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

	/**
	 * 파일의 이름을 잘라내는 메소드
	 * @param sb
	 * @return 파일 이름
	 */
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

	/**
	 * 입력받은 경로에 학생 파일과 같은 이름이 있다면 그 경로로 학생이름으로 폴더를 만들고 경로를 반환
	 * @param name 이름
	 * @param week 주차
	 * @return 경로
	 */
	public static String makedirs(String name)  {

		StringBuilder dirNamePath = new StringBuilder();
		dirNamePath.append(dirPath+"\\src");

		for(int i = 0; i < student.size(); i++) {

			if(name.contains(student.get(i))) {
				dirNamePath.append("\\"+student.get(i)+"1");
				File file = new File(dirNamePath.toString());
				file.mkdirs();
				break;
			}
		}

		return dirNamePath.toString()+"\\";
	}

//	public static void copyFile(String sourcePath, String targetPath) {
//
//
//		for(String fullPath : fileLst) {		
//
//			File sourceF = new File(fullPath);
//			File targetF = new File(makedirs());
//		}
//
//	}

	public static void inputName() throws FileNotFoundException{

		student = new ArrayList<>();

		Scanner input = new Scanner(new File("names.txt"));

		while(input.hasNext()) {
			student.add(input.next());
		}

	}

}
