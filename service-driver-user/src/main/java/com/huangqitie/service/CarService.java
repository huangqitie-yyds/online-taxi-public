package com.huangqitie.service;

import com.huangqitie.internalcommon.dto.Car;
import com.huangqitie.internalcommon.dto.ResponseResult;
import com.huangqitie.internalcommon.response.TerminalResponse;
import com.huangqitie.internalcommon.response.TrackResponse;
import com.huangqitie.mapper.CarMapper;
import com.huangqitie.remote.ServiceMapClient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        // 获得此车辆的终端id：tid
        ResponseResult<TerminalResponse> responseResult =
                serviceMapClient.addTerminal(car.getVehicleNo(), car.getId() + "");
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        // 获得此车辆的轨迹id：trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);
        carMapper.updateById(car);
        return ResponseResult.success("");
    }

    public ResponseResult<Car> getCarById(Long id){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        List<Car> cars = carMapper.selectByMap(map);
        return ResponseResult.success(cars.get(0));

    }
}
