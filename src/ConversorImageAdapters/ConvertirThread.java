package ConversorImageAdapters;

import ConversorImage.Imagen;
import Tools.Tools;
import icmconversoricon.ConversorIconGui;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import net.sf.image4j.codec.ico.ICOEncoder;

/**
 *
 * @author josue
 */
public class ConvertirThread extends Thread{
    private String path_image = "";
    private CustomListImages list_model;
    private JPanel progreso;
    public ConvertirThread(String path_image, CustomListImages list_model, JPanel progreso){
        this.path_image = path_image;
        this.list_model = list_model;
        this.progreso = progreso;
    }
    @Override
    public void run(){
        progreso.setVisible(true);
        convertir();
        progreso.setVisible(false);
    }
    private void convertir(){
        try {
            /*BufferedImage bi = ImageIO.read(new File("C:\\Users\\josue\\Desktop\\icono-php.png"));
            ICOEncoder.write(redimensionar(bi, 50, 50), new File("C:\\Users\\josue\\Desktop\\my-icono.ico"));*/
            Imagen c_image = new Imagen(path_image, 0, 0);
            BufferedImage bi = ImageIO.read(new File(c_image.getPath()));
            int cant = 5;
            int ini_escala = 16;
            for(int i = 0;i < cant;i++){
                //String newPath = "C:\\Users\\josue\\Desktop\\"+c_image.getNombre()+"_"+escala+"x"+escala+".ico";
                String newPath = Tools.PATH_UBICACION_SOFTWARE+Tools.getSeparador()+"Img_ico"+Tools.getSeparador()+c_image.getNombre()+"_"+ini_escala+"x"+ini_escala+".ico";
                ICOEncoder.write(redimensionar(bi, ini_escala, ini_escala), new File(newPath));
                Imagen image = new Imagen(newPath, c_image.getPath(), ini_escala, ini_escala);
                list_model.add(image);
                ini_escala *= 2;
            }
        } catch (IOException ex) {
            Logger.getLogger(ConversorIconGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private BufferedImage redimensionar(BufferedImage bufferedImage, int newW, int newH){
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
}
