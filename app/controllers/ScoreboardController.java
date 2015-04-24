package controllers;

import models.entity.ScoreboardEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Created by Florian on 16/04/2015.
 */
public class ScoreboardController extends Controller{

    public static Result findById(int id){
        return ok(Json.toJson(ScoreboardEntity.find.byId(id)));
    }

    //    @Url(value="/player",method = HttpMethod.GET)
    public static Result findAll(){
        return ok(Json.toJson(ScoreboardEntity.find.all()));
    }

    public static Result createScoreboard(){
        Json.fromJson(request().body().asJson(), ScoreboardEntity.class).save();
        return ok();
    }

    public static Result updateScoreboard(){
        Json.fromJson(request().body().asJson(), ScoreboardEntity.class).update();
        return ok();
    }

    public static Result deleteScoreboard(){
        ScoreboardEntity.find.byId(request().body().asJson().get("id").asInt()).delete();
        return ok();
    }
}
