package com.thtf.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-12-21 下午04:51:37 类说明
 */
@Entity
@Table(name = "epc_subitem_rule", catalog = "epc")
public class EpcSubitemRule extends BasicObject{

	private static final long serialVersionUID = 1L;
	private SubitemRulePK id;
	private EpcUnit epcUnit;
	private EpcSubitem epcSubitem;
	private Double rule;
	private Double powerRule;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "unitId", column = @Column(name = "UNIT_ID", nullable = false)),
			@AttributeOverride(name = "subitemId", column = @Column(name = "SUBITEM_ID", nullable = false)) })
	public SubitemRulePK getId() {
		return id;
	}

	public void setId(SubitemRulePK id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false, insertable = false, updatable = false)
	public EpcUnit getEpcUnit() {
		return epcUnit;
	}

	public void setEpcUnit(EpcUnit epcUnit) {
		this.epcUnit = epcUnit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBITEM_ID", nullable = false, insertable = false, updatable = false)
	public EpcSubitem getEpcSubitem() {
		return epcSubitem;
	}

	public void setEpcSubitem(EpcSubitem epcSubitem) {
		this.epcSubitem = epcSubitem;
	}
	
	@Column(name = "RULE", precision = 255, scale = 0)
	public Double getRule() {
		return rule;
	}

	public void setRule(Double rule) {
		this.rule = rule;
	}
	
	@Column(name = "POWERRULE", precision = 255, scale = 0)
	public Double getPowerRule() {
		return powerRule;
	}

	public void setPowerRule(Double powerRule) {
		this.powerRule = powerRule;
	}

}
