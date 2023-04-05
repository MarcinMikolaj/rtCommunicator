package project.rtc.infrastructure.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
	
	public static String createSingleFolder(String pathToFolder) {
		
		Path path = Paths.get(pathToFolder);
		
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				System.out.print(e.getMessage());
			}
		}
		
		return pathToFolder;
		
	}
	
	
	public static void saveFileInDirectory(String filePath, String string) {
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(string);
			fileOutputStream.close();
			
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public static String deserializeObjectAndGetFromDirectory(String imgPath){

        String imgInBase64 = null;

        try{
        	ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(imgPath));
            imgInBase64 = (String) inputStream.readObject();
            inputStream.close();
        }catch(Exception e){
            System.out.println(ConsoleColors.RED + "FileUtils.deserializeObjectAndGetFromDirectory:" + e.getMessage() + ConsoleColors.RESET);
        }
        return imgInBase64;
    }

}
