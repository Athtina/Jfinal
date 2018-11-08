package com.example.persistence.model;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class ChargingDayRecord {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 库存Id
	 */
	private Integer stockId;
	/**
	 * 库存电量
	 */
	private BigDecimal stockChargingUtility;
	/**
	 * 已用电量
	 */
	private BigDecimal usedChargingUtility;

	/**
	 * 创建人
	 */
	private String createUserId;
	/**
	 * 创建时间
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 更新人
	 */
	private String updateUserId;
	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	
	
	
}