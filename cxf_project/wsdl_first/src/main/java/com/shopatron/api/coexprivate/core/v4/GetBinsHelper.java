package com.shopatron.api.coexprivate.core.v4;

import java.math.BigInteger;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GetBinsHelper{
   public static BinResponse getBins(BinRequest binRequest){
      ArrayOfImplBin binArray = new ArrayOfImplBin();
      //must use binArray.getBins.[method] to modify bins inside array
      
      String ext = binRequest.getExternalLocationID();
      BigInteger fulfillerid = binRequest.getFulfillerID();
      
      int binCount = doRequest(binArray, ext, fulfillerid, binRequest.getResultsStart(), binRequest.getNumResults());
      
      if (binCount > 0){
         BinResponse response = new BinResponse();
         response.setBins(binArray);
         response.setResultCount(BigInteger.valueOf(binCount));
         return response;
      }
      else{
         return null;
      }
      
   }
   
   private static int doRequest(ArrayOfImplBin binArray, String ext, BigInteger fulfiller, BigInteger start, BigInteger max){
      
      int binCount = 0;
      
      StringBuilder queryBuilder = new StringBuilder("Select binID, status, name, type from Bins where ")
        .append("EXTFULFILLERID = '" + ext + "'")
        .append("and FulfillerID = " + fulfiller);
      
      if (max != null && start == null) {
         start = BigInteger.valueOf(0);
      }
      if (max != null && start != null) {
        queryBuilder.append(" LIMIT " + start + ", " + max + ";")
        .toString();
      }
      else {
        queryBuilder.append(";");
      }
      
      String query = queryBuilder.toString();
   
      try {
         Connection conn = DbConnect.getConnection();
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(query);
         
         while (result.next()){
            BigInteger binID = result.getBigDecimal("BinID").toBigInteger();
            String binName = result.getString("name");
            String status = result.getString("status");
            String type = result.getString("type");
            
            Bin bin = new Bin();
            bin.setBinID(binID);
            bin.setBinStatus(status);
            bin.setBinType(type);
            bin.setExternalLocationID(ext);
            bin.setFulfillerID(fulfiller);
            bin.setName(binName);
            
            binArray.getItems().add(bin); 
            binCount++;
             
         }
         
         return binCount;
      }
      catch (SQLException e) {
         System.err.println(e.getMessage());
         return -1;
      }
      

   }
}