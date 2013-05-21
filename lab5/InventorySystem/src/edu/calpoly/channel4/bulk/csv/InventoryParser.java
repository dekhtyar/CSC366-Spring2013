package edu.calpoly.channel4.bulk.csv;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import edu.calpoly.channel4.bulk.bean.InventoryBean;

public class InventoryParser {

   /**
    * Sets up the processors used for inventory. There are 11 CSV columns, so 11
    * processors are defined. Empty columns are read as null (hence the
    * NotNull() for mandatory columns).
    * 
    * @return the cell processors
    */
   private static CellProcessor[] getProcessors() {

      final CellProcessor[] processors = new CellProcessor[] { 
            new Optional(), // product name
            new NotNull(), // SKU
            new NotNull(), // UPC
            new NotNull(new ParseInt()), // safety stock
            new NotNull(new ParseDouble()), // LTD
            new NotNull(new ParseInt()), // manufacturer id
            new NotNull(new ParseInt()), // catalog id
            new NotNull(new ParseInt()), // on hand
            new NotNull(), // bin name
            new NotNull(), // external fulfiller loc id
            new NotNull(new ParseInt()), // internal fulfiller loc id
      };

      return processors;
   }

   /**
    * Get inventory from a CSV file.
    */
   public static List<InventoryBean> getInventories(String filename) throws Exception {

      ICsvBeanReader beanReader = null;
      ArrayList<InventoryBean> inventories = new ArrayList<InventoryBean>();
      
      try {
         beanReader = new CsvBeanReader(new FileReader(filename),
               CsvPreference.STANDARD_PREFERENCE);
         beanReader.getHeader(true); // skip reading the header

         // the field map elements are used to map the values to the bean
         final String[] fieldMap = new String[] { "productName", "UPC", "SKU",
               "safetyStock", "LTD", "manufacturerId", "catalogId", "onHand",
               "binName", "extFulfillerLocId", "intFulfillerLocId" };

         final CellProcessor[] processors = getProcessors();

         InventoryBean inventory;

         while ((inventory = beanReader.read(InventoryBean.class, fieldMap,
               processors)) != null) {
            inventories.add(inventory);
         }
      }
      finally {
         if (beanReader != null) {
            beanReader.close();
         }
      }
      
      return inventories;
   }
}
