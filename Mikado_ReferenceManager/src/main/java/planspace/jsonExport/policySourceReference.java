package planspace.jsonExport;

import planspace.compliance.SourceReferenceData;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_annotationType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_sourceType;

public class policySourceReference {
	private String _id;
	private t_sourceType sourceType;
	private t_annotationType annotationType;
	private String shortDescription;
	private t_chefType markedAsPolicyType;
	private String markedContent;
	private String externalID;
	private String documentName;
	private String fullPathName;
	private String URL;
	private boolean isTextMarkup;
	private int pageNumber;

	public policySourceReference() {

	}

	public void initBySourceReferenceData(SourceReferenceData aRef) {
		this._id = aRef.get_id();
		this.sourceType = aRef.getSourceType();
		this.annotationType = aRef.getMyAnnotationType();
		this.shortDescription = aRef.getInformalName();
		this.markedAsPolicyType = aRef.getChefType();
		this.markedContent = aRef.getMarkedContent();
		this.externalID = aRef.getExternalID();
		this.documentName = this.parseDocumentName(aRef.getFullPathName());
		this.fullPathName = aRef.getFullPathName();
		this.URL = aRef.getFullPathNameURL();
		this.isTextMarkup = aRef.isTextMarkup();
		this.pageNumber = aRef.getPageNumber();

	}

	private String parseDocumentName(String fullPathName) {
		int p;
		String docName;

		docName = "";
		if (fullPathName != null) {

			p = fullPathName.lastIndexOf("\\");
			if (p > 0) {
				docName = fullPathName.substring(p + 1); // skip the last backslash
			}
		}
		return docName;
	}

}
