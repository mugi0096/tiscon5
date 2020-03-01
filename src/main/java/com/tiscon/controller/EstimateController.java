package com.tiscon.controller;

import com.tiscon.dao.EstimateDao;
import com.tiscon.dto.UserOrderDto;
import com.tiscon.form.PrivateOrderForm;
import com.tiscon.form.UserOrderForm;
import com.tiscon.form.EstimateOrderForm;
import com.tiscon.service.EstimateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 引越し見積もりのコントローラークラス。
 *
 * @author Oikawa Yumi
 */
@Controller
@SessionAttributes(types = {EstimateOrderForm.class})
public class EstimateController {

    private final EstimateDao estimateDAO;

    private final EstimateService estimateService;

    /**
     * コンストラクタ
     *
     * @param estimateDAO EstimateDaoクラス
     * @param estimateService EstimateServiceクラス
     */
    public EstimateController(EstimateDao estimateDAO, EstimateService estimateService) {
        this.estimateDAO = estimateDAO;
        this.estimateService = estimateService;
    }

    @GetMapping("")
    String index(Model model) {
        return "top";
    }

    /**
     * 入力画面に遷移する。
     *
     * @param model 遷移先に連携するデータ
     * @return 遷移先
     */
    @GetMapping("input")
    String input(Model model) {
        if (!model.containsAttribute("userOrderForm")) {
            model.addAttribute("estimateOrderForm", new EstimateOrderForm());
        }

        model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
        return "inputEstimate";
    }

    /**
     * TOP画面に戻る。
     *
     * @param model 遷移先に連携するデータ
     * @return 遷移先
     */
    @PostMapping(value = "submitEstimate", params = "backToTop")
    String backToTop(Model model) {
        return "top";
    }


    /**
     * 見積もり確認画面に遷移する。
     *
     * @param
     */
    @PostMapping(value = "submitEstimate", params = "confirm")
    String confirmEstimate(@Validated EstimateOrderForm estimateOrderForm, BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
            model.addAttribute("estimateOrderForm", estimateOrderForm);
            return "inputEstimate";
        }

        UserOrderDto dto = new UserOrderDto();
        BeanUtils.copyProperties(estimateOrderForm, dto);
        Integer price = estimateService.getPrice(dto);

        model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
        model.addAttribute("estimateOrderForm", estimateOrderForm);
        model.addAttribute("price", price);
        return "confirmEstimate";
    }

    /**
     * 見積もり情報入力画面に戻る
     *
     *
     */
    @PostMapping(value = "resultEstimate", params = "backToInput")
    String backToInputEstimate(EstimateOrderForm estimateOrderForm,  Model model) {

        model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
        model.addAttribute("estimateOrderForm", estimateOrderForm);
        return "inputEstimate";
    }

    /**
     * 個人情報入力画面に遷移
     *
     */
    @PostMapping(value = "resultEstimate", params = "inputPrivate")
    String inputPrivate(Model model) {
        if (!model.containsAttribute("privateOrderForm")) {
            model.addAttribute("privateOrderForm", new PrivateOrderForm());
        }
        return "inputPrivate";
    }

    /**
     * 見積もり情報確認画面に戻る
     *
     *
     */
    @PostMapping(value = "submitPrivate", params  ="backToConfirmEstimate")
    String backToConfirmEstimate(EstimateOrderForm estimateOrderForm, Model model) {
        model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
        model.addAttribute("estimateOrderForm", estimateOrderForm);
        return "confirmEstimate";
    }

    /**
     * 総合確認画面に遷移
     *
     *
     */
    @PostMapping(value  ="submitPrivate", params = "confirmAll")
    public String confirmAll(@ModelAttribute("estimateOrderForm") EstimateOrderForm estimateOrderForm,
                       @Validated PrivateOrderForm privateOrderForm , BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("privateOrderForm", privateOrderForm);
            return "inputPrivate";
        }

        UserOrderForm userOrderForm = mergeForm(estimateOrderForm ,privateOrderForm);

        UserOrderDto dto = new UserOrderDto();
        BeanUtils.copyProperties(userOrderForm, dto);
        Integer price = estimateService.getPrice(dto);

        model.addAttribute("prefectures", estimateDAO.getAllPrefectures());
        model.addAttribute("userOrderForm", userOrderForm);
        model.addAttribute("price", price);

        return "confirmAll";
    }

    private UserOrderForm mergeForm(EstimateOrderForm estimateOrderForm, PrivateOrderForm privateOrderForm) {
        UserOrderForm userOrderForm = new UserOrderForm();
        BeanUtils.copyProperties(estimateOrderForm, userOrderForm);
        BeanUtils.copyProperties(privateOrderForm, userOrderForm);
        return userOrderForm;
    }

    /**
     * 個人情報入力画面に戻る
     *
     *
     */
    @PostMapping(value = "resultAll", params = "backToInputPrivate")
    public String backToInputPrivate(PrivateOrderForm privateOrderForm, Model model) {
        model.addAttribute("privateOrderForm", privateOrderForm);
        return "inputPrivate";
    }

    /**
     * 申込画面に遷移
     *
     *
     */
    @PostMapping(value = "resultAll", params = "order")
    public String order(@ModelAttribute("estimateOrderForm") EstimateOrderForm estimateOrderForm,
                        @ModelAttribute("privateOrderForm") PrivateOrderForm privateOrderForm) {
        UserOrderForm userOrderForm = mergeForm(estimateOrderForm, privateOrderForm);

        UserOrderDto dto = new UserOrderDto();
        BeanUtils.copyProperties(userOrderForm, dto);
        estimateService.registerOrder(dto);

        return "complete";
    }
}
