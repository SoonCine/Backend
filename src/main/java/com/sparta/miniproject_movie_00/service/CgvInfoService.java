package com.sparta.miniproject_movie_00.service;

import com.google.gson.Gson;

import com.sparta.miniproject_movie_00.controller.response.CGVInfoDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CgvInfoService {

    private static Logger logger = LoggerFactory.getLogger(CgvInfoService.class);

    @Transactional
    public String movieUpComming() {

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";

        // #contents > div.wrap-movie-chart > div.sect-movie-chart > ol:nth-child(2) > li:nth-child(1) > div.box-image
        //#contents > div.wrap-movie-chart > div.sect-movie-chart > ol:nth-child(2) > li:nth-child(1) > div.box-contents

        try {

            doc = Jsoup.connect("http://www.cgv.co.kr/movies/pre-movies.aspx").get();
            /* Elements */
            Elements ranks = doc.select(".rank");
            /* logger.info("rank" + ranks); */

            Elements imgs = doc.select(".thumb-image > img");
            /* logger.info("imgs" + imgs); */

            Elements movieAges = doc.select(".ico-grade");
            /* logger.info("ico-grade" + movieAges); */

            Elements movieTitles = doc.select("div.box-contents strong.title");
            /* logger.info("titles" + movieTitles); */

            Elements movieRates = doc.select(".percent span");
            /* logger.info("percents" + movieRates); */

            Elements movieOpenDates = doc.select(".txt-info strong");
            /* logger.info("percents" + movieOpenDates); */

            Elements likes = doc.select(".count strong>i");
            /* logger.info("counts" + likes); */
            List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

            for(int i = 3; i < movieTitles.size(); i++) {

                //String rank = ranks.get(i).text();
                String img = imgs.get(i).attr("src");
                String movieAge = movieAges.get(i).text();
                String movieTitle = movieTitles.get(i).text();
                String movieRate = movieRates.get(i).text();
                String movieOpenDate = movieOpenDates.get(i).text();
                //String like = likes.get(i).text();
                int seq = i;
                //CGVInfoDto cgvInfoDto = new CGVInfoDto(rank, img, movieAge, movieTitle, movieRate, movieOpenDate, like, seq);
                CGVInfoDto cgvInfoDto = new CGVInfoDto(img, movieAge, movieTitle, movieRate, movieOpenDate, seq);

                logger.info(cgvInfoDto.toString());
                list.add(cgvInfoDto);
            }
            gson = new Gson().toJson(list);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return gson;

    }

    @Transactional
    public String movieNow() {

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";

        // #contents > div.wrap-movie-chart > div.sect-movie-chart > ol:nth-child(2) > li:nth-child(1) > div.box-image
        //#contents > div.wrap-movie-chart > div.sect-movie-chart > ol:nth-child(2) > li:nth-child(1) > div.box-contents

        try {

            doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();
            /* Elements */
            Elements ranks = doc.select(".rank");
            /* logger.info("rank" + ranks); */

            Elements imgs = doc.select(".thumb-image > img");
            /* logger.info("imgs" + imgs); */

            Elements movieAges = doc.select(".ico-grade");
            /* logger.info("ico-grade" + movieAges); */

            Elements movieTitles = doc.select("div.box-contents strong.title");
            /* logger.info("titles" + movieTitles); */

            Elements movieRates = doc.select(".percent span");
            /* logger.info("percents" + movieRates); */

            Elements movieOpenDates = doc.select(".txt-info strong");
            /* logger.info("percents" + movieOpenDates); */

            Elements likes = doc.select("div.egg-gage > .percent");
            /* logger.info("counts" + likes); */
            List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

            for(int i = 0; i < movieTitles.size(); i++) {

                //String rank = ranks.get(i).text();
                String img = imgs.get(i).attr("src");
                String movieAge = movieAges.get(i).text();
                String movieTitle = movieTitles.get(i).text();
                String movieRate = movieRates.get(i).text();
                String movieOpenDate = movieOpenDates.get(i).text();
                String like = likes.get(i).text();
                int seq = i;
                //CGVInfoDto cgvInfoDto = new CGVInfoDto(rank, img, movieAge, movieTitle, movieRate, movieOpenDate, like, seq);
                CGVInfoDto cgvInfoDto = new CGVInfoDto(img, movieAge, movieTitle, movieRate, movieOpenDate,like, seq);

                logger.info(cgvInfoDto.toString());
                list.add(cgvInfoDto);
            }
            gson = new Gson().toJson(list);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gson;

    }
}
