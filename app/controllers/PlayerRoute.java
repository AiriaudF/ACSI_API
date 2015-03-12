package controllers;

import models.entity.PlayerEntity;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerRoute {

    public Result index(){
        PlayerEntity p = new PlayerEntity();
        return ok();
    }
}
