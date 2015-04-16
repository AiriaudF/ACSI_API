package controllers;

import controllers.routes;
import models.Url;
import play.mvc.Controller;
import play.mvc.Result;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 24/03/2015.
 */
public class Home extends Controller {
    public static Result index(){
        List<Method> list = new ArrayList<>();
        for(Field f:routes.class.getFields()){
            try {
                Class objClass = Class.forName("controllers."+f.getName());
                for(Method m :objClass.getDeclaredMethods()){
                    if(m.getReturnType().equals(Result.class)){
                        list.add(m);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ok(views.html.index.render(list));
    }
}
