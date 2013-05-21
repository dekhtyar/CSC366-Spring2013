package edu.calpoly.channel4.bulk.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import edu.calpoly.channel4.bulk.bean.BinBean;

public class BinParser {

   /**
    * Sets up the processors used for bins. There are 5 CSV columns, so 5
    * processors are defined. Empty columns are read as null (hence the
    * NotNull() for mandatory columns).
    * 
    * @return the cell processors
    */
   private static CellProcessor[] getProcessors() {

      final CellProcessor[] processors = new CellProcessor[] {
            new NotNull(), // external fulfiller loc id
            new NotNull(new ParseInt()), // internal fulfiller loc id
            new NotNull(), // bin name
            new NotNull(), // bin type
            new NotNull() // bin status
      };

      return processors;
   }

   /**
    * Get Bins from a CSV file.
    * @throws IOException 
    */
   public static List<BinBean> getBins(String filename) throws IOException {

      ICsvBeanReader beanReader = null;
      ArrayList<BinBean> bins = new ArrayList<BinBean>();
      
      try {
         beanReader = new CsvBeanReader(new FileReader(filename),
               CsvPreference.STANDARD_PREFERENCE);
         beanReader.getHeader(true); // skip reading the header

         // the field map elements are used to map the values to the bean
         final String[] fieldMap = new String[] { "extFulfillerLocId", 
               "intFulfillerLocId", "name", "status", "type" };

         final CellProcessor[] processors = getProcessors();

         BinBean bin;

         while ((bin = beanReader.read(BinBean.class, fieldMap,
               processors)) != null) {
            bins.add(bin);
         }
      }
      finally {
         if (beanReader != null) {
            beanReader.close();
         }
      }
      
      return bins;
   }
}
