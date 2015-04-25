package controllers;

import models.entity.PlayerEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;




/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerController extends Controller {

    public static Result findById(int id){
        return ok(Json.toJson(PlayerEntity.find.byId(id)));
    }

    public static Result findAll(){
        return ok(Json.toJson(PlayerEntity.find.all()));
    }

    public static Result createPlayer(){
        Json.fromJson(request().body().asJson(), PlayerEntity.class).save();
        return ok();
    }

    public static Result updatePlayer(){
        Json.fromJson(request().body().asJson(), PlayerEntity.class).update();
        return ok();
    }

    public static Result deletePlayer(){
        PlayerEntity.find.byId(request().body().asJson().get("id").asInt()).delete();
        return ok();
    }
}
