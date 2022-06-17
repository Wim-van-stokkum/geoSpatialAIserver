package planspace.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import planspace.utils.MikadoSettings;

public class JsonExportFile {
	private File jsonFile;
	private String fileName;
	private String pathRoot;
	private FileOutputStream myOutputStream;
	private OutputStreamWriter myOsWriter;
	private BufferedWriter myWriter;

	public JsonExportFile(String name) {
		this.init();
		this.fileName = name;
		createTextFile();
	}

	public JsonExportFile() {
		this.init();
		createTextFile();

	}

	private void init() {
		// pathRoot = "C:\\mikado_exports\\";
		pathRoot = MikadoSettings.getInstance().getPathToJSONexport();
	}

	public String getFullPath() {
		String s;
		s = this.pathRoot + "\\" + this.fileName;
		return s;
	}

	public boolean createTextFile() {
		boolean succes;
		succes = false;

		try {
			jsonFile = new File(this.getFullPath());
			if (jsonFile.createNewFile()) {
				System.out.println("File created: " + this.getFullPath());

				this.myOutputStream = new FileOutputStream(jsonFile);
				this.myOsWriter = new OutputStreamWriter(myOutputStream);
				this.myWriter = new BufferedWriter(myOsWriter);

			} else {
				System.out.println("File already exists." + this.getFullPath());
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return succes;
	}

	public void write(String s) {
		try {
			if (this.jsonFile != null) {
				if (this.myWriter != null) {
					this.myOsWriter.write(s);
				}

			}
		} catch (IOException e) {
			System.out.println("An error occurred while writing to " + this.getFullPath());
			e.printStackTrace();
		}
	}

	public void writeln(String s) {
		this.write(s);
		this.write("\n");

	}

	public boolean closeTextFile() {
		boolean succes;
		succes = false;

		try {
			if (this.jsonFile != null) {
				this.myOsWriter.close();

			}
		} catch (IOException e) {
			System.out.println("An error occurred while closing " + this.getFullPath());
			e.printStackTrace();
		}
		return succes;
	}

	

}