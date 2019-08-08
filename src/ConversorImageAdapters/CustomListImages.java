package ConversorImageAdapters;

import ConversorImage.Imagen;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author josue
 */
public class CustomListImages extends AbstractListModel{
    private ArrayList<Imagen> list_imagenes=new ArrayList<>();
    @Override
    public int getSize() {
        return list_imagenes.size();
    }

    @Override
    public Object getElementAt(int index) {
        Imagen imagen=list_imagenes.get(index);
        //return c_compartir_model.getUsuario();
        return imagen;
    }
    
    public void add(Imagen imagen){
        list_imagenes.add(imagen);
        this.fireIntervalAdded(this, getSize(), getSize()+1);
    }
    
    public void remove(int index){
        list_imagenes.remove(index);
        this.fireIntervalRemoved(index, getSize(), getSize()+1);
    }
    
    public void removeAllElements(){
        int index1 = list_imagenes.size()-1;
        list_imagenes.removeAll(list_imagenes);
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }
    
    public Imagen getItem(int index){
        return list_imagenes.get(index);
    }
    
    public void dataProvider(Imagen[] arr_imagen){
        for(Imagen imagen : arr_imagen){
            this.add(imagen);
        }
    }
}