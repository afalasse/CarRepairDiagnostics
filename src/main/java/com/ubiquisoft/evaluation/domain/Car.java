package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */
		// Count parts present in the car by type.
		HashMap<PartType, Integer> presentPartsMap = new HashMap<>() ;

		if( null != this.parts )
		{
			for( Part part: this.parts )
			{
				PartType	partType    = part.getType() ;
				Integer		nbParts		= presentPartsMap.get( partType ) ;

				if( null == nbParts )
				{
					nbParts = 0 ;
				}

				presentPartsMap.put( partType, nbParts + 1 ) ;
			}
		}

		HashMap<PartType, Integer> missingPartsMap = new HashMap<>() ;

		// For each part type, verify that the car has the correct number of them.
		for( PartType partType: PartType.values() )
		{
			Integer nbParts = presentPartsMap.get( partType ) ;

			if( null == nbParts )
			{
				nbParts = 0 ;
			}

			int wantedNbParts = PartType.TIRE == partType ? 4 : 1 ;

			if( nbParts < wantedNbParts )
			{
				missingPartsMap.put( partType, nbParts ) ;
			}
			else if( nbParts > wantedNbParts )
			{
				// Car not well initialized, note that there are no "missing" parts of that
				// type if they are in excess, so the nethod contract is actually fullfilled.
				System.err.println( String.format( "Too many parts of type '%s' specified for '%s': %d."
												 , partType
												 , this
												 , nbParts ) ) ;
			}
		}

		return missingPartsMap ;
	}

	@Override
	public String toString() {
		return "Car{" +
				"year='" + year + '\'' +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				", parts=" + parts +
				'}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
