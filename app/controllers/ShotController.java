package controllers;

import models.entity.ShotEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Created by Florian on 16/04/2015.
 */
public class ShotController extends Controller{

    public static Result findById(int id){
        return ok(Json.toJson(ShotEntity.find.byId(id)));
    }

    //    @Url(value="/player",method = HttpMethod.GET)
    public static Result findAll(){
        return ok(Json.toJson(ShotEntity.find.all()));
    }

    public static Result createShot(){
        Json.fromJson(request().body().asJson(), ShotEntity.class).save();
        return ok();
    }

    public static Result updateShot(){
        Json.fromJson(request().body().asJson(), ShotEntity.class).update();
        return ok();
    }

    public static Result deleteShot(){
        ShotEntity.find.byId(request().body().asJson().get("id").asInt()).delete();
        return ok();
    }
}
