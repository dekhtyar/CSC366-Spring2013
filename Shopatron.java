import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Shopatron {
   public static Connection conn = null;
   public static void close() {
      try {
         conn.close();
         System.out.println("Connection closed successfully!\n");
      }
      catch (Exception e) {
         System.out.println("Could not close connection!\n");
      };
   }
   public static int countProducts() {
      int rtn = 0;
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select count(*) From Product");
         result.next();
         rtn = result.getInt(1);
      }
      catch (Exception e) {
         System.out.println("Bad Count Product");
      } 
      return rtn;
   }
   public static int countFulfillers() {
      int rtn = 0;
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select count(*) From Fulfiller");
         result.next();
         rtn = result.getInt(1);
      }
      catch (Exception e) {
         System.out.println("Bad Count Fulfiller");
      } 
      return rtn;
   }
   public static int countLocations() {
      int rtn = 0;
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select count(*) From Location");
         result.next();
         rtn = result.getInt(1);
      }
      catch (Exception e) {
         System.out.println("Bad Count Locations");
      } 
      return rtn;
   }
   public static int countBins() {
      int rtn = 0;
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select count(*) From Bin");
         result.next();
         rtn = result.getInt(1);
      }
      catch (Exception e) {
         System.out.println("Bad Count Bins");
      } 
      return rtn;
   }
   public static void printProducts() {
      List<String> strings = new ArrayList<String>();
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select * From Product");
         for (int i = 0; i < countProducts(); i++) {
            result.next();
            String temp = result.getString(1);
            temp += " " + result.getString(2);
            System.out.println(temp);
         }
      }
      catch (Exception e) {
         System.out.println("Bad Print Products");
      }
   }
   public static void printFulfillers() {
      List<String> strings = new ArrayList<String>();
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select * From Fulfiller");
         for (int i = 0; i < countFulfillers(); i++) {
            result.next();
            String temp = result.getString(1);
            temp += " " + result.getString(2);
            System.out.println(temp);
         }
      }
      catch (Exception e) {
         System.out.println("Bad Print Fulfillers");
      }
   }
   public static void printLocations() {
      List<String> strings = new ArrayList<String>();
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select * From Location");
         for (int i = 0; i < countLocations(); i++) {
            result.next();
            String temp = result.getString(1);
            temp += " " + result.getString(2);
            temp += " " + result.getString(3);
            temp += " " + result.getString(4);
            temp += " " + result.getString(5);
            temp += " " + result.getString(6);
            temp += " " + result.getString(7);
            temp += " " + result.getString(8);
            temp += " " + result.getString(9);
            temp += " " + result.getString(10);
            temp += " " + result.getString(11);
            temp += " " + result.getString(12);
            temp += " " + result.getString(13);
            System.out.println(temp);
         }
      }
      catch (Exception e) {
         System.out.println("Bad Print Bins");
      }
   }
   public static void printBins() {
      List<String> strings = new ArrayList<String>();
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select * From Bin");
         for (int i = 0; i < countBins(); i++) {
            result.next();
            String temp = result.getString(1);
            temp += " " + result.getString(2);
            temp += " " + result.getString(3);
            temp += " " + result.getString(4);
            temp += " " + result.getString(5);
            temp += " " + result.getString(6);
            System.out.println(temp);
         }
      }
      catch (Exception e) {
         System.out.println("Bad Print Bins");
      }
   }
   public static void insertTuples(String file) {
      System.out.println(file);
      String cmd = "";
      try {
         Statement s = conn.createStatement();
         Scanner c = new Scanner(new File(file));
         while (c.hasNextLine()) {
            String temp = c.nextLine();
            temp = temp.replace(';', ' ');
            System.out.println(temp);
            s.executeUpdate(temp);
         }
        System.out.println("Success!\n");
      }
      catch (Exception e) {
         System.out.println("Bad Insert Tuples\n");
      }
   }
   public static void createTables() {
      String cmd = "";
      if (numTables() > 0) {
         System.out.println("Tables already exist - Cannot recreate\n"); 
         return;
      }
      try {
         Statement s = conn.createStatement();
         Scanner c = new Scanner(new File("./SHOPATRON-setup.sql"));
         while (c.hasNextLine()) {
            String temp = c.nextLine();
            if (temp.contains(");")) {
               cmd += ")";
               s.executeUpdate(cmd);
               cmd = "";
            }
            else
               cmd += temp;
         }
      	System.out.println("Success!\n");
      }
      catch (Exception e) {
         System.out.println("Bad Create Tables\n");
      }
   }
   public static void destroyTables() {
      String cmd = "";
      try {
         Statement s = conn.createStatement();
         Scanner c = new Scanner(new File("./SHOPATRON-cleanup.sql"));
         while (c.hasNextLine()) {
            String temp = c.nextLine();
            s.executeUpdate(temp);
         }
      	System.out.println("Success!\n");
      }
      catch (Exception e) {
         System.out.println("Bad Destroy Tables\n");
      }
   }
   public static void clearTable(String table) {
      try {
         Statement s = conn.createStatement();
         s.executeUpdate(
          "Delete From " + table);
         System.out.println("Success!");
      }
      catch (Exception e) {
         System.out.println("Bad Clear Table");
      }
   }
   public static void clearTables() {
      String cmd = "";
      try {
         Statement s = conn.createStatement();
         Scanner c = new Scanner(new File("./SHOPATRON-clear.sql"));
         while (c.hasNextLine()) {
            String temp = c.nextLine();
            s.executeUpdate(temp);
         }
         System.out.println("Success!\n");
      }
      catch (Exception e) {
         System.out.println("Bad Clear Tables\n");
      }
   }
 
   public static int numTables() {
      int num = 0;
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(
          "Select count(*) From (Select table_name from user_tables)");
         result.next();
         num = result.getInt(1);
      }
      catch (Exception e) {
         System.out.println("Couldn't get number of tables!\n");
      }
      return num;
   }
   public static void main(String[] args) {
      String url = "", name = "", pw = "";
      try {
         Scanner in = new Scanner(new File("./ServerSettings.txt"));
         url = in.nextLine();
         name = in.nextLine();
         pw = in.nextLine();
         in.close();
      }
      catch (Exception e) {
         System.out.println("Bad Scanner\n");
      };
      try {
         Class.forName("oracle.jdbc.OracleDriver");
      }
      catch (Exception e) {
         System.out.println("Driver not found!\n");
      };
      conn = null;
      try {
         conn = DriverManager.getConnection(url, name, pw);
      }
      catch (Exception e) {
         System.out.println("Could not open connection!\n");
      };
      System.out.println("Connection opened successfully!\n");
      createTables();
      Scanner in = new Scanner(System.in);
      String commands[] = {"CreateDatabase", "Quit", "Test", "ClearDatabase",
       "DestroyDatabase", "InsertTuples <file>", "PrintBins", "PrintLocations",
       "PrintFulfillers", "ClearTable <tableName>", "PrintProducts"};
      java.util.Arrays.sort(commands);
      do {
         System.out.println("Possible commands:");
         for (int i = 0; i < commands.length; i++) {
            System.out.println("   " + commands[i]);
         }
         System.out.print("\nEnter a command: ");
         String cmd = in.nextLine();
         String[] cmds = cmd.split("\\s+");
         cmd = cmd.toLowerCase();
         if (cmd.contains("quit")){
            close();
            break;
         }
         else if (cmd.contains("inserttuples")) {
            insertTuples(cmds[1]);
         }
         else if (cmd.contains("createdatabase")) {
            createTables();
         }
         else if (cmd.contains("test")) {
            int num = numTables();
            System.out.println("" + num + "\n");
         } 
         else if (cmd.contains("cleardatabase")) {
            clearTables();
         }
         else if (cmd.contains("destroydatabase")) {
            destroyTables();
         }
         else if (cmd.contains("printfulfillers")) {
            System.out.println("");
            printFulfillers();
            System.out.println("");
         }
         else if (cmd.contains("printlocations")) {
            System.out.println("");
            printLocations();
            System.out.println("");
         }
         else if (cmd.contains("printproducts")) {
            System.out.println("");
            printProducts();
            System.out.println("");
         }
         else if (cmd.contains("printbins")) {
            System.out.println("");
            printBins();
            System.out.println("");
         }
         else if (cmd.contains("cleartable")) {
            clearTable(cmds[1]);
         }
         else 
            System.out.println("Not sure what you wrote there...\n");
      } while (true);
   }
}
