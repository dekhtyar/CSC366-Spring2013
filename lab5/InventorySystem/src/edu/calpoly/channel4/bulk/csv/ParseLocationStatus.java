package edu.calpoly.channel4.bulk.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

/**
 * Parses a Location status. A location status is stored in a CSV as the string
 * "active" or "inactive". This cell processor will return an int
 * (1 for "active", 2 for "inactive") representing the status.
 */
public class ParseLocationStatus extends CellProcessorAdaptor {
   
   public enum LocationStatus {
      ACTIVE("active", 1), INACTIVE("inactive", 2);
      
      /*package*/ String desc;
      /*package*/ int code;
      
      LocationStatus(String desc, int code) {
         this.desc = desc;
         this.code = code;
      }
   }

   public ParseLocationStatus() {
      super();
   }

   public ParseLocationStatus(CellProcessor next) {
      // this constructor allows other processors to be chained after ParseDay
      super(next);
   }

   public Object execute(Object value, CsvContext context) {
      
      // throw an Exception if the input is null
      validateInputNotNull(value, context);

      for (LocationStatus status : LocationStatus.values()) {
         if (status.desc.equalsIgnoreCase(value.toString())) {
            // passes the status code to the next processor in the chain
            return next.execute(status.code, context);
         }
      }

      throw new SuperCsvCellProcessorException(String.format(
               "Could not parse '%s' as a location status", value), context, this);
   }
}
