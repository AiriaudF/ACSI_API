package controllers;

import controllers.routes;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.book;
import views.html.creategame;
import views.html.home;

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

    public static Result book() {
        return ok(book.render());
    }

    public static Result home() {
        return ok(home.render());
    }
    public static Result creategame() {
        return ok(creategame.render());
    }



}
