import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args)throws Exception {
        
        Class.forName("com.mysql.cj.jdbc.Driver");//loading the driver
        String url="jdbc:mysql://localhost:3306/reservationSystem";//mysql database 
        String name="root",password="@Smehekkelaskar1";
       Connection connection= DriverManager.getConnection(url,name,password);//connection is establishing with the database.
        
        System.out.println("Hotel Management System");
        System.out.println("1.Reserve a room");
        System.out.println("2.View reservation");
        System.out.println("3.Get room number");
        System.out.println("4.Delete reservation");
        System.out.println("5.Exit");
        
        int choice=0;
        System.out.println("Enter you choice ");
        Scanner sc=new Scanner(System.in);
        choice=sc.nextInt();
        switch(choice){
            case 1:reserveRoom(connection ,sc);
            break;
            case 2:viewReservation(connection);
            break;
            case 3:getRoomNumber(connection,sc);
            break;
            case 4:deleteReservation(connection,sc);
            break;
            case 5:exit();
            sc.close();
            return ;
            default:
            System.out.println("Invalid choice");
        }
    }

    public static void  reserveRoom(Connection connection,Scanner sc) {//method for reservation of a room 
        System.out.println("enter the name");
        String name=sc.next();
        sc.nextLine();
        System.out.println("enter the roomnumber");
        int no=sc.nextInt();
        System.out.println("enter the contact number");
        String contactNo=sc.next();
        String query = "INSERT INTO reservation (guest_name, room_no, contactNo) values(?,?,?)";
        
         try(PreparedStatement pst=connection.prepareStatement(query)){//exception handling 
             pst.setString(1,name);
             pst.setInt(2,no);
             pst.setString(3,contactNo);
             int count=pst.executeUpdate();
             if(count>0){
                 System.out.println("Successfully added");
             } else{
                 System.out.println("error in adding");
             }
         } catch(Exception e){
             System.out.println("error "+e.getMessage());
         }
    }
    
public static void viewReservation(Connection connection){//method for viewing the reservation 
String query="select * from reservation";
try(Statement st=connection.createStatement()){
    ResultSet set=st.executeQuery(query);
    while(set.next()){
        System.out.println(set.getInt("reservation_id"));
        System.out.println(set.getString("guest_name"));
        System.out.println(set.getInt("room_no"));
        System.out.println(set.getString("contactNo"));
        System.out.println(set.getTimestamp("reservation_date"));
    }
}
    catch(Exception e){
    System.out.println(" Error"+e.getMessage());
}
    }
    
    public static void getRoomNumber(Connection connection,Scanner sc) {//method to get the rooomnumber
        System.out.println("enter the reservation id");
        int id = sc.nextInt();
        System.out.println("enter the guest name");
        String name = sc.nextLine();
        String query = "select room_no  from reservation" + " where reservation_id=" + id + "and guest_name='" + name + "' ";
        try (Statement st = connection.createStatement()) {
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                System.out.println(rst.getInt("room_no"));
            }
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
    } 
    public  static void deleteReservation(Connection connection,Scanner sc){//method for deleting the reserrvation 
        System.out.println("enter the reservation id to delete ");
        int id=sc.nextInt();
        String query=" delete from reservation where reservation_id="+id;
        try(Statement st=connection.createStatement()){
            int count=st.executeUpdate(query);
            if (count> 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Reservation deletion failed.");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i=5;
        while(i!=0) {
            System.out.print(".");
            Thread.sleep(1000);//1 second of delay for printing the fullstop
            i--;
        }
    System.out.println();
    System.out.println("Thankyou for visiting");
}
}








