package com.thtf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-12-21 下午04:53:19
 * 类说明
 */
@Embeddable
public class SubitemRulePK implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long unitId;
	private Long subitemId;
	
	@Column(name = "UNIT_ID", nullable = false)
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	@Column(name = "SUBITEM_ID", nullable = false)
	public Long getSubitemId() {
		return subitemId;
	}
	public void setSubitemId(Long subitemId) {
		this.subitemId = subitemId;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SubitemRulePK))
			return false;
		SubitemRulePK castOther = (SubitemRulePK) other;

		return ((this.getUnitId() == castOther.getUnitId()) || (this
				.getUnitId() != null
				&& castOther.getUnitId() != null && this.getUnitId().equals(
				castOther.getUnitId())))
				&& ((this.getSubitemId() == castOther.getSubitemId()) || (this
						.getSubitemId() != null
						&& castOther.getSubitemId() != null && this
						.getSubitemId().equals(castOther.getSubitemId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUnitId() == null ? 0 : this.getUnitId().hashCode());
		result = 37
				* result
				+ (getSubitemId() == null ? 0 : this.getSubitemId()
						.hashCode());
		return result;
	}
}
