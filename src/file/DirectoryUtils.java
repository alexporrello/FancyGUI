package file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class DirectoryUtils {

	/**
	 * Deletes a given file from a directory.
	 * @param file is the file to be deleted.
	 * @return true if the file exists after attempted deletion; else, false.
	 */
	public static boolean deleteDirectory(File directory) {
		if(directory.exists()) {
			return delete(directory);
		} else {
			return false;
		}
	}

	public static boolean delete(File file) {
		if(file.isDirectory()) {
			Boolean toReturn = true;

			if(file.list().length == 0) {
				toReturn = file.delete();
			} else {
				File files[] = file.listFiles();

				for (File temp : files) {
					if(temp.isDirectory()) {
						delete(temp);
					} else {
						try {
							Files.delete(temp.toPath());
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}
				}

				if(file.listFiles().length == 0) {
					toReturn = file.delete();
				}
			}

			return toReturn;
		} else {
			return file.delete();
		}
	}


	public static void openInWindowsExplorer(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException f) {
			f.printStackTrace();
		}
	}

	public static void copyDirectory(File localDirectory, File remoteDirectory) throws IOException {

		ArrayList<File> tree = new ArrayList<File>();
		makeDirectoryTree(localDirectory, tree);

		for(File file : tree) {						
			String localDirectoryPath = localDirectory.getAbsolutePath().substring(0, 
					localDirectory.getAbsolutePath().lastIndexOf("\\"));
			String localfilePath      = file.getAbsolutePath();
			String remoteFilePath     = remoteDirectory.getAbsolutePath() + 
					localfilePath.replace(localDirectoryPath, "");

			if(!new File(remoteFilePath).getParentFile().exists()) {
				new File(remoteFilePath).getParentFile().mkdir();
			}
			
			Files.copy(new File(localfilePath).toPath(), 
					new File(remoteFilePath).toPath(), 
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private static void makeDirectoryTree(File directory, ArrayList<File> files) {
		for (File file : directory.listFiles()) {			
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				makeDirectoryTree(file, files);
			}
		}
	}

}
