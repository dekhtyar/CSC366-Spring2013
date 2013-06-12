package com.shopatron.api.coexprivate.core.v4;

import java.math.BigInteger;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class GetBinTypeHelper{
   public static GetBinTypesResponse getBinTypesResponse(){
      
      GetBinTypesResponse response = new GetBinTypesResponse();
      
      ResultSet res;
      try{
         res = Utils.doSelectQuery("Select type from Bins group by type;");

         while (res.next()){
            String t = res.getString("type");
            BinType generalType = new BinType();
            generalType.setBinType(t);
            response.getGetBinTypesReturn().add(generalType);   
         }
      }catch (Exception e){
         System.out.println("err");
      }
      
      
      
      return response;
   }
   
}