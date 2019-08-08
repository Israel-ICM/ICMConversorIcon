
package ConversorImage;

import Tools.Tools;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author josue
 */
public class Imagen {
    private String path = "";
    private String path_image_view = "";
    private int width = 0;
    private int height = 0;
    public Imagen(String path, int width, int height){
        this.path = path;
        this.width = width;
        this.height = height;
    }
    public Imagen(String path, String path_image_view, int width, int height){
        this.path = path;
        this.path_image_view = path_image_view;
        this.width = width;
        this.height = height;
    }
    public String getNombre(){
        String[] arr_path = path.split(Tools.getSeparador(2));
        String name = arr_path[arr_path.length-1];
        return name.split("\\.")[0];
    }
    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return path;
    }
    public String getPathImageView(){
        return path_image_view.isEmpty()?path:path_image_view;
    }
    public Icon getImageIcon(){
        return new ImageIcon(getPathImageView());
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}
