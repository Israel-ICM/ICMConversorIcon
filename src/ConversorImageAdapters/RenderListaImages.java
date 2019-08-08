package ConversorImageAdapters;

import ConversorImage.Imagen;
import com.siap.components.PanelImage;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author josue
 */
public class RenderListaImages implements ListCellRenderer{
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Imagen c_imagen=(Imagen) value;
        
        JPanel panel=new JPanel();
        JPanel panel2=new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel txtTamanio=new JLabel();
        PanelImage image = new PanelImage();
        
        image.setSize(new Dimension(c_imagen.getWidth(), c_imagen.getHeight()));
        image.setMinimumSize(new Dimension(c_imagen.getWidth(), c_imagen.getHeight()));
        image.setPreferredSize(new Dimension(c_imagen.getWidth(), c_imagen.getHeight()));
        image.setIcon(c_imagen.getImageIcon());
        
        txtTamanio.setText(c_imagen.getWidth()+" x "+c_imagen.getHeight());
        txtTamanio.setFont(new Font("Century Gothic"/*"Microsoft JhengHei"*/, Font.BOLD, 12));
                
        panel2.add(image);
        panel.add(panel2);
        panel.add(txtTamanio);
        
        panel.setToolTipText("<html><div style='padding: 5px; font-size: 10px;'>"+c_imagen.getPath()+"</div></html>");
        return panel;
    }
}