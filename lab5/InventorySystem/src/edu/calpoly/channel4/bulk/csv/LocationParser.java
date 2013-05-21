package edu.calpoly.channel4.bulk.csv;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import edu.calpoly.channel4.bulk.bean.LocationBean;

public class LocationParser {

   /**
    * Sets up the processors used for locations. There are 11 CSV columns, so 11
    * processors are defined. Empty columns are read as null (hence the
    * NotNull() for mandatory columns).
    * 
    * @return the cell processors
    */
   private static CellProcessor[] getProcessors() {

      final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // name
            new NotNull(new ParseInt()), // fulfiller id
            new NotNull(), // external fulfiller loc id
            new NotNull(new ParseInt()), // internal fulfiller loc id
            new NotNull(), // description
            new NotNull(new ParseDouble()), // latitude
            new NotNull(new ParseDouble()), // longitude
            new NotNull(new ParseLocationStatus()), // status
            new NotNull(new ParseInt()), // safety stock
            new NotNull(new ParseInt()), // manufacturer id
            new NotNull(new ParseInt()), // catalog id
      };

      return processors;
   }

   /**
    * Get locations from a CSV file.
    */
   public static List<LocationBean> getLocations(String filename) throws Exception {

      ICsvBeanReader beanReader = null;
      ArrayList<LocationBean> locations = new ArrayList<LocationBean>();
      
      try {
         beanReader = new CsvBeanReader(new FileReader(filename),
               CsvPreference.STANDARD_PREFERENCE);
         beanReader.getHeader(true); // skip reading the header

         // the field map elements are used to map the values to the bean
         final String[] fieldMap = new String[] { "name", "fulfillerId",
               "extFulfillerLocId", "intFulfillerLocId", "desc", "latitude",
               "longitude", "status", "safetyStock", "manufacturerId",
               "catalogId" };

         final CellProcessor[] processors = getProcessors();

         LocationBean location;

         while ((location = beanReader.read(LocationBean.class, fieldMap,
               processors)) != null) {
            locations.add(location);
         }
      }
      finally {
         if (beanReader != null) {
            beanReader.close();
         }
      }
      
      return locations;
   }
}