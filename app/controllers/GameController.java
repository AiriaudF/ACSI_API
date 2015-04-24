package controllers;

import models.entity.GameEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Florian on 16/04/2015.
 */
public class GameController extends Controller {

    public static Result findById(int id){
        return ok(Json.toJson(GameEntity.find.byId(id)));
    }

    public static Result findAll(){
        return ok(Json.toJson(GameEntity.find.all()));
    }

    public static Result createGame(){
        Json.fromJson(request().body().asJson(), GameEntity.class).save();
        return ok();
    }

    public static Result updateGame(){
        Json.fromJson(request().body().asJson(), GameEntity.class).update();
        return ok();
    }

    public static Result deleteGame(){
        GameEntity.find.byId(request().body().asJson().get("id").asInt()).delete();
        return ok();
    }
}
