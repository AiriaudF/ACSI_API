package controllers;

import models.entity.PlayerEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerController extends Controller {

    public static Result findById(Integer id){
        return ok(Json.toJson(PlayerEntity.find.byId(id)));
    }
}
