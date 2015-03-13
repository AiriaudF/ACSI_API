package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.entity.GameEntity;
import play.libs.Json;
import play.mvc.Result;
import views.html.index;

import static play.mvc.Results.ok;

/**
 * Created by Thomas on 12/03/2015.
 */
public class PlayerRoute {

    public static Result index(){
        GameEntity p = GameEntity.find.byId(1);
        JsonNode pJson = Json.toJson(p);
        return ok(index.render(Json.stringify(pJson)));
    }
}
