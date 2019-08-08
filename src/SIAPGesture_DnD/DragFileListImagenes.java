/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SIAPGesture_DnD;

import ConversorImage.Imagen;
import Tools.Tools;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JList;

/**
 *
 * @author josue
 */
public class DragFileListImagenes implements DragSourceListener, DragGestureListener{
    FileSelection transferable;
    JList list;
    DragSource ds;
    public DragFileListImagenes(JList list){
        this.list = list;
        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_COPY, this);
    }
    @Override
    public void dragEnter(DragSourceDragEvent dsde) {}

    @Override
    public void dragOver(DragSourceDragEvent dsde) {}

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {}

    @Override
    public void dragExit(DragSourceEvent dse) {}

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        System.out.print("Drag Drop End: ");
        if (dsde.getDropSuccess()) {
            System.out.println("Succeeded");
        }
        else {
            System.out.println("Failed");
        }
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        System.out.println("Drag Gesture Recognized!");
        //transferable = new StringSelection(jList1.getSelectedValue().toString());
        
        File[] files = new File[list.getSelectedIndices().length];
        for(int i = 0;i < list.getSelectedIndices().length; i++){
            Imagen c_imagen = (Imagen) list.getModel().getElementAt(list.getSelectedIndices()[i]);
            files[i] = new File(c_imagen.getPath());
        }
        
        transferable = new FileSelection(files);
        //ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
        ImageIcon image = new Tools().getImageExtension("png");
        ds.startDrag(dge, DragSource.DefaultCopyDrop, image.getImage(), new Point(50, 50), transferable, this);
    }
    public class FileSelection extends Vector implements Transferable
    {
        final static int FILE = 0;
        final static int STRING = 1;
        final static int PLAIN = 2;
        DataFlavor flavors[] = {DataFlavor.javaFileListFlavor,
                                /*DataFlavor.stringFlavor,
                                DataFlavor.plainTextFlavor*/};
        public FileSelection(File[] files)
        {
            for(File file : files)
                addElement(file);
        }
        /* Returns the array of flavors in which it can provide the data. */
        public synchronized DataFlavor[] getTransferDataFlavors() {
    	return flavors;
        }
        /* Returns whether the requested flavor is supported by this object. */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            boolean b  = false;
            b |=flavor.equals(flavors[FILE]);
            /*b |= flavor.equals(flavors[STRING]);
            b |= flavor.equals(flavors[PLAIN]);*/
        	return (b);
        }
        /**
         * If the data was requested in the "java.lang.String" flavor,
         * return the String representing the selection.
         */
        public synchronized Object getTransferData(DataFlavor flavor)
    			throws UnsupportedFlavorException, IOException {
    	if (flavor.equals(flavors[FILE])) {
    	    return this;
    	} else if (flavor.equals(flavors[PLAIN])) {
    	    return new StringReader(((File)elementAt(0)).getAbsolutePath());
    	} else if (flavor.equals(flavors[STRING])) {
    	    return((File)elementAt(0)).getAbsolutePath();
    	} else {
    	    throw new UnsupportedFlavorException(flavor);
    	}
        }
    }
}
