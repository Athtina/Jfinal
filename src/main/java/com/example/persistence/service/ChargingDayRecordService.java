package com.example.persistence.service;

import com.example.persistence.mapper.ChargingDayRecordMapper;
import com.example.persistence.model.ChargingDayRecord;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/8 14:53
 */
@Service
public class ChargingDayRecordService {


  @Autowired
  private ChargingDayRecordMapper cdrMapper;

  public List<ChargingDayRecord> getData(){
    List<ChargingDayRecord> cdrList=cdrMapper.selectAll();
    return cdrList;
  }
}
