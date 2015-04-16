package controllers;

import models.entity.GameEntity;
import models.entity.PlayerEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Thomas on 14/04/2015.
 */
public class GameController extends Controller {

    public static Result findById(int id){
        return ok(Json.toJson(GameEntity.find.byId(id)));
    }
}
