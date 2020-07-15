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

public class Orders {

    private int id;
    private String clientName;
    private int client_id;
    private String productName;
    private int wantedQuantity;
    private int totalPrice;

    /**
     * Generates a bill for the order
     * @param path the file where the bill is written
     * @param clientNameAux client name
     * @param productNameAux product name
     * @param wantedQuantityAux quantity
     */
    public static void generateBill(String path,String clientNameAux,String productNameAux, int wantedQuantityAux){

        Document document = new Document();

        try {
            PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            document.add(new Paragraph("Bill"));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Client Name"));
            cell1.setBorderColor(BaseColor.BLACK);
            cell1.setPadding(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Product"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPadding(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPadding(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Price"));
            cell4.setBorderColor(BaseColor.BLACK);
            cell4.setPadding(10);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            Connection con = ConnectionFactory.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from orders where client_name=? and product_name=? and wanted_quantity=?;");
            ps.setString(1, clientNameAux);
            ps.setString(2, productNameAux);
            ps.setLong(3, wantedQuantityAux);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

            PdfPCell cellName = new PdfPCell(new Paragraph(rs.getString("client_name")));
            cellName.setBorderColor(BaseColor.BLACK);
            cellName.setPadding(10);
            cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellName.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellName);
            PdfPCell cellProduct = new PdfPCell(new Paragraph(rs.getString("product_name")));
            cellProduct.setBorderColor(BaseColor.BLACK);
            cellProduct.setPadding(10);
            cellProduct.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellProduct.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellProduct);
            PdfPCell cellQuantity = new PdfPCell(new Paragraph(rs.getString("wanted_quantity")));
            cellQuantity.setBorderColor(BaseColor.BLACK);
            cellQuantity.setPadding(10);
            cellQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQuantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellQuantity);

            PreparedStatement psAux = con.prepareStatement("select price from product where name='" + rs.getString("product_name") + "';");
            ResultSet rs2 = psAux.executeQuery();
            while(rs2.next()){
            double priceAux = rs2.getDouble("price") * rs.getInt("wanted_quantity");
            String aux = String.valueOf(priceAux);
            PdfPCell cellPrice = new PdfPCell(new Paragraph(aux));
            cellPrice.setBorderColor(BaseColor.BLACK);
            cellPrice.setPadding(10);
            cellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPrice.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellPrice);

            document.add(table);
            document.close();
            pdf.close();

        }}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addOrdersToPdf(String path){

        Document document = new Document();

        try{
            PdfWriter pdf= PdfWriter.getInstance(document,new FileOutputStream(path));
            document.open();

            document.add(new Paragraph("Order Report"));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f,1f,1f,1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Client Name"));
            cell1.setBorderColor(BaseColor.BLACK);
            cell1.setPadding(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Product"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPadding(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPadding(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Price"));
            cell4.setBorderColor(BaseColor.BLACK);
            cell4.setPadding(10);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            Connection con = ConnectionFactory.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from orders;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                PdfPCell cellName = new PdfPCell(new Paragraph(rs.getString("client_name")));
                cellName.setBorderColor(BaseColor.BLACK);
                cellName.setPadding(10);
                cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellName.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellName);
                PdfPCell cellProduct = new PdfPCell(new Paragraph(rs.getString("product_name")));
                cellProduct.setBorderColor(BaseColor.BLACK);
                cellProduct.setPadding(10);
                cellProduct.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellProduct.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellProduct);
                PdfPCell cellQuantity = new PdfPCell(new Paragraph(rs.getString("wanted_quantity")));
                cellQuantity.setBorderColor(BaseColor.BLACK);
                cellQuantity.setPadding(10);
                cellQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQuantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellQuantity);

                PreparedStatement psAux = con.prepareStatement("select price from product where name='"+rs.getString("product_name") + "';");
                ResultSet rs2 = psAux.executeQuery();
                rs2.next();
                double priceAux = rs2.getDouble("price") * rs.getInt("wanted_quantity");
                String aux = String.valueOf(priceAux);
                PdfPCell cellPrice = new PdfPCell(new Paragraph(aux));
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
     * Empty Constructor
     */
    public Orders(){ }

    /**
     * Constructor for creating new orders
     * @param clientName the name of the client
     * @param productName the name of the product
     * @param wantedQuantity the quantity
     */
    public Orders( String clientName, String productName, int wantedQuantity) {
        this.clientName = clientName;
        //this.client_id = client_id;
        this.productName = productName;
        this.wantedQuantity = wantedQuantity;
    }

    /**
     * Method for showing all the orders in the database
     */
    public static void showAll(){
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("select * from orders");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                System.out.print((Integer.parseInt(rs.getString("id"))) + ",");
                System.out.print(rs.getString("client_name") + ",");
                //System.out.print(Integer.parseInt(rs.getString("client_id")) + ",");
                System.out.print(rs.getString("product_name") + ",");
                System.out.println(Integer.parseInt(rs.getString("wanted_quantity")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method for adding a new order to the database
     * @param clientNameAux the name of the client from the order we want to delete
     * @param productNameAux the name of the product from the order we want to delete
     * @param wantedQuantity the quantitty from the product we want to delete
     */
    public static void addOrder(String clientNameAux,String productNameAux,int wantedQuantity)
    {
        Connection con = ConnectionFactory.getConnection();

        if(Product.hasQuantity(productNameAux,wantedQuantity))
        {
            try{
                PreparedStatement ps = con.prepareStatement("insert into orders(client_name,product_name,wanted_quantity) values (?,?,?);");
                ps.setString(1,clientNameAux);
                ps.setString(2,productNameAux);
                ps.setLong(3,wantedQuantity);

                Product.decreaseQuantity(productNameAux,wantedQuantity);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for finding an order in the database used solely for the purpose of verifying
     * @param client the client from the order we are searching for
     * @param product the product from the order we are searching for
     * @return returns the order we searched for
     */
        public static Orders find(String client,String product){
            Orders toReturn = new Orders();
            Connection dbConnection = ConnectionFactory.getConnection();
            try {
                PreparedStatement findStatement = dbConnection.prepareStatement("select * from orders where client_name=? and product_name=? ;");
                findStatement.setString(1,client);
                findStatement.setString(2,product);
                ResultSet rs = findStatement.executeQuery();
                rs.next();

                toReturn.setId(Integer.parseInt(rs.getString("id")));
                toReturn.setClientName(rs.getString("client_name"));
               // toReturn.setClientId(Integer.parseInt(rs.getString("client_id")));
                toReturn.setProductName(rs.getString("product_name"));
                toReturn.setWantedQuantity(Integer.parseInt(rs.getString("wanted_quantity")));
                
                
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return toReturn;
        }


    /**
     * Returns an order as a string
     * @return return the order as a string
     */
    public String toString(){
                String aux = new String();
                aux+=this.getId() +",";
                aux+=this.getClientName() +",";
                //aux+=this.getClient_id() +",";
                aux+=this.getProductName() +",";
                aux+=this.getWantedQuantity();
                return aux;

            }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getClientName() { return clientName; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public int getWantedQuantity() { return wantedQuantity; }

    public void setWantedQuantity(int wantedQuantity) { this.wantedQuantity = wantedQuantity; }
}
