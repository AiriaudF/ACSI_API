package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Thomas on 24/03/2015.
 */
public class Home extends Controller {
    public static Result index(){
        return ok();
    }
}
