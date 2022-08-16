package com.sparta.miniproject_movie_study_01.controller;




import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.CgvInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//@Controller
@Slf4j
@RequiredArgsConstructor
@RestController
public class CGVInfoController {

    //private static Logger logger = LoggerFactory.getLogger(CGVInfoController.class);

    private final CgvInfoService cgvInfoService;

    //api 수정함 영화 검색.
    //@RequestMapping(value = "/api/movie/search", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    @RequestMapping(value = "/api/movie/search", method = {RequestMethod.GET})
    public ResponseDto<?> movieSearch(@RequestParam String query) {

        log.info(query);

        //query = URLDecoder.decode(query, "utf-8");

        String result = cgvInfoService.movieSearch(query);

        log.info(result);

        return cgvInfoService.moiveItem(result);

    }


    // 프론트 분들한테 날짜 형식을 받아올때 20220813으로 현재날짜 받아오기.
    @RequestMapping(value = "/api/movie/nowrank", method = {RequestMethod.GET})
    public ResponseDto<?> movieNowRank(@RequestParam String nowDate) throws ParseException {

        log.info(nowDate);

        // 1. 날짜 표시 format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        // 2. 오늘날짜 Data 클래스로 구하기(기준날짜가 오늘이 아니면 생략가능)
        Date today = new Date();

        // 3. 오늘날짜 format에 맞춰서 String 으로 변경(기준날짜가 오늘이 아니면 생략가능)
        String date =  formatter.format(today);

        // 4. 기준이 되는 날짜(format에 맞춘)
        Date setDate = formatter.parse(date);

        // 5. 한국 날짜 기준 Calendar 클래스 선언
        Calendar cal = new GregorianCalendar(Locale.KOREA);

        // 6. 선언된 Calendar 클래스에 기준 날짜 설정
        cal.setTime(setDate);

        // 7. 하루전으로 날짜 설정
        cal.add(Calendar.DATE, -1);

        // 8. 하루전으로 설정된 날짜를 설정된 format으로 String 타입 변경
        //String y_date = formatter.format(cal.getTime());

        nowDate = formatter.format(cal.getTime());

        String result = cgvInfoService.movieNowRank(nowDate);

        return cgvInfoService.moiveNowRankItem(result);
    }


    //api 수정함. 상영 예정인 영화.  --> post인가???
    //@RequestMapping(value = "/api/movie/upcomming", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    @RequestMapping(value = "/api/movie/upcomming", method = {RequestMethod.GET})
    public ResponseDto<?> movieUpComming() {

        return cgvInfoService.movieUpComming();


    }

    // api 수정함. 상영 중인 영화
    //@RequestMapping(value = "/api/movie/now", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    @RequestMapping(value = "/api/movie/now", method = {RequestMethod.GET})
    public ResponseDto<?> movieNow() {

        return cgvInfoService.movieNow();

    }


}
