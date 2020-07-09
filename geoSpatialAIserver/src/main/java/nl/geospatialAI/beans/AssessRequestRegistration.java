package nl.geospatialAI.beans;



import java.util.ArrayList;
import java.util.List;

public class AssessRequestRegistration {

    private List<AssessRequest> assessRequestRecords;
    private static AssessRequestRegistration stdregd = null;

    private AssessRequestRegistration(){

    	assessRequestRecords = new ArrayList<AssessRequest>();

    }

    public static AssessRequestRegistration getInstance() {

        if(stdregd == null) {

              stdregd = new AssessRequestRegistration();

              return stdregd;

            }

            else {

                return stdregd;

            }

    }

    public void add(AssessRequest reqRequest) {

    	 
    	assessRequestRecords.add(reqRequest);

    }
}

