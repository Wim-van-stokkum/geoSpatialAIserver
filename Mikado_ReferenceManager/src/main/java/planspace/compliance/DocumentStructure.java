package planspace.compliance;

import java.util.HashMap;

public class DocumentStructure {
	private String documentName;
	private String title;

	private int sectionCount;
	private String lastSectionName;
	private int articleCount;

	private String lastArticleName;
	private int lidCount;
	private String lastLidName;

	private int h1;
	private String lastH1name;

	private int h2;
	private String lastH2name;

	private int h3;
	private String lastH3name;

	private int h4;
	private String lastH4name;

	private int h5;
	private String lastH5name;

	private int h6;
	private String lastH6name;
	
	private HashMap<Integer, Integer> headCountForLevel;
	private HashMap<Integer, String> headNameForLevel;
	private int currentLevel;

	public DocumentStructure() {
		Init();
		resetAll();

	}

	public DocumentStructure(String docName) {
		documentName = docName;
		Init();
		resetAll();

	}

	public void Init() {
		documentName = "";
		headCountForLevel = new HashMap<Integer, Integer>();
		headNameForLevel = new HashMap<Integer, String>();
		currentLevel = 0;
	}
	private void resetAll() {

		this.resetHeaders(1);
		this.resetArticle();
		this.resetLid();
		this.resetSection();

	}

	private void resetSection() {
		this.sectionCount = 0;
		this.lastSectionName = "";

	}

	private void resetLid() {
		this.lidCount = 0;
		this.lastLidName = "";

	}

	private void resetArticle() {
		this.lastArticleName = "";
		this.articleCount = 0;

	}

	private void resetHeaders(int level) {
		if (level == 6) {
			this.lastH6name = "";
			this.h6 = 0;
		} else if (level == 5) {
			this.lastH5name = "";
			this.h5 = 0;
			resetHeaders(6);
		} else if (level == 4) {
			this.lastH4name = "";
			this.h4 = 0;
			resetHeaders(5);
		} else if (level == 3) {
			this.lastH3name = "";
			this.h3 = 0;
			resetHeaders(2);
		} else if (level == 2) {
			this.lastH2name = "";
			this.h2 = 0;
			resetHeaders(1);
		} else if (level <= 1) {
			this.lastH1name = "";
			this.h1 = 0;
			resetHeaders(0);
		}

	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void startHeader(String name, int level) {
		if (level == 6) {
			this.lastH6name = name;
			this.h6 ++;
		} else if (level == 5) {
			this.lastH5name = name;
			this.h5++;;
			resetHeaders(6);
		} else if (level == 4) {
			this.lastH4name = name;
			this.h4++;
			resetHeaders(5);
		} else if (level == 3) {
			this.lastH3name = name;
			this.h3++;
			resetHeaders(2);
		} else if (level == 2) {
			this.lastH2name = name;
			this.h2 ++;
			resetHeaders(1);
		} else if (level <= 1) {
			this.lastH1name = name;
			this.h1++;
			resetHeaders(0);
		}

	}

	
	

}
