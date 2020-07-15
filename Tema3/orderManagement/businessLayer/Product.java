package orderManagement.businessLayer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import orderManagement.dataAccessLayer.ConnectionFactory;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    private String name;
    private int quantity;
    private double price;

    /**
     * Method used for adding the products in the SQL database to a pdf file
     * @param path the file we write in
     */
    public static void addProductToPdf(String path){

        Document document = new Document();

        try{
            PdfWriter pdf= PdfWriter.getInstance(document,new FileOutputStream(path));
            document.open();

            document.add(new Paragraph("Product Report"));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f,1f,1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Name"));
            cell1.setBorderColor(BaseColor.BLACK);
            cell1.setPadding(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Quantity"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPadding(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Price"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPadding(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            Connection con = ConnectionFactory.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from product;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                PdfPCell cellName = new PdfPCell(new Paragraph(rs.getString("name")));
                cellName.setBorderColor(BaseColor.BLACK);
                cellName.setPadding(10);
                cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellName.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellName);
                PdfPCell cellQuantity = new PdfPCell(new Paragraph(rs.getString("quantity")));
                cellQuantity.setBorderColor(BaseColor.BLACK);
                cellQuantity.setPadding(10);
                cellQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQuantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellQuantity);
                PdfPCell cellPrice = new PdfPCell(new Paragraph(rs.getString("price")));
                cellPrice.setBorderColor(BaseColor.BLACK);
                cellPrice.setPadding(10);
                cellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPrice.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellPrice);
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
    public Product(){ }

    /**
     * Constructor used for generating a new product
     * @param name name of the product
     * @param quantity quantity of the product
     * @param price price of the product
     */
    public Product(String name, int quantity, double price) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Method used for showing all the products present in the  database
     *
     */
    public static void showAll(){
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                System.out.print(rs.getString("name") + ",");
                System.out.print(Integer.parseInt(rs.getString("quantity")) + ",");
                System.out.println(Double.parseDouble(rs.getString("price")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method used for adding a new product to the database
     * @param nameAux name of the product we want to add
     * @param quantityAux name of the quality we want to add
     * @param priceAux the price we want to add
     */
    public static void addProduct(String nameAux,int quantityAux,double priceAux) {
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps1 = con.prepareStatement("select * from product where name = ?;");
            ps1.setString(1,nameAux);
            ResultSet rs = ps1.executeQuery();
            if(rs.next()) {
                PreparedStatement ps = con.prepareStatement("update product set quantity = quantity + ? where name=?;");
                ps.setLong(1,quantityAux);
                ps.setString(2,nameAux);
                ps.executeUpdate();
            }
            else {
                PreparedStatement ps = con.prepareStatement("insert into product(name,quantity,price) values (?,?,?);");
                ps.setString(1, nameAux);
                ps.setLong(2, quantityAux);
                ps.setDouble(3, priceAux);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for deleting a product from the database
     * @param productName the product we want to delete
     */
    public static void deleteProduct(String productName){
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps1 = con.prepareStatement("delete from orders where product_name='" + productName + "';");
            ps1.executeUpdate();
            PreparedStatement ps = con.prepareStatement("delete from product where name='" + productName + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Method used for verifying if the product stock is large enough for the wanted quantity
     * from the order
     * @param productNameAux name of the product
     * @param wantedQuantity the quantity
     * @return true if the stock is big enough, false otherwise
     */
    public static boolean hasQuantity(String productNameAux,int wantedQuantity){
        Connection con = ConnectionFactory.getConnection();
        int aux;

        try{
            PreparedStatement ps = con.prepareStatement("select quantity from product where name='" + productNameAux + "' limit 1;");
            ResultSet rs = ps.executeQuery();
            rs.next();

            aux = Integer.parseInt(rs.getString("quantity"));
            if(aux >= wantedQuantity)
               return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * IF the product stock is large enough , the wanted quantity from the orders will be substracted from the product stock
     * @param productNameAux name of the product
     * @param wantedQuantity the amount of stock we substract from the quantity
     */
    public static void decreaseQuantity(String productNameAux,int wantedQuantity){
        Connection con = ConnectionFactory.getConnection();

        if(Product.hasQuantity(productNameAux,wantedQuantity))
        {
            try{
                PreparedStatement ps = con.prepareStatement("update product set quantity = quantity - ? where name = ?;");
                ps.setLong(1,wantedQuantity);
                ps.setString(2,productNameAux);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method used for finding a product in the database used solely for the purpose of verifying
     * @param productName the name of the product we search for
     * @return return the product we searched for
     */
    public static Product find(String productName){
        Product toReturn = new Product();
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            PreparedStatement findStatement = dbConnection.prepareStatement("select * from product where name= ? limit 1;");
            findStatement.setString(1,productName);
            ResultSet rs = findStatement.executeQuery();
            rs.next();

            toReturn.setName(rs.getString("name"));
            toReturn.setQuantity(Integer.parseInt(rs.getString("quantity")));
            toReturn.setPrice(Double.parseDouble(rs.getString("price")));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    /**
     * Returns an order in string format
     * @return the product as a string
     */
    public String toString(){
        String aux = new String();
        aux+=this.getName() +",";
        aux+=this.getQuantity() +",";
        aux+=this.getPrice() +",";
        return aux;
    }



    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
