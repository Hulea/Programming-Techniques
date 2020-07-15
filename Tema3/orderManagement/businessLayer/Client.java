package orderManagement.businessLayer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import orderManagement.dataAccessLayer.ConnectionFactory;


public class Client {

    private int id;
    private String name;
    private String address;

    /**
     * Adds clients from SQL database to pdf file
     * @param path the file where we add the report of the clients
     */
    public static void addClientsToPdf(String path){

        Document document = new Document();

        try{
            PdfWriter pdf= PdfWriter.getInstance(document,new FileOutputStream(path));
            document.open();

            document.add(new Paragraph("Client Report"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(70);
            table.setSpacingBefore(5f);
            table.setSpacingAfter(5f);

            float[] columnWidths = {1f,1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Name"));
            cell1.setBorderColor(BaseColor.BLACK);
            cell1.setPadding(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Address"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPadding(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);

            Connection con = ConnectionFactory.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from client;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                PdfPCell cellName = new PdfPCell(new Paragraph(rs.getString("name")));
                cellName.setBorderColor(BaseColor.BLACK);
                cellName.setPadding(10);
                cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellName.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellName);
                PdfPCell cellAddress = new PdfPCell(new Paragraph(rs.getString("address")));
                cellAddress.setBorderColor(BaseColor.BLACK);
                cellAddress.setPadding(10);
                cellAddress.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellAddress.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellAddress);
            }

            document.add(table);
            document.close();
            pdf.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Empty constructor
     */
    public Client (){
    }

    /**
     * Constructor for generating a client
     * @param name name of the client
     * @param address address name
     */
    public Client(String name,String address){
        //this.id=id;
        this.name=name;
        this.address=address;
    }

    public static void showAll(){
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("select * from client");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                //System.out.print((Integer.parseInt(rs.getString("id"))) + ",");
                System.out.print((rs.getString("name")) + ",");
                System.out.println((rs.getString("address")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method for adding a client to the database
     * @param nameAux name of the client we add
     * @param addressAux address of the added client
     */
    public static void addClient(String nameAux,String addressAux){
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("insert into client(name,address) values (?,?);");
            ps.setString(1,nameAux);
            ps.setString(2,addressAux);

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for deleting clients from the database
     * @param nameAux name of the client we delete
     * @param addressAux address of the client we delete
     */
    public static void deleteClient(String nameAux,String addressAux)
    {
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps1 = con.prepareStatement("delete from orders where client_name = '" + nameAux+ "';");
            ps1.executeUpdate();
            PreparedStatement ps = con.prepareStatement("delete from client where name = '" + nameAux+"' and address = '" + addressAux + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for finding a client in the database, used solely for the purpose of verifying
     * @param nameAux name of the client we search for
     * @return returns the searched client
     */
   public static Client find(String nameAux){
       Client toReturn = new Client();
       Connection dbConnection = ConnectionFactory.getConnection();
           try {
               PreparedStatement findStatement = dbConnection.prepareStatement("select * from client where name= ?");
               findStatement.setString(1,nameAux);
               ResultSet rs = findStatement.executeQuery();
               rs.next();

              // toReturn.setId(clientId);
               toReturn.setName(rs.getString("name"));
               toReturn.setAddress(rs.getString("address"));

           } catch (SQLException e) {
               e.printStackTrace();
           }

       return toReturn;
   }

    /**
     * Metod for returning the client as a string
     * @return returns the client as a string
     */
   public String toString(){
       String aux = new String();
      // aux+=this.getId() +",";
       aux+=this.getName() +",";
       aux+=this.getAddress() +",";
       return aux;
   }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
