package com.tiscon.service;

import com.tiscon.dao.EstimateDao;
import com.tiscon.dto.UserOrderDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstimateServiceTest {

    @Autowired
    private EstimateService sut;

    @MockBean
    private EstimateDao estimateDao;

    @Before
    public void prepareEstimateDaoForStaticData() {

        Mockito.when(estimateDao.getBoxPerPackage(1)).thenReturn(1);
        Mockito.when(estimateDao.getBoxPerPackage(2)).thenReturn(20);
        Mockito.when(estimateDao.getBoxPerPackage(3)).thenReturn(15);
        Mockito.when(estimateDao.getBoxPerPackage(4)).thenReturn(10);

        Mockito.when(estimateDao.getPricePerOptionalService(1)).thenReturn(3000);
    }

    /**
     * 全部の項目が指定された場合
     */
    @Test
    public void testAllItemsIsSpecified() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("10");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(90)).thenReturn(50000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(63000);
    }

    /**
     * 洗濯機の設置オプションを外した場合
     */
    @Test
    public void testNoWashingMachineInstallation() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("10");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(false);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(90)).thenReturn(50000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(60000);
    }

    /**
     * 箱が80個の場合
     */
    @Test
    public void test80boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("0");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(80)).thenReturn(30000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(43000);
    }

    /**
     * 箱が81個の場合
     */
    @Test
    public void test81boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("1");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(81)).thenReturn(50000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(63000);
    }

    /**
     * 箱が200個の場合
     */
    @Test
    public void test200boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("120");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(63000);
    }

    /**
     * 箱が201個の場合
     */
    @Test
    public void test201boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("121");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);
        Mockito.when(estimateDao.getPricePerTruck(1)).thenReturn(30000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(93000);
    }

    /**
     * 箱が400個の場合
     */
    @Test
    public void test400boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("320");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(113000);
    }

    /**
     * 箱が401個の場合
     */
    @Test
    public void test401boxes() {

        final UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setOldPrefectureId("1");
        userOrderDto.setNewPrefectureId("9");
        userOrderDto.setBox("321");
        userOrderDto.setBed("2");
        userOrderDto.setBicycle("2");
        userOrderDto.setWashingMachine("1");
        userOrderDto.setWashingMachineInstallation(true);

        Mockito.when(estimateDao.getDistance("1", "9")).thenReturn(100.123d);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);
        Mockito.when(estimateDao.getPricePerTruck(200)).thenReturn(50000);
        Mockito.when(estimateDao.getPricePerTruck(1)).thenReturn(30000);

        Assertions.assertThat(sut.getPrice(userOrderDto)).isEqualTo(143000);
    }
}
