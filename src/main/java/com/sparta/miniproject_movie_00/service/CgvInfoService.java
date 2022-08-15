package com.sparta.miniproject_movie_00.service;

import com.google.gson.Gson;

import com.sparta.miniproject_movie_00.controller.response.*;
import com.sparta.miniproject_movie_00.domain.MovieUpComming;
import com.sparta.miniproject_movie_00.domain.Post;
import com.sparta.miniproject_movie_00.repository.MovieUpComingRepository;
import com.sparta.miniproject_movie_00.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CgvInfoService {

    private final MovieUpComingRepository movieUpComingRepository;
    private final PostRepository postRepository;

    private static Logger logger = LoggerFactory.getLogger(CgvInfoService.class);

    @Transactional
    public String movieSearch(String query) {

        log.info(query);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "R19VlCx5JhDdzA7fADa8");
        headers.add("X-Naver-Client-Secret", "Q0WeoiHdj0");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange("https://openapi.naver.com/v1/search/movie.json?query=" + query , HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;

    }

    @Transactional
    public ResponseDto<?> moiveItem(String result) {

        log.info(result);

        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<CgvSearchResponseDto> itemDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemlist = items.getJSONObject(i);
            CgvSearchResponseDto itemDto = new CgvSearchResponseDto(itemlist);
            itemDtoList.add(itemDto);

        }

        System.out.println(itemDtoList);

        return ResponseDto.success(itemDtoList);

    }

    @Transactional
    public String movieNowRank(String nowDate) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity
                = rest.exchange("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=e6d12eea02933de00f8f46edf3473465&targetDt="+nowDate, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;

    }

    @Transactional
    public ResponseDto<?> moiveNowRankItem(String result) {

        JSONObject rjson = new JSONObject(result);
        System.out.println(" ");
        System.out.println(rjson);

        JSONArray items = rjson.getJSONObject("boxOfficeResult")
                .getJSONArray("dailyBoxOfficeList");

        List<MovieNowRankResponseDto> itemDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemlist = items.getJSONObject(i);

            System.out.println(" ");
            System.out.println(itemlist);
            // 여기까지 정상 출력됨.

            MovieNowRankResponseDto itemDto = new MovieNowRankResponseDto(itemlist);

            itemDtoList.add(itemDto);

        }
        System.out.println(" ");
        System.out.println(itemDtoList);
        return ResponseDto.success(itemDtoList);
    }

    @Transactional
    public ResponseDto<?> movieUpComming() {

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";
        List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

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

            Elements likes = doc.select("div.egg-gage > .percent");
            /* logger.info("counts" + likes); */
            //List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

            for(int i = 3; i < movieTitles.size(); i++) {

                Post a =new Post();
                postRepository.save(a);

                //String rank = ranks.get(i).text();
                String img = imgs.get(i).attr("src");
                String movieAge = movieAges.get(i).text();
                String movieTitle = movieTitles.get(i).text();
                String movieRate = movieRates.get(i).text();
                String movieOpenDate = movieOpenDates.get(i).text();
                String like = likes.get(i).text();
                int seq = i;

                // 영화예정테이블에 해당 이미지 유알엘이 있으면
                // 저장을 안하고
                //
                // 유알엘이 없으면 저장함.


                //CGVInfoDto cgvInfoDto = new CGVInfoDto(rank, img, movieAge, movieTitle, movieRate, movieOpenDate, like, seq);
                CGVInfoDto cgvInfoDto = new CGVInfoDto(img, movieAge, movieTitle, movieRate, movieOpenDate, like);

                movieUpComingRepository.save(MovieUpComming.builder()
                        .img(img)
                        .movieAge(movieAge)
                        .movieTitle(movieTitle)
                        .movieRate(movieRate)
                        .movieOpenDate(movieOpenDate)
                        .post(a)
                        .like(like)
                        .build());
                //logger.info(cgvInfoDto.toString());

                list.add(cgvInfoDto);
            }

            //gson = new Gson().toJson(list);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //return gson;
        return ResponseDto.success(list);


    }

    @Transactional
    public ResponseDto<?> movieNow() {

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";

        List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

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
//            List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

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

        //return gson;
        return ResponseDto.success(list);

    }


}
