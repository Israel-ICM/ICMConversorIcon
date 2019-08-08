package Tools;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author josue
 */
public class Tools {
    //static private String[] tipos_permitidos=ArchivosPermitidos.getArchivosPermitidos();
    static public final String[] capas = {"presql", "sql", "vos", "model", "gui", "serv"};
    static public final String PATH_UBICACION_SOFTWARE = System.getProperty("user.dir");
    static public final String SERVER_IP = "192.168.1.99";//Esto ya no es fijo es configurable (Este solo es el valor por dafault)
    public static final String PROGRAM_NAME = "SIAP Minificador GS";
    public static final String CODIGO_PROGRAMA = "SMGS";//Este codigo es para hacer el filtro cuando se necesita hacer una actualización
    public static final String APPLICATION_UPDATE_NAME = "SIAP_Actualizacion.jar";
    public static final String VERSION = "1.2.9";
    public static final String LINUX = "Linux";
    public static final String WINDOWS = "Windows";
    public static final String MACOS = "MacOS";
    public static final String EXTENSION_SIAPLIST = "siaplist";
    public static final int SW_REPOSITORIO_GIT_LOCAL = 1;
    public static final int SW_REPOSITORIO_GIT_SERVER = 2;
    public static final int SW_REPOSITORIO_GIT_BARE = 3;
    public static final String DB_EMPRESAS = "siapd";
    public static final String FILTRO_ARCHVOS_SIAPLIST = "siaplist";
    public static final String FILTRO_ARCHVOS_PHP = "php";
    public static final String FILTRO_ARCHVOS_JS = "js";
    public static final String FILTRO_ARCHVOS_SWF = "swf";
    
    //Colores
    public static final Color COLOR_ERROR = new Color(213, 112, 112);
    public final ImageIcon IMG_PHP = new ImageIcon(getClass().getResource("/img/icon-php.png"));
    public final ImageIcon IMG_JS = new ImageIcon(getClass().getResource("/img/icon-js.png"));
    public final ImageIcon IMG_SWF = new ImageIcon(getClass().getResource("/img/icon-swf.png"));
    public final ImageIcon IMG_SQL = new ImageIcon(getClass().getResource("/img/icon-sql.png"));
    public final ImageIcon IMG_IMG = new ImageIcon(getClass().getResource("/img/icon-img.png"));
    public final ImageIcon IMG_SIAP = new ImageIcon(getClass().getResource("/img/icon-siap.png"));
    public final ImageIcon IMG_CSS = new ImageIcon(getClass().getResource("/img/icon-css.png"));
    public final ImageIcon IMG_ALL_EXTENSION = new ImageIcon(getClass().getResource("/img/icon-todo.png"));
    
    public static void nuevaCarpeta(String path){
        File file = new File(path);
        file.mkdir();
    }
    
    public ImageIcon getImageExtension(String extension){
        if(extension.equals("php"))
            return IMG_PHP;
        else if(extension.equals("js"))
            return IMG_JS;
        else if(extension.equals("swf"))
            return IMG_SWF;
        else if(extension.equals("sql"))
            return IMG_SQL;
        else if(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif"))
            return IMG_IMG;
        else if(extension.equals("siap"))
            return IMG_SIAP;
        else if(extension.equals("css"))
            return IMG_CSS;
        else
            return IMG_ALL_EXTENSION;
    }
    
    public static String getPathXPathArchivo(String path_archivo)
    {
        String[] a = path_archivo.split(getSeparador(2));
        String[] nu = new String[a.length-1];
        for(int i = 0; i < a.length-1; i++){
            nu[i] = a[i];
        }
        return String.join(getSeparador(), nu);
    }
    public void copiarArchivo(String path, String path_destino){
        if(!existePath(path_destino)){
            try {
                File file = new File(getPathXPathArchivo(path_destino));
                File archivo_destino = new File(file.getAbsolutePath()+getSeparador());
                file.mkdir();
                archivo_destino.createNewFile();
                
                InputStream archivo_copy = new FileInputStream(path);
                //InputStream archivo_copy = getClass().getResourceAsStream(path);
                //se crea un archivo en disco
                OutputStream nuevo_archivo=new FileOutputStream(path_destino);
                byte[] buf=new byte[1024];
                int longitud;
                //se escribe byte a byte el archivo Sound
                while((longitud=archivo_copy.read(buf))>0){
                    nuevo_archivo.write(buf,0,longitud);
                }
                archivo_copy.close();
                nuevo_archivo.close();
                System.out.println("archivo copiado en disco");
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Tools/copiarArchivo(): "+ex.getMessage());
            }
        }
        else
            JOptionPane.showMessageDialog(null, "El archivo ya existe");
    }
    
    public static String getSeparador(int cant){
        String separadores = "";
        if(!getOS().equals(WINDOWS))
            cant = 1;
        for(int i = 0; i < cant; i++)
            separadores += getSeparador();
        return separadores;
    }
    
    public static String getSeparador(){
        if(getOS().equals(WINDOWS))
            return "\\";
        else
            return "/";
    }
    
    public static String getOS(){
        String so = System.getProperty("os.name");
        if(so.indexOf("Windows") != -1)
            return WINDOWS;
        if(so.indexOf("Linux") != -1)
            return LINUX;
        else//Aqui para ser Mac podría retornar OSX o MacOS 
            return MACOS;
    }
    
    public static void ejecutarCmd(String comando){
        try{
            Runtime.getRuntime().exec(comando);
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "No es posible abrir la actualización reciente: "+ex.getMessage());
        }
    }
    
    public static String[] convertirNumTask(String[] arr_num_task){
        for(int i=0;i<arr_num_task.length;i++){
            arr_num_task[i] = "#"+arr_num_task[i];
        }
        return arr_num_task;
    }
    public static String[] filterStringInArray(String cadena, String[] lista){
        ArrayList<String> arr_coincidentes = new ArrayList<>();
        ArrayList<String> arr_archivos = new ArrayList<>();
        
        for(String palabras_clave : cadena.split(" ")){
            for(String cad : lista){
                if(cad.toLowerCase().indexOf(palabras_clave.toLowerCase())!=-1)
                    arr_coincidentes.add(cad);
            }
        }
        
        String[] arr_salida=(String[]) arr_coincidentes.toArray(new String[arr_coincidentes.size()]);
        if(cadena.split(" ").length>1){
            for(String aux : lista){
                int count=0;
                for(String coincid : arr_salida){
                    if(aux.equals(coincid))
                        count++;
                }
                if(count>1)
                    arr_archivos.add(aux);
            }
        }
        else
            arr_archivos=arr_coincidentes;
        
        return (String[]) arr_archivos.toArray(new String[arr_archivos.size()]);
        //System.out.println(String.join(", ", arr_archivos_string));
        
    }
    
    public static void ejecutarJar(String nombre_jar){
        //try{
            //File abrir=new File("C:/Users/srv2/Documents/NetBeansProjects/Siap Personal/dist/Siap_Personal.jar");
            //File abrir=new File("C:/Program Files (x86)/SIAP Software/My Product Name/Siap_Personal.jar");
            /*File abrir=new File(System.getProperty("user.dir")+"\\"+nombre_jar);
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + abrir);*/
            System.out.println("COMANDO A EJECUTAR: "+"java -jar "+System.getProperty("user.dir")+getSeparador()+nombre_jar);
            ejecutarCmd("java -jar "+System.getProperty("user.dir")+getSeparador()+nombre_jar);
        /*}
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "No es posible abrir la actualización reciente");
        }*/
    }
    
    public static String getIP(){
        try {
            Inet4Address host=(Inet4Address) Inet4Address.getLocalHost();
            return host.getHostAddress();
        }
        catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }
    public static String getUsuarioPC(){
        try {
            Inet4Address host=(Inet4Address) Inet4Address.getLocalHost();
            return host.getHostName();
        }
        catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }
    
    public static String getPathToProjectGITXPath_file(String path_archivo){
        String empresa = Tools.getNombreEmpresaXPath(path_archivo);
        String[] arr_path = path_archivo.split(empresa);
        return arr_path[0]+empresa;
    }
    public static boolean verificarInteger(char num){
        if (num >= '0' && num <='9')
            return true;
        else
            return false;
    }
    public static boolean verificarNumTask(char num){
        if ((num >= '0' && num <='9') || num==',' || num==' ')
            return true;
        else
            return false;
    }
    public static boolean verificarLetra(char letra){
        if ((letra >= 'a' && letra <='z') || (letra >= 'A' && letra <='Z') || letra == 'ñ' || letra == 'Ñ')
            return true;
        else
            return false;
    }
    public static boolean verificarDouble(char num){
        if ((num >= '0' && num <='9') || num=='.')
            return true;
        else
            return false;
    }
    public static String[] Files_srcToFiles(String[] archivos_src){
        String[] arr_archivos=new String[archivos_src.length];
        for(int i=0;i<archivos_src.length;i++){
            String file="";
            if(archivos_src[i].indexOf("_src")!=-1)
                file=archivos_src[i].replaceAll("_src", "");
            else
                file=archivos_src[i];
            arr_archivos[i]=file;
        }
        return arr_archivos;
    }
    
    public static String[] FilesToFiles_src(String[] archivos){
        String[] arr_archivos_src=new String[archivos.length];
        for(int i=0;i<archivos.length;i++){
            String src="";
            if(archivos[i].indexOf(".js")!=-1 || archivos[i].indexOf(".php")!=-1){
                String[] arr_aux=archivos[i].split("\\.");
                src=arr_aux[0]+"_src."+arr_aux[1];
            }
            else{
                src=archivos[i];
            }
            arr_archivos_src[i]=src;
        }
        return arr_archivos_src;
    }
    /**
     * Retorna el path de donde se compartiran los archivos
     * @param disco Es el nombre del disco o path de la carpeta donde se encuentran las empresas con los respectivos archivos para compartir
     * @param empresa Nombre de la empresa de donde se compartirán los archivos
     * @param path path actual el cual se obtiene del archivo arrastrado a la lista de minificación
     */
    public static String getPathCompartir(String disco, String empresa, String path){
        String path_compartir;
        try{
            String[] arr_path=path.split(empresa+getSeparador(2));
            path_compartir = disco+getSeparador()+empresa+getSeparador()+arr_path[1];
        }
        catch(Exception e){
            String[] arr_path=path.split(empresa+"/");
            path_compartir = disco+getSeparador()+empresa+getSeparador()+arr_path[1];
        }
        return path_compartir;
    }
    public static String getNombreEmpresaXPath(String path){
        String empresa="";
        if(path.indexOf("flashservices")!=-1){
            if(path.split("flashservices")[1].split(getSeparador(2)).length>1)
                empresa = path.split("flashservices")[1].split(getSeparador(2))[1];
            else{
                try{
                    empresa = path.split("flashservices")[1].split("\\\\")[1];
                }
                catch(Exception e){
                    empresa = path.split("flashservices")[1].split("/")[1];
                }
            }
        }
        else{
            if(getOS().equals(WINDOWS))
                empresa=path.split(":"+getSeparador(2))[1].split(getSeparador(2))[0];//Aqui busca por disco
            else{
                empresa=path.split(getSeparador())[3].split(getSeparador())[0];//En el caso de linux debe saltar dos carpetas /media/server
            }
        }
        return empresa;
    }
    
    public static void abrirURL(String URL){
        try{
            Desktop.getDesktop().browse(new URI(URL));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "No es posible abrir la dirección. Error: "+e.getMessage());
        }
    }
    /**
     * Retorna true si el archivo proviene de un disco (F:, C:, etc) si proviene del 192.168.1.99 retornara false
     */
    public static boolean isDisc(String path){
        return path.indexOf("192.168.1.99")==-1;
    }
    public static boolean verificarFecha(char num){
        if ((num >= '0' && num <='9') || num=='-')
            return true;
        else
            return false;
    }
    public static String fecha_filtro(String anio, String mes, String dia){
        //Fecha fecha=new Fecha();
        if(dia==null || dia.equals("0"))
            dia="";
        if(mes==null || mes.equals("0"))
            mes="";
        if(anio==null || anio.isEmpty() || anio.equals("0"))
            anio="0";//fecha.getFechaHoy().split("-")[0];
        return anio+(!mes.isEmpty()?"-"+mes:"")+(!dia.isEmpty()?"-"+dia:"");
    }
    public static String fecha_filtro(String fecha){
        if(fecha==null || fecha.equals("0") || fecha.isEmpty())
            return fecha_filtro("", "", "");
        if(fecha.split("-").length==1)
            return fecha_filtro(fecha.split("-")[0], "", "");
        else if(fecha.split("-").length==2)
            return fecha_filtro(fecha.split("-")[0], fecha.split("-")[1], "");
        else
            return fecha_filtro(fecha.split("-")[0], fecha.split("-")[1], fecha.split("-")[2]);
    }
    /**
     * Retorna la capa del grupo al que pertenece el archivo que se esta buscando
     * @param nombre_archivo String
     * @return String retorna null si el archivo no tiene especificado a que capa pertenece
     */
    public static String getCapaXNombre_archivo(String nombre_archivo){
        for(String capa : capas){
            if(nombre_archivo.indexOf(capa)!=-1)
                return capa;
        }
        return null;
    }
    
    public static String renameFile(String path_archivo){
        String path_real=path_archivo;
        
        String []arr_path_nuevo=path_archivo.split(getSeparador()/*"\\\\"*/);
        String []arr_archivo=arr_path_nuevo[arr_path_nuevo.length-1].split("\\.");
        
        String path_aux="";
        for(int i=0;i<arr_path_nuevo.length-1;i++){
            path_aux+=arr_path_nuevo[i]+getSeparador()/*"\\"*/;
        }
        String path_nuevo=path_aux+arr_archivo[0]+"_src."+arr_archivo[1];
        
        System.out.println("Archivo fuente: "+path_real);
        System.out.println("Archivo source: "+path_nuevo);
        
        File file=new File(path_real);
        File file_nuevo=new File(path_nuevo);
        if(file.renameTo(file_nuevo))
            System.out.println("Archivo renombrado");
        else
            JOptionPane.showMessageDialog(null, "No fué posible renombrar el archivo");
        return path_nuevo;
    }
    
    public static boolean existePath(String path){
        if(path.length()>0)
        {
            File folder=new File(path);
            if(folder.exists())
            {
                return true;
            }
            else
                return false;
        }
        return false;
        
    }
}