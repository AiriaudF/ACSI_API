package controllers;

import models.entity.PlayerEntity;
import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerRoute {

    public static Result index(){
        PlayerEntity p = PlayerEntity.find.byId(1);
        return ok(Json.toJson(p));
    }
}
