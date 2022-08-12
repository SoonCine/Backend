package com.sparta.miniproject_movie_00.controller;


import com.sparta.miniproject_movie_00.service.CgvInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RequiredArgsConstructor
@RestController
public class CGVInfoController {

    //private static Logger logger = LoggerFactory.getLogger(CGVInfoController.class);

    private final CgvInfoService cgvInfoService;

    //@ResponseBody
    @RequestMapping(value = "/api/movieupcomming", method = {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
    public String movieUpComming() {

       return cgvInfoService.movieUpComming();


    }

    @RequestMapping(value = "/api/movie/movienow", method = {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
    public String movieNow() {

        return cgvInfoService.movieNow();


    }


}
