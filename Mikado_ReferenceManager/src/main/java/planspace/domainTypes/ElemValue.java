package planspace.domainTypes;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_elemDataType;

/* Een elementaire value (ElemValue) is een class waarin een elementatire waarde kan worden vastgelegd.
 * Deze waarde hoort bij een DataPointValue. Deze DatapointValue hoort bij een DomainInstance.
 * 
 * Een elementaire waarde is een waarde die niet is samengesteld uit (andere onderdelen). 

 * 
 * Uiteraard kan deze class in toekomst uitgebreid worden met aanvullende typen zoals:
 * - Tijd
 * - Geo referentie 
 * - Postcode etc
 * - Werkdagen/ weekdagen
 * - Dagen/maanden/jaar
 * - uren/ minuten/ seconden
 
 * - etc
 * 
 * Deze class zorgt voor:
 * - Vastlegging van gegeven in de standaard JAVA datatypen, zodat alle Java functies hiervan gebruikt kunnen worden
 * - Typechecking: welke datatypen zijn compatible voor vergelijken/rekenkundige functies
 * - TypeCasting: waarden aangeleverd als "string" worden omgezet naar elementaire datatypen 
 * - Vergelijken: indien type daarvoor geschikt is is het mogelijk om de waarde te vergelijken met een andere waarde
 *    (gelijk aan, groter, groter gelijk, kleiner, kleiner gelijk) 
 * 
 * 
 * */

public class ElemValue {

	/*
	 * Indien de waarde een CURRENCY is, dan is de valuata eenheid/soort van belang
	 */
	public enum t_monetaryValue {
		EURO, DOLLAR, YEN

		// to de defined or extended in pilot
	}

	/*
	 * De waarde kan uit 1 waarde bestaan , maar in toekomst ook meervoudig van aard
	 * zijn
	 */

	boolean isMultiValue = false;

	/*
	 * Uiteindelijk worden de PlanSace waarden vertaald naar de Java datatypen,
	 * zodat de volledige toolset van waarde manipultaies en bewerking van Java te
	 * gebruiken zijn.
	 */
	int intValue;
	boolean booleanValue;
	float numberValue;
	float currencyValue;
	Date dateValue;
	Time timeValue;
	String stringValue;
	String monetaryValue;
	String memoValue;
	String filePathValue;
	String fileUrlValue;
	String valueSelectionCodeValue;

	// Het planSpace datatype wordt bij de waarde geregistreerd.
	t_elemDataType myDataType;

	public ElemValue() {
		/*
		 * Constructor. Aangezien ElemValue instances in een DataValue class worden mee
		 * opgeslagen in de MongoDB hebben we nog geen behoefte aan een UUID id voor
		 * deze instanties
		 * 
		 * Datatype is in beginsel onbekend tot nadere bekendheid
		 */
		myDataType = t_elemDataType.UNKNOWN;
	}

	public ElemValue(String aDataType, String valueAsString) {

		/*
		 * Constructor Deze methode heeft als argumenten:
		 *
		 * Een Datatype: zie voor waarde t_ElemDataType Een waarde als Java String
		 *
		 * De ontvangen waarden worden gebruikt om een nieuwe instantie van ElemValue
		 * aan te maken
		 */

		this.setValueForStringDataTypeAsString(aDataType, valueAsString);

	}

	// getters en setters van de waarde uitgedrukt in de Java waarde datattypen;
	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public float getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(float currencyValue) {
		this.currencyValue = currencyValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	// getters en setters van de meta data elementen
	public boolean isBooleanValue() {
		return booleanValue;
	}

	public boolean isMultiValue() {
		return isMultiValue;
	}

	public void setMultiValue(boolean isMultiValue) {
		this.isMultiValue = isMultiValue;
	}

	public String getMonetaryValue() {
		return monetaryValue;
	}

	public void setMonetaryValue(String monetaryValue) {
		this.monetaryValue = monetaryValue;
	}

	public String getMemoValue() {
		return memoValue;
	}

	public void setMemoValue(String memoValue) {
		this.memoValue = memoValue;
	}

	public String getFilePathValue() {
		return filePathValue;
	}

	public void setFilePathValue(String filePathValue) {
		this.filePathValue = filePathValue;
	}

	public String getFileUrlValue() {
		return fileUrlValue;
	}

	public void setFileUrlValue(String fileUrlValue) {
		this.fileUrlValue = fileUrlValue;
	}

	public t_elemDataType getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(t_elemDataType myDataType) {
		this.myDataType = myDataType;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public float getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(float numberValue) {
		this.numberValue = numberValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public Time getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(Time timeValue) {
		this.timeValue = timeValue;
	}

	// methods
	@JsonIgnore()
	public String getValueAsString() {

		/*
		 * Deze methode geeft de waarde terug geconverteerd naar een string waarde
		 * rekening houdend met het formele Javadatatype
		 */

		String valueAsString;

		valueAsString = "";

		if (myDataType.equals(t_elemDataType.INTEGERVALUE)) {
			valueAsString = String.valueOf(this.intValue);
		} else if (myDataType.equals(t_elemDataType.TRUTHVALUE)) {
			valueAsString = String.valueOf(this.booleanValue);
		} else if (myDataType.equals(t_elemDataType.NUMBER)) {
			valueAsString = String.valueOf(this.numberValue);
		} else

		if (myDataType.equals(t_elemDataType.CURRENCY_EURO)) {
			valueAsString = String.valueOf(this.currencyValue);
		} else

		if (myDataType.equals(t_elemDataType.TEXT)) {
			valueAsString = this.stringValue;
		} else

		if (myDataType.equals(t_elemDataType.FILE_PATH)) {
			valueAsString = this.filePathValue;
		} else

		if (myDataType.equals(t_elemDataType.FILE_URL)) {
			valueAsString = this.fileUrlValue;
		} else

		if (myDataType.equals(t_elemDataType.MEMO)) {
			valueAsString = this.memoValue;
		} else

		if (myDataType.equals(t_elemDataType.VALUESELECTION)) {
			valueAsString = this.valueSelectionCodeValue;
		} else

		if (myDataType.equals(t_elemDataType.DATE_EURO)) {

			String pattern = "dd/MM/yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			valueAsString = df.format(this.dateValue);
		} else

		if (myDataType.equals(t_elemDataType.UNKNOWN)) {
			valueAsString = this.stringValue;
		}

		return valueAsString;
	}

	@JsonIgnore()
	public t_elemDataType convertStingDataTypeToFormalDataType(String aDataType) {
		/*
		 * Deze methode zet het elementaire datatype (als String aangeleverd) om naar
		 * een enum code van ondersteunde datatypen (t_elemDataType);
		 */

		t_elemDataType theformalDataType;

		theformalDataType = t_elemDataType.UNKNOWN;
		if (aDataType.equals("INTEGERVALUE")) {
			theformalDataType = t_elemDataType.INTEGERVALUE;
		} else if (aDataType.equals("TRUTHVALUE")) {
			theformalDataType = t_elemDataType.TRUTHVALUE;
		} else if (aDataType.equals("NUMBER")) {
			theformalDataType = t_elemDataType.NUMBER;
		} else

		if (aDataType.equals("CURRENCY_EURO")) {
			theformalDataType = t_elemDataType.CURRENCY_EURO;
		} else

		if (aDataType.equals("TEXT")) {
			theformalDataType = t_elemDataType.TEXT;
		} else

		if (aDataType.equals("FILE_PATH")) {
			theformalDataType = t_elemDataType.FILE_PATH;
		} else

		if (aDataType.equals("FILE_URL")) {
			theformalDataType = t_elemDataType.FILE_URL;
		} else

		if (aDataType.equals("MEMO")) {
			theformalDataType = t_elemDataType.MEMO;
		} else

		if (aDataType.equals("VALUESELECTION")) {
			theformalDataType = t_elemDataType.VALUESELECTION;
		}

		return theformalDataType;

	}

	public void setValueForStringDataTypeAsString(String aDataType, String valueAsString) {
		/*
		 * Deze methode verwerkt een string waarde naar de juiste java datatype
		 * attributen afhankelijk van het datatype dat wordt aangeleverd.
		 * 
		 * Aangezien de waarde als string wordt aangeleverd, kan er onzin in staan of
		 * waarden die niet geldig zijn. Zodra dit wordt gedetecteerd, wordt de waarde
		 * wel (als string waarde) opgeslagen zodat we deze niet kwijt raken. Het
		 * DataType wordt echter op UNKNOWN gezet.
		 * 
		 * We gebruiken het Try-catch mechanisme om de conversie te maken. Deze
		 * detecteert ongeldige waarden.
		 */

		if (aDataType.equals("INTEGERVALUE")) {
			try {
				this.intValue = Integer.parseInt(valueAsString);
				myDataType = t_elemDataType.INTEGERVALUE;
			} catch (Exception e) {
				System.out.println("ERROR value is not an integer");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}

		} else if (aDataType.equals("TRUTHVALUE")) {
			boolean value_ok;

			/*
			 * Een Thruth value wordt vertaald naar boolean. Voor true worden de waarden
			 * "true", "waar" "1" geaccepteerd Voor false worden de waarden "false",
			 * "onwaar" "o" geaccepteerd De typecase (hoofd/kleine letters) worden hierbij
			 * genegeerd.
			 */
			value_ok = false;
			myDataType = t_elemDataType.TRUTHVALUE;
			if ((valueAsString.toLowerCase().equals("true")) || (valueAsString.toLowerCase().equals("waar"))
					|| (valueAsString.toLowerCase().equals("1"))) {

				this.booleanValue = true;
				value_ok = true;
			}
			if ((valueAsString.toLowerCase().equals("false")) || (valueAsString.toLowerCase().equals("onwaar"))
					|| (valueAsString.toLowerCase().equals("0"))) {

				this.booleanValue = false;
				value_ok = true;
			}

			if (value_ok == false) {
				System.out.println("ERROR value is not a truth value");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else

		if (aDataType.equals("NUMBER")) {
			try {
				this.numberValue = Float.parseFloat(valueAsString);
				myDataType = t_elemDataType.NUMBER;

			} catch (Exception e) {
				System.out.println("ERROR value is not a number");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}

		} else

		if (aDataType.equals("DATE_EURO")) {
			try {
				/*
				 * Bij een Data_EURO wordt een datum in het formaat van EURO verwacht In
				 * toekomst kunnen we hier ook andere datum formaten gaan toevoegen als input
				 */

				myDataType = t_elemDataType.DATE_EURO;

				if (valueAsString.contains("/")) {
					this.dateValue = new SimpleDateFormat("dd/MM/yyyy").parse(valueAsString);
				} else if (valueAsString.contains("-")) {
					this.dateValue = new SimpleDateFormat("dd-MM-yyyy").parse(valueAsString);
				}

			} catch (Exception e) {
				System.out.println("ERROR value is not a euro date");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}

		} else

		if (aDataType.equals("CURRENCY_EURO")) {
			try {
				/*
				 * De currency_euro doet twee dingen: het slaat de waarde op als currency en het
				 * regsitreerd deze waarde als EURO valuta soort
				 */
				this.currencyValue = Float.parseFloat(valueAsString);
				this.monetaryValue = String.valueOf(ElemValue.t_monetaryValue.EURO);
				myDataType = t_elemDataType.CURRENCY_EURO;

			} catch (Exception e) {
				System.out.println("ERROR value is not an euro currency");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else

		if (aDataType.equals("TEXT")) {

			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.TEXT;
			} catch (Exception e) {
				System.out.println("ERROR value is not an text");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else

		if (aDataType.equals("FILE_PATH")) {
			try {
				this.filePathValue = valueAsString;
				myDataType = t_elemDataType.FILE_PATH;
			} catch (Exception e) {
				System.out.println("ERROR value is not a FILE_PATH");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else if (aDataType.equals("FILE_URL")) {
			try {
				this.fileUrlValue = valueAsString;
				myDataType = t_elemDataType.FILE_URL;
			} catch (Exception e) {
				System.out.println("ERROR value is not a FILE_URL");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else

		if (aDataType.equals("MEMO")) {
			try {
				this.memoValue = valueAsString;
				myDataType = t_elemDataType.MEMO;
			} catch (Exception e) {
				System.out.println("ERROR value is not a MEMO");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else

		if (aDataType.equals("VALUESELECTION")) {
			try {
				this.valueSelectionCodeValue = valueAsString;
				myDataType = t_elemDataType.VALUESELECTION;
			} catch (Exception e) {
				System.out.println("ERROR value is not a value selection");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}
		} else if (aDataType.equals("UNKNOWN")) {

			this.stringValue = valueAsString;
			myDataType = t_elemDataType.UNKNOWN;
		} else {
			/*
			 * Mocht het datatype dat als String binnenkomt nie overeenstemmen met een
			 * PlanSpace Datatype, dan wordt de waarde wel opgeslagen, maar krijgt het per
			 * definitie het dataType onbekend.
			 */
			System.out.println("ERROR datatype " + aDataType + " is not supported");
			this.stringValue = valueAsString;
			myDataType = t_elemDataType.UNKNOWN;
		}

	}

	public void setValueForFormalDataTypeAsString(t_elemDataType theformalDataType, String valueAsString) {
		/*
		 * Deze methode verwerkt een string waarde naar de juiste java datatype
		 * attributen afhankelijk van het datatype dat wordt aangeleverd.
		 * 
		 * Is in werking identiek aan setValueForStringDataTypeAsString behalve dat het
		 * datatype een formeel datatype is uit de enumeratie t_ElemDataType
		 */

		if (theformalDataType.equals(t_elemDataType.INTEGERVALUE)) {
			try {
				this.intValue = Integer.parseInt(valueAsString);
				myDataType = t_elemDataType.INTEGERVALUE;
			} catch (Exception e) {
				System.out.println("ERROR value is not an integer");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}

		} else if (theformalDataType.equals(t_elemDataType.TRUTHVALUE)) {
			boolean value_ok;

			value_ok = false;
			myDataType = t_elemDataType.TRUTHVALUE;
			if ((valueAsString.toLowerCase().equals("true")) || (valueAsString.toLowerCase().equals("waar"))
					|| (valueAsString.toLowerCase().equals("1"))) {

				this.booleanValue = true;
				value_ok = true;
			}
			if ((valueAsString.toLowerCase().equals("false")) || (valueAsString.toLowerCase().equals("onwaar"))
					|| (valueAsString.toLowerCase().equals("0"))) {

				this.booleanValue = false;
				value_ok = true;
			}

			if (value_ok == false) {
				System.out.println("ERROR value is not a truth value");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else

		if (theformalDataType.equals(t_elemDataType.NUMBER)) {
			try {
				this.numberValue = Float.parseFloat(valueAsString);
				myDataType = t_elemDataType.NUMBER;

			} catch (Exception e) {
				System.out.println("ERROR value is not a number");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}

		} else

		if (theformalDataType.equals(t_elemDataType.DATE_EURO)) {
			try {

				myDataType = t_elemDataType.DATE_EURO;

				if (valueAsString.contains("/")) {
					this.dateValue = new SimpleDateFormat("dd/MM/yyyy").parse(valueAsString);
				} else if (valueAsString.contains("-")) {
					this.dateValue = new SimpleDateFormat("dd-MM-yyyy").parse(valueAsString);
				}

			} catch (Exception e) {
				System.out.println("ERROR value is not a euro date");
				this.setValueForStringDataTypeAsString("UNKNOWN", valueAsString);
			}

		} else

		if (theformalDataType.equals(t_elemDataType.CURRENCY_EURO)) {
			try {
				this.currencyValue = Float.parseFloat(valueAsString);
				this.monetaryValue = String.valueOf(ElemValue.t_monetaryValue.EURO);
				myDataType = t_elemDataType.CURRENCY_EURO;

			} catch (Exception e) {
				System.out.println("ERROR value is not an euro currency");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else

		if (theformalDataType.equals(t_elemDataType.TEXT)) {

			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.TEXT;
			} catch (Exception e) {
				System.out.println("ERROR value is not an text");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else

		if (theformalDataType.equals(t_elemDataType.FILE_PATH)) {
			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.FILE_PATH;
			} catch (Exception e) {
				System.out.println("ERROR value is not a FILE_PATH");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else if (theformalDataType.equals(t_elemDataType.FILE_URL)) {
			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.FILE_URL;
			} catch (Exception e) {
				System.out.println("ERROR value is not a FILE_URL");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else

		if (theformalDataType.equals(t_elemDataType.MEMO)) {
			try {
				this.memoValue = valueAsString;
				myDataType = t_elemDataType.MEMO;
			} catch (Exception e) {
				System.out.println("ERROR value is not a MEMO");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else

		if (theformalDataType.equals(t_elemDataType.VALUESELECTION)) {
			try {
				this.valueSelectionCodeValue = valueAsString;
				myDataType = t_elemDataType.VALUESELECTION;
			} catch (Exception e) {
				System.out.println("ERROR value is not a value selection");
				this.setValueForFormalDataTypeAsString(t_elemDataType.UNKNOWN, valueAsString);
			}
		} else if (theformalDataType.equals(t_elemDataType.UNKNOWN)) {

			this.stringValue = valueAsString;
			myDataType = t_elemDataType.UNKNOWN;
		} else {
			System.out.println("ERROR datatype " + theformalDataType + " is not supported");
			this.stringValue = valueAsString;
			myDataType = t_elemDataType.UNKNOWN;
		}

	}

	@JsonIgnore()
	public String toJson() {

		/*
		 * Deze methode kan in instantie van deze class omzetten naar Json m.b.v de
		 * JackSon Library (zie POM.XML)
		 */
		Gson gsonParser;
		String meAsJson;

		// Gson is de externe utility class (zie POM.XML) die Java Classes kan omzetten
		// naar Json.
		gsonParser = new Gson();

		// Ik zet mijzelf (instantie van deze class) om naar Json, en deze wordt binair
		// gemaakt om
		// in MongoDB opgeslagen te kunnen worden.
		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}

	// Compare methods

	@JsonIgnore()
	public boolean iAm_EqualToElemValue(ElemValue valueDPToCompare) {
		/*
		 * Comparing myself as value with the value specified in arguments Goal :
		 * determine if my value is equal to value in argument
		 * 
		 * result depends on: -there must be two values to compare -Types should be
		 * compatible -Value condition outcome
		 */

		boolean iAm_Equal;
		boolean twoValues;
		boolean typesCompatible;
		boolean valueConditionResult;

		// By default no success
		valueConditionResult = false;

		iAm_Equal = false;
		typesCompatible = false;

		/* Check two values */
		twoValues = this.checkTwoValuesAvailableToCompare(valueDPToCompare);

		if (twoValues) {
			/* Check compatability of elem dataTypes */
			typesCompatible = this.checkDataTypesCompatible(valueDPToCompare);
			if (typesCompatible) {
				/* Check is values are equal */

				valueConditionResult = checkEqual(valueDPToCompare);
				if (valueConditionResult) {
					iAm_Equal = true;
				}
			}
		}

		return iAm_Equal;
	}

	@JsonIgnore()
	public boolean iAm_GreaterThanElemValue(ElemValue valueDPToCompare) {
		/*
		 * Comparing myself as value with the value specified in arguments Goal :
		 * determine if my value is greater than value in argument
		 * 
		 * result depends on: -there must be two values to compare -Types should be
		 * compatible -myType is suitable for numeric comparison -Value condition
		 * outcome
		 */

		boolean iAm_Greater;
		boolean twoValues;
		boolean typesCompatible;
		boolean valueConditionResult;
		boolean numericComparable;

		// By default no success
		valueConditionResult = false;
		numericComparable = false;

		iAm_Greater = false;
		typesCompatible = false;

		/* Check two values */
		twoValues = this.checkTwoValuesAvailableToCompare(valueDPToCompare);

		if (twoValues) {
			/* Check compatability of elem dataTypes */
			typesCompatible = this.checkDataTypesCompatible(valueDPToCompare);
			if (typesCompatible) {
				numericComparable = checkNumericComparable();

				if (numericComparable) {
					/* Check my value is greater than valueDPToCompare */

					valueConditionResult = checkGreater(valueDPToCompare);
					if (valueConditionResult) {
						iAm_Greater = true;
					}
				}
			}
		}

		return iAm_Greater;
	}

	@JsonIgnore()
	public boolean iAm_LessThanElemValue(ElemValue valueDPToCompare) {
		/*
		 * Comparing myself as value with the value specified in arguments Goal :
		 * determine if my value is less than value in argument
		 * 
		 * result depends on: -there must be two values to compare -Types should be
		 * compatible -myType is suitable for numeric comparison -Value condition
		 * outcome
		 */

		boolean iAm_Less;
		boolean twoValues;
		boolean typesCompatible;
		boolean valueConditionResult;
		boolean numericComparable;

		// By default no success
		valueConditionResult = false;
		numericComparable = false;

		iAm_Less = false;
		typesCompatible = false;

		/* Check two values */
		twoValues = this.checkTwoValuesAvailableToCompare(valueDPToCompare);

		if (twoValues) {
			/* Check compatability of elem dataTypes */
			typesCompatible = this.checkDataTypesCompatible(valueDPToCompare);
			if (typesCompatible) {
				numericComparable = checkNumericComparable();

				if (numericComparable) {
					/* Check my value is greater than valueDPToCompare */

					valueConditionResult = checkLess(valueDPToCompare);
					if (valueConditionResult) {
						iAm_Less = true;
					}
				}
			}
		}

		return iAm_Less;
	}

	@JsonIgnore()
	public boolean iAm_LessOrEqualThanElemValue(ElemValue valueDPToCompare) {
		/*
		 * Comparing myself as value with the value specified in arguments Goal :
		 * determine if values are equal or less than value in argument
		 * 
		 * result depends on: -there must be two values to compare -Types should be
		 * compatible -myType is suitable for numeric comparison -Value condition
		 * outcome
		 */

		boolean iAm_LessOrEq;
		boolean twoValues;
		boolean typesCompatible;
		boolean valueConditionResult;
		boolean numericComparable;

		// By default no success
		valueConditionResult = false;
		numericComparable = false;

		iAm_LessOrEq = false;
		typesCompatible = false;

		/* Check two values */
		twoValues = this.checkTwoValuesAvailableToCompare(valueDPToCompare);

		if (twoValues) {
			/* Check compatability of elem dataTypes */
			typesCompatible = this.checkDataTypesCompatible(valueDPToCompare);
			if (typesCompatible) {
				numericComparable = checkNumericComparable();

				if (numericComparable) {
					/* Check my value is greater than valueDPToCompare */

					valueConditionResult = checkLess(valueDPToCompare);
					if (valueConditionResult) {
						iAm_LessOrEq = true;
					} else {

						valueConditionResult = checkEqual(valueDPToCompare);
						if (valueConditionResult) {
							iAm_LessOrEq = true;
						}
					}
				}
			}
		}

		return iAm_LessOrEq;
	}

	@JsonIgnore()
	public boolean iAm_GreaterOrEqualThanElemValue(ElemValue valueDPToCompare) {
		/*
		 * Comparing myself as value with the value specified in arguments Goal :
		 * determine if values are equal or greater than value in argument
		 * 
		 * result depends on: -there must be two values to compare -Types should be
		 * compatible -myType is suitable for numeric comparison -Value condition
		 * outcome
		 */

		boolean iAm_greaterOrEq;
		boolean twoValues;
		boolean typesCompatible;
		boolean valueConditionResult;
		boolean numericComparable;

		// By default no success
		valueConditionResult = false;
		numericComparable = false;

		iAm_greaterOrEq = false;
		typesCompatible = false;

		/* Check two values */
		twoValues = this.checkTwoValuesAvailableToCompare(valueDPToCompare);

		if (twoValues) {
			/* Check compatability of elem dataTypes */
			typesCompatible = this.checkDataTypesCompatible(valueDPToCompare);
			if (typesCompatible) {
				numericComparable = checkNumericComparable();

				if (numericComparable) {
					/* Check my value is greater than valueDPToCompare */

					valueConditionResult = checkGreater(valueDPToCompare);
					if (valueConditionResult) {
						iAm_greaterOrEq = true;
					} else {

						valueConditionResult = checkEqual(valueDPToCompare);
						if (valueConditionResult) {
							iAm_greaterOrEq = true;
						}
					}
				}
			}
		}

		return iAm_greaterOrEq;
	}

	private boolean checkNumericComparable() {
		/*
		 * Checks whether my dataType is suitable for numeric comparison (greater, less)
		 */

		boolean numericComparable;

		numericComparable = false; // default false

		if (this.myDataType.equals(t_elemDataType.INTEGERVALUE)) {
			numericComparable = true;
		} else if (this.myDataType.equals(t_elemDataType.NUMBER)) {
			numericComparable = true;
		} else if (this.myDataType.equals(t_elemDataType.CURRENCY_EURO)) {
			numericComparable = true;
		} else if (this.myDataType.equals(t_elemDataType.DATE_EURO)) {
			numericComparable = true;
		}

		return numericComparable;

	}

	private boolean checkTwoValuesAvailableToCompare(ElemValue valueDPToCompare) {
		boolean twoValues;

		/*
		 * Deze functie controleert of er wel twee waarden zijn om met elkaar te
		 * vergelijken Onbekende datatypen UNKNOWN worden hierbij geclassificeerd als
		 * niet bruikbaar
		 */
		twoValues = false;
		if (valueDPToCompare != null) {
			if (this.myDataType.equals(t_elemDataType.UNKNOWN) == false) {
				twoValues = true;
			}

		}

		if (twoValues == false) {
			System.out.println("Error while comparing elemValues: there are no two DPvalues to compare");

		}

		return twoValues;
	}

	private boolean checkDataTypesCompatible(ElemValue valueDPToCompare) {
		/*
		 * Check the compatibility of two DP datatype for comparision
		 * 
		 * Let op de functie staat toe dat de volgende datatypen met elkaar
		 * uitwisselbaar zijn in vergelijkingen: - gehele waarden met numbers - Texten
		 * met File_Paths/ FileURls - Currencies met integers - Currencies met numbers -
		 * teksten met memo's
		 */

		boolean compatible;

		compatible = false;
		/*
		 * if datatypes are matching then compatible by default
		 */

		if (this.getMyDataType().equals(valueDPToCompare.getMyDataType())) {
			compatible = true;
		}

		if (compatible == false) {
			/* Number compatability integers versus numbers */
			if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
					compatible = true;
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {
					compatible = true;
				}
			}

			/* Text compatible with MEMO */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.MEMO)) {
					compatible = true;
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.MEMO)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					compatible = true;
				}
			}

			/* Text compatible with FILE_URL */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.FILE_URL)) {
					compatible = true;
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.FILE_URL)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					compatible = true;
				}
			}

			/* Text compatible with FILE_PATH */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.FILE_PATH)) {
					compatible = true;
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.FILE_PATH)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					compatible = true;
				}
			}

			/* Number compatible with currency */
			if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
					compatible = true;
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {
					compatible = true;
				}
			}
		}

		if (compatible == false) {
			System.out.println("Error datatypes not compatible for comparison");
		}
		return compatible;
	}

	private boolean checkEqual(ElemValue valueDPToCompare) {
		/*
		 * Deze methode voert de daadwerkelijke vergelijk uit om vast te stellen of twee
		 * waarden gelijk zijn aan elkaar.
		 * 
		 * De functie gaat er van uit dat de waarden: Aanwezig zijn Compatible zijn qua
		 * datatypen
		 * 
		 * De Java datatype vergelijking functies worden hier gebruikt Met name voor
		 * datum waarden is dit een dingetje !
		 */
		String myValue;
		String otherValue;
		boolean eq;
		int anInt, otherInt;
		float anFloat, otherFloat;

		eq = false;

		myValue = this.getValueAsString();
		otherValue = valueDPToCompare.getValueAsString();

		/* Bij gelijke datatype is het makkelijk */
		if (this.getMyDataType().equals(valueDPToCompare.getMyDataType())) {

			if (myValue.toLowerCase().equals(otherValue.toLowerCase())) {
				eq = true;
			}
		} else

		{
			/*
			 * bij ongelijke datatypen zijn eventuele typeCasts nodig in Java /* Number
			 * comparability integers versus numbers
			 */
			if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {

					anFloat = Float.parseFloat(myValue);
					otherFloat = Float.parseFloat(otherValue);
					if (anFloat == otherFloat) {
						eq = true;
					}
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {

					anFloat = Float.parseFloat(myValue);
					otherFloat = Float.parseFloat(otherValue);
					if (anFloat == otherFloat) {
						eq = true;
					}
				}
			}

			/* Text compatible with MEMO */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.MEMO)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.MEMO)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
				}
			}

			/* Text compatible with FILE_URL */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.FILE_URL)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.FILE_URL)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
				}
			}

			/* Text compatible with FILE_PATH */
			if (this.getMyDataType().equals(t_elemDataType.TEXT)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.FILE_PATH)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.FILE_PATH)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.TEXT)) {
					if (this.getValueAsString().equals(valueDPToCompare.getValueAsString())) {
						eq = true;
					}
					;
				}
			}

			/* Number compatible with currency */
			if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
					anFloat = Float.parseFloat(myValue);
					otherFloat = Float.parseFloat(otherValue);
					if (anFloat == otherFloat) {
						eq = true;
					}
				}
			}
			if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
				if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {
					anFloat = Float.parseFloat(myValue);
					otherFloat = Float.parseFloat(otherValue);
					if (anFloat == otherFloat) {
						eq = true;
					}
				}
			}

		}

		return eq;
	}

	private boolean checkGreater(ElemValue valueDPToCompare) {
		/*
		 * Deze methode voert de daadwerkelijke vergelijk uit om vast te stellen de
		 * huidige waarde groter is dan de waarde die in de argumenten wordt
		 * aangeleverd. Met name voor datumis dit een dingetje !
		 * 
		 * De functie gaat er van uit dat de waarden: Aanwezig zijn Compatible zijn qua
		 * datatypen
		 * 
		 * De Java datatype vergelijking functies worden hier gebruikt
		 */
		String myValue;
		String otherValue;
		boolean gt;
		int myInt, otherInt;
		float myFloat, otherFloat;
		Date myDate, otherDate;

		gt = false;

		myValue = this.getValueAsString();
		otherValue = valueDPToCompare.getValueAsString();

		// both numbers
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}

		// both integers
		if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {

				myInt = Integer.parseInt(myValue);
				otherInt = Integer.parseInt(otherValue);
				if (myInt > otherInt) {
					gt = true;
				}
			}
		}

		// both currency
		if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}

		// both date
		if (this.getMyDataType().equals(t_elemDataType.DATE_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.DATE_EURO)) {

				myDate = this.getDateValue();
				otherDate = valueDPToCompare.getDateValue();

				if (myDate.after(otherDate)) {
					gt = true;
				}

			}
		}

		/* Number compatability integers versus numbers */
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}
		if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}

		/* Number compatible with currency */
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}
		if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {
				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat > otherFloat) {
					gt = true;
				}
			}
		}

		return gt;
	}

	private boolean checkLess(ElemValue valueDPToCompare) {
		/*
		 * Deze methode voert de daadwerkelijke vergelijk uit om vast te stellen de
		 * huidige waarde kleiner is dan de waarde die in de argumenten wordt
		 * aangeleverd
		 * 
		 * De functie gaat er van uit dat de waarden: Aanwezig zijn Compatible zijn qua
		 * datatypen
		 * 
		 * De Java datatype vergelijking functies worden hier gebruikt
		 */
		String myValue;
		String otherValue;
		boolean lt;
		int myInt, otherInt;
		float myFloat, otherFloat;
		Date myDate, otherDate;

		lt = false;

		myValue = this.getValueAsString();
		otherValue = valueDPToCompare.getValueAsString();

		// both numbers
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}

		// both integers
		if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {

				myInt = Integer.parseInt(myValue);
				otherInt = Integer.parseInt(otherValue);
				if (myInt < otherInt) {
					lt = true;
				}
			}
		}

		// both currency
		if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}

		// both date
		if (this.getMyDataType().equals(t_elemDataType.DATE_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.DATE_EURO)) {

				myDate = this.getDateValue();
				otherDate = valueDPToCompare.getDateValue();

				if (myDate.before(otherDate)) {
					lt = true;
				}

			}
		}

		/* Number compatability integers versus numbers */
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}
		if (this.getMyDataType().equals(t_elemDataType.INTEGERVALUE)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {

				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}

		/* Number compatible with currency */
		if (this.getMyDataType().equals(t_elemDataType.NUMBER)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}
		if (this.getMyDataType().equals(t_elemDataType.CURRENCY_EURO)) {
			if (valueDPToCompare.getMyDataType().equals(t_elemDataType.NUMBER)) {
				myFloat = Float.parseFloat(myValue);
				otherFloat = Float.parseFloat(otherValue);
				if (myFloat < otherFloat) {
					lt = true;
				}
			}
		}

		return lt;
	}

	public t_elemDataType proposeDataType(String typicalValue) {
		t_elemDataType suggestion;

		suggestion = t_elemDataType.UNKNOWN;

		if (TryValueForFormalDataTypeAsString(t_elemDataType.INTEGERVALUE, typicalValue)) {
			suggestion = t_elemDataType.INTEGERVALUE;
		} else if (TryValueForFormalDataTypeAsString(t_elemDataType.NUMBER, typicalValue)) {
			suggestion = t_elemDataType.NUMBER;
		}  else if (TryValueForFormalDataTypeAsString(t_elemDataType.CURRENCY_EURO, typicalValue)) {
			suggestion = t_elemDataType.CURRENCY_EURO;
		} else if (TryValueForFormalDataTypeAsString(t_elemDataType.TRUTHVALUE, typicalValue)) {
			suggestion = t_elemDataType.TRUTHVALUE;
		} else if (TryValueForFormalDataTypeAsString(t_elemDataType.DATE_EURO, typicalValue)) {
			suggestion = t_elemDataType.DATE_EURO;
		} else if (TryValueForFormalDataTypeAsString(t_elemDataType.TEXT, typicalValue)) {
			suggestion = t_elemDataType.TEXT;
		}

		return suggestion;
	}

	public boolean TryValueForFormalDataTypeAsString(t_elemDataType theformalDataType, String valueAsString) {
		/*
		 * Deze methode verwerkt een string waarde naar de juiste java datatype
		 * attributen afhankelijk van het datatype dat wordt aangeleverd.
		 * 
		 * Is in werking identiek aan setValueForStringDataTypeAsString behalve dat het
		 * datatype een formeel datatype is uit de enumeratie t_ElemDataType
		 */

		boolean succes;

		succes = false;

		if (theformalDataType.equals(t_elemDataType.INTEGERVALUE)) {
			try {
				this.intValue = Integer.parseInt(valueAsString);
				succes = true;
			} catch (Exception e) {
				succes = false;
			}

		} else if (theformalDataType.equals(t_elemDataType.TRUTHVALUE)) {
			boolean value_ok;

			if ((valueAsString.toLowerCase().equals("true")) || (valueAsString.toLowerCase().equals("waar"))
					|| (valueAsString.toLowerCase().equals("ja")) || (valueAsString.toLowerCase().equals("1"))) {

				succes = true;
		
			}
			if ((valueAsString.toLowerCase().equals("false")) || (valueAsString.toLowerCase().equals("onwaar"))
					|| (valueAsString.toLowerCase().equals("nee")) 		|| (valueAsString.toLowerCase().equals("onbekend")) || (valueAsString.toLowerCase().equals("0"))) {

						succes = true;
	
			}

		} else

		if (theformalDataType.equals(t_elemDataType.NUMBER)) {
			try {
				valueAsString = valueAsString.replace(",", ".");
				this.numberValue = Float.parseFloat(valueAsString);
				myDataType = t_elemDataType.NUMBER;
				succes = true;
			} catch (Exception e) {
				succes = false;
			}

		} else

		if (theformalDataType.equals(t_elemDataType.DATE_EURO)) {
			try {

				myDataType = t_elemDataType.DATE_EURO;
				
				if (valueAsString.contains("/")) {
					this.dateValue = new SimpleDateFormat("dd/MM/yyyy").parse(valueAsString);
					succes = true;

				} else if (valueAsString.contains("-")) {
					this.dateValue = new SimpleDateFormat("dd-MM-yyyy").parse(valueAsString);
					succes = true;
				} else if (valueAsString.toLowerCase().contains("jan") ||
						valueAsString.toLowerCase().contains("feb") ||
						valueAsString.toLowerCase().contains("mrt") ||
						valueAsString.toLowerCase().contains("maart") ||
						valueAsString.toLowerCase().contains("apr")||
						valueAsString.toLowerCase().contains("mei")||
						valueAsString.toLowerCase().contains("jun")||
						valueAsString.toLowerCase().contains("jul")||
						valueAsString.toLowerCase().contains("aug")||
						valueAsString.toLowerCase().contains("sep")||
						valueAsString.toLowerCase().contains("okt")||
						valueAsString.toLowerCase().contains("nov")||
						valueAsString.toLowerCase().contains("dec")		) {
					succes = true;
				}
				
			} catch (Exception e) {
				succes = false;
			}

		} else

		if (theformalDataType.equals(t_elemDataType.CURRENCY_EURO)) {
			try {
				if (valueAsString.toLowerCase().contains("eur") 
						) {
					
			
				myDataType = t_elemDataType.CURRENCY_EURO;
				succes = true;
				}
			} catch (Exception e) {
				succes = false;
			}
		} else

		if (theformalDataType.equals(t_elemDataType.TEXT)) {

			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.TEXT;
				succes = true;
			} catch (Exception e) {
				succes = false;
			}
		} else

		if (theformalDataType.equals(t_elemDataType.FILE_PATH)) {
			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.FILE_PATH;
				succes = true;
			} catch (Exception e) {
				succes = false;
			}
		} else if (theformalDataType.equals(t_elemDataType.FILE_URL)) {
			try {
				this.stringValue = valueAsString;
				myDataType = t_elemDataType.FILE_URL;
				succes = true;
			} catch (Exception e) {
				succes = false;
			}
		} else

		if (theformalDataType.equals(t_elemDataType.MEMO)) {
			try {
				this.memoValue = valueAsString;
				myDataType = t_elemDataType.MEMO;
				succes = true;
			} catch (Exception e) {

			}
		} else

		if (theformalDataType.equals(t_elemDataType.VALUESELECTION)) {
			try {
				this.valueSelectionCodeValue = valueAsString;
				myDataType = t_elemDataType.VALUESELECTION;
				succes = true;
			} catch (Exception e) {
				succes = false;
			}
		}
		return succes;
	}

}
