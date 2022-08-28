package com.example.springboottest.controller;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.example.springboottest.util.HttpCli;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.controller
 * @Author: lujie
 * @Date: 2021/4/29 17:50
 * @Description:
 */
@RestController
public class Demo {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/SSOlogin1")
    private ModelAndView pcSSOlogin(HttpServletRequest request, RedirectAttributes redirectattributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("http://nbds.i.gopherasset.com/seeyon/kk/fundAllocation.do?method=fundAllocation2"));
        JSONObject jsonObject = JSONObject.parseObject("{\"msg\":\"主流程发送成功\",success\":\"true\",\"loginName\":Ilj10953\",\"summaryld\":\"0\",\"applicant\":\"p219yfrpzq31qKSCdp3zGw==\"}");
        redirectattributes.addAttribute(jsonObject.toString());
        return modelAndView;
    }

    @RequestMapping("/getCode")
    private void getCode(HttpServletResponse response) throws Exception {
        response.sendRedirect("http://localhost:8080/ssoLogin?code=0.AVUAn90PD7wB40ayR8xyDqWpIEGWICjymQlBvJggeBvn6-iIALw.AQABAAIAAAD--DLA3VO7QrddgJg7WevryZHMNgs3YoWgj6gYForaLoW19ViRWZp6Z2aA-I0PtWtsTlWaRqvaMHfHwj6VOj1CsY47v8Yjk6Hx8CjkRWwUZaYd7Adm_f0vBi6uE_H8K2NtSU7gFbUT-mWHGZEknlceyxhr1S6kpK7jraQU3PL_5ZDg5ksUtrbuAUA6d-1XozyodJVEQ9BbxFl6DhptnYm2HyyNxV6ME4opsB6vuNqe0AZA4ldJGez1IyJUt9F8fk2QIUYkr96km2fcnB1cfmn1XYWqs-06XwbAo9g121P9L4vKSDbnUOCvTbc7qPzcKMwKbC0RDWpSzg40OtKV4dIopI1Bp7VMjtTt2jmcZ0rZ7UtJUsT4X1YaPO3gUGUEG-_MFGuEyJX1nSLEYvfZYAQhhSOC1ywL85CkrBVVLegjB6aVNlVTObEQmEncRtanC4DAXADbOwqEPQdrfPnoeBDijhJJby-6uZDOPABKciAmxxZxokxbbaNsQFfEFWVx5BNN5sYgWWT8MdfKyyEKZd0r7vZupasQKLc-xejjQC0WAYH4VXdGR0pAxXM1e9m8JDbuo7Rq10k_b-ahyVtHdpRsZdIpUmiEkF-idqqfTqybdXYi4JP_djf1lSHjajPnOJjchzawPEN6MA-SpfyewM38a-Sc426CHDD2AXjzo8EittLIaV79zKL7HkazRquasb6ho38AtwmglwWNYjJDM4cinFcIWDKmljaRHYaAJXswSHRBL5BsU_KfcNkMVE55p8-A1XpOA7-BK8o5U54_RfbKH-b403SRkbKx-ditgfRefyCM_XD10HyLL17YIkH9GLS4-rSQPu6JLrWvS_HUSEHpzciUL_-v71sN0imOz0I6QfoVdW-lCzJ7m9lRfEeo2gggAA&state=23452124343&session_state=8bbddcd8-6561-490e-a8b7-deb7738d1961");
    }

    @RequestMapping("/ssoLogin")
    private String test(HttpServletRequest request, RedirectAttributes redirectattributes) throws Exception {
        return request.getParameter("code");
    }
}
