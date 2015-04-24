package controllers;

import models.entity.TurnEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by Florian on 16/04/2015.
 */
public class TurnController extends Controller{

    public static Result findById(int id){
        return ok(Json.toJson(TurnEntity.find.byId(id)));
    }

    //    @Url(value="/player",method = HttpMethod.GET)
    public static Result findAll(){
        return ok(Json.toJson(TurnEntity.find.all()));
    }

    public static Result createTurn(){
        Json.fromJson(request().body().asJson(), TurnEntity.class).save();
        return ok();
    }

    public static Result updateTurn(){
        Json.fromJson(request().body().asJson(), TurnEntity.class).update();
        return ok();
    }

    public static Result deleteTurn(){
        TurnEntity.find.byId(request().body().asJson().get("id").asInt()).delete();
        return ok();
    }
}
