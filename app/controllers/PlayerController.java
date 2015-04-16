package controllers;

import models.HttpMethod;
import models.Parameter;
import models.Url;
import models.entity.PlayerEntity;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;




/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerController extends Controller {

//    @Url(value="/player",method= HttpMethod.GET,
//            parameters={@Parameter(type="int",name="id")}    )
    public static Result findById(int id){
        return ok(Json.toJson(PlayerEntity.find.byId(id)));
    }

//    @Url(value="/player",method = HttpMethod.GET)
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
