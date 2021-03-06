package Projecte;

import static Projecte.projecte_1.connectarBD;
import com.mysql.jdbc.Connection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

   

public class projecte_1 {
    static Connection connectarBD = null;
    private static Object sentencia;
    public static void main (String[] args) throws SQLException, IOException {
        boolean sortir=false;
        connectarBD();
        Scanner teclat = new Scanner (System.in);
        
            //12/11/21
        do{
           System.out.println("****MENU GESTOR PRODUCTES****");
           System.out.println("1.Manteniment de productes A/B/M/C");
           System.out.println("2.Actualitzar stocks");
           System.out.println("3.Generar comandes");
           System.out.println("4.Analisis comandes proveïdors");
           System.out.println("5.Sortir");
           System.out.println("\nTria una de les opcions");
           
           int opcio=teclat.nextInt();
           
           switch (opcio){
               case 1:
                    gestioProductes ();
                    break;
               case 2:
                    actualitzarStocks();
                    break;
               case 3:
                    generarComanda();
                    break;
               case 4:
                    consultarComandes();
                    break;
               case 5:
                   sortir=true;
                   break;
               default:
                   System.out.println("L'Opció no és vàlida");
            }
           
      
          System.out.println(("opció: ")+ opcio);
          
         
      } while (!sortir);
        desconnexioBD();
    }
    
    
    static void altaProductes (){
        System.out.println("Alta productes");
    }
    static void actualitzarStocks(){
        System.out.println("Actualitzar Stock");

    }
    static void generarComanda() throws IOException, SQLException{
        System.out.println("Generar comanda");
        /*
        RsultSet rs = sentencia.executeQuery();
        String proveidorAnt="";
        while (rs.next()){
            idprod=rs.getint("idprod");
            nomprod=rs.getString("nomprod");
            stokprod=rs.getint("stokprod");
            proveidor=rs.getString("proveidor");
            categoria=rs.getString("categoria");
            
            if (!proveidorAnt.equals(proveidor)){
                proveidorAnt = proveidor;
                System.out.println("canvi proveidor: " + proveidor);
            }
        }
        */
        
        String consola ="SELECT * FROM productes WHERE";
        PreparedStatement ps = connectarBD.prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            FileWriter fw = new FileWriter("fichero.txt",true);
            BufferedWriter bf = new BufferedWriter(fw);
            PrintWriter escritor = new PrintWriter(bf);
            escritor.println("");
            escritor.println("");
            escritor.println("");
            escritor.println("");
            
            do{
                pw.println("    " + nomprod + "\t" + categoria + "\t" + (MAXSTOCK - rs.getInt("estock")))
            }while (rs.next());
        }
        
        
        
    }
    static void consultarComandes(){
        System.out.println("Consultar comanda");
        
    }
    
    static void gestioProductes () throws SQLException {
        Scanner teclat = new Scanner (System.in);
        boolean enrere=false;
        do{
           System.out.println("****MENU GESTOR PRODUCTES****");
           System.out.println("1.Llista Productes");
           System.out.println("2.Alta de Productes");
           System.out.println("3.modificar Productes");
           System.out.println("4.Esborrar Productes");
           System.out.println("5.Enrere");
           System.out.println("\nTria una de les opcions:");

            int opcio=teclat.nextInt();
            teclat.nextLine();


           switch (opcio){
                case 1:
                    llistaProductes();
                    break;
                case 2:
                    altaProductes();
                    break;
                case 3:
                    modificarProductes();
                    break;
                case 4:
                    esborrarProductes();
                    break;
                case 5:
                   enrere=true;
                   break;
                default:
                   System.out.println("L'Opció no és vàlida");
            }
        } while (!enrere);
                  
    }

    public static void desconnexioBD() {
        System.out.println("Desconnectat de la BD");
    }

    

    public static void llistaProductes() throws SQLException {
        System.out.println("Llistem productes");
        String consulta ="SELECT * FROM productes ORDER BY codi_prod";
        //preparem la consulta
        PreparedStatement ps = connectarBD.prepareStatement(consulta);
        //lencem la consulta
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            System.out.println("codi_prod: " + rs.getInt("codi_prod"));
            System.out.println("model: " + rs.getString("model"));
            System.out.println("estoc: " + rs.getInt("estoc"));
            System.out.println("marca: " + rs.getString("marca"));

        }
    }
    static void altaProducte() throws SQLException{
        Scanner teclat = new Scanner (System.in);
        System.out.println("Fem l'alta a un producte nou");
        String codi_prod = teclat.nextLine();
        String model = teclat.nextLine();
        String estoc = teclat.nextLine();
        String marca = teclat.nextLine();
        //PreparedStatement sentencia = connectarBD.prepareStatement (consulta);
        
        String sentenciaSql = "INSERT INTO productes (codi_prod, model, estoc, marca) VALUES (?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try{   
            sentencia = connectarBD.prepareStatement(sentenciaSql);
            sentencia.setString(1, codi_prod);
            sentencia.setString(2, model);
            sentencia.setString(3, estoc);
            sentencia.setString(4, marca);
            sentencia.executeUpdate();
      } catch (SQLException sqle){
        sqle.printStackTrace();
      } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    

    public static void modificarProductes() {
        Scanner teclat = new Scanner (System.in);
        System.out.println("Modifiquem productes");
        String model = teclat.nextLine();
        String estoc = teclat.nextLine();
        String marca = teclat.nextLine();
        String sentenciaSql = "UPDATE productes SET model = ?, estoc = ?, marca = ?" + "WHERE model = ?";
        PreparedStatement sentencia = null;
 
    try {
      sentencia = connectarBD.prepareStatement(sentenciaSql);
      sentencia.setString(2, model);
      sentencia.setString(3, estoc);
      sentencia.setString(4, marca);
      sentencia.executeUpdate();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    } finally {
      if (sentencia != null)
            try {
              sentencia.close();
            } catch (SQLException sqle) {
              sqle.printStackTrace();
            }
        }
    }

    public static void esborrarProductes() {
        Scanner teclat = new Scanner (System.in);
        System.out.println("Esborrar productes");
        String sentenciaSql = "DELETE FROM productes WHERE nombre = ?";
        String codi_prod = teclat.nextLine();
        String model = teclat.nextLine();
        String estoc = teclat.nextLine();
        String marca = teclat.nextLine();
        PreparedStatement sentencia = null;
 
    try {
      sentencia = connectarBD.prepareStatement(sentenciaSql);
      sentencia.setString(1, codi_prod);
      sentencia.setString(2, model);
      sentencia.setString(3, estoc);
      sentencia.setString(4, marca);
      sentencia.executeUpdate();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    } finally {
      if (sentencia != null)
            try {
              sentencia.close();
            } catch (SQLException sqle) {
              sqle.printStackTrace();
            }
        }
    }
    static void connectarBD() {

        String servidor = "jdbc:mysql://192.168.1.93:3307/";
        String bbdd = "electroimp";
        String user = "root";
        String password = "Alastor666Radio";

        try {

            connectarBD = (Connection) DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }     
}
