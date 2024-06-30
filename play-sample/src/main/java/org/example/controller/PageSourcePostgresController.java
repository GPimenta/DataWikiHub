package org.example.controller;

import play.mvc.Controller;
import play.mvc.Result;

public class PageSourcePostgresController extends Controller {

    public Result health() {
        return ok();
    }
}
