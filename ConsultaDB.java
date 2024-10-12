package ABC_java_mariadb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConsultaDB {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/p2"; // Conexión a BD
        String user = "p2"; // Usuario
        String password = "p2"; 
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Conexión a la base de datos
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            boolean continuar = true;

            while (continuar) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Insertar alumno");
                System.out.println("2. Actualizar alumno");
                System.out.println("3. Eliminar alumno");
                System.out.println("4. Consultar alumnos");
                System.out.println("5. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1: // Insertar alumno
                        System.out.print("Ingrese el nombre del alumno: ");
                        String nombreInsertar = scanner.nextLine();
                        String insertSql = "INSERT INTO alumno (nombre) VALUES ('" + nombreInsertar + "')";
                        stmt.executeUpdate(insertSql);
                        System.out.println("Alumno insertado.");
                        break;

                    case 2: // Actualizar alumno
                        System.out.print("Ingrese el ID del alumno a actualizar: ");
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nombreActualizar = scanner.nextLine();
                        String updateSql = "UPDATE alumno SET nombre='" + nombreActualizar + "' WHERE id=" + idActualizar;
                        stmt.executeUpdate(updateSql);
                        System.out.println("Alumno actualizado.");
                        break;

                    case 3: // Eliminar alumno
                        System.out.print("Ingrese el ID del alumno a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        String deleteSql = "DELETE FROM alumno WHERE id=" + idEliminar;
                        stmt.executeUpdate(deleteSql);
                        System.out.println("Alumno eliminado.");
                        break;

                    case 4: // Consultar alumnos
                        String selectSql = "SELECT * FROM alumno"; // Consulta a la tabla
                        ResultSet rs = stmt.executeQuery(selectSql);
                        while (rs.next()) {
                            int id = rs.getInt("id"); // Obtiene el ID del alumno
                            String nombre = rs.getString("nombre"); // Obtiene el nombre del Alumno
                            System.out.println("ID: " + id + ", Nombre: " + nombre);
                        }
                        rs.close();
                        break;

                    case 5: // Salir
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el statement y la conexión
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}

