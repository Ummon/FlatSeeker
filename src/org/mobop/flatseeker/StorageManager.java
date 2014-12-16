package org.mobop.flatseeker;

import android.content.Context;

import org.mobop.flatseeker.model.FlatFinder;
import org.mobop.flatseeker.model.ImmoScout24Finder;
import org.mobop.flatseeker.model.ImmoStreetFinder;
import org.mobop.flatseeker.model.Model;
import org.mobop.flatseeker.model.StubFinder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageManager{
    public static final String FILE_MODEL = "flatseeker_storage_model";
    public static final String FILE_ACTUAL = "flatseeker_storage_actual";

    public static Model loadModel(Context context){
        Model model = (Model)loadObject(FILE_MODEL, context);

        FlatFinder finder = new StubFinder();//new ImmoScout24Finder();

        if (model != null) {
            model.setFinder(finder);
            return model;
        }

        return new Model(finder);
    }

//    public static ActualSearch loadActualSearch(){
//
//    }

    public static void saveModel(Model model,Context context){
        saveObject(FILE_MODEL, model, context);
    }

    public static void saveObject(String path,Object object,Context context){
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Object loadObject(String path, Context context){
        FileInputStream fis;
        ObjectInputStream ois;
        Object object = null;
        try {
            fis = context.openFileInput(path);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
